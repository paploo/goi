package net.paploo.goi.application

import com.zaxxer.hikari.HikariConfig
import net.paploo.goi.common.extensions.append
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.util.TimerLog
import net.paploo.goi.persistence.common.HikariServicedataSource
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.pipeline.core.Pipeline
import net.paploo.goi.pipeline.grammarrule.GrammarRulePipeline
import net.paploo.goi.pipeline.grammarrule.exporter.AnkiGrammarRuleExporter
import net.paploo.goi.pipeline.grammarrule.exporter.SqlFileGrammarRuleExporter
import net.paploo.goi.pipeline.grammarrule.exporter.SqlGrammarRuleExporter
import net.paploo.goi.pipeline.grammarrule.importer.JsonFileGrammarRuleImporter
import net.paploo.goi.pipeline.grammarrule.transformer.ValidationGrammarRuleTransformer
import net.paploo.goi.pipeline.vocabulary.VocabularyPipeline
import net.paploo.goi.pipeline.vocabulary.exporter.AnkiVocabularyExporter
import net.paploo.goi.pipeline.vocabulary.exporter.GoogleVocabularyExporter
import net.paploo.goi.pipeline.vocabulary.exporter.SqlFileVocabularyExporter
import net.paploo.goi.pipeline.vocabulary.exporter.SqlVocabularyExporter
import net.paploo.goi.pipeline.vocabulary.importer.GoogleSheetVocabularyImporter
import net.paploo.goi.pipeline.vocabulary.transformer.AutoConjugationTransformer
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolute

/**
 * Applicationized vocabulary/grammar pipelines.
 */
class PipelineApplication(
    configBuilder: Configuration.() -> Unit = {}
) : BaseApplication<PipelineApplication.Configuration, PipelineApplication.Arguments, PipelineApplication.Environment>() {

    data class Configuration(
        /**
         * For now we derive all file paths relative to this from this:
         */
        var filesDirectory: Path = Path(".", "files").absolute(),

        /**
         * The database hikari config
         */
        var hikariConfig: HikariConfig = HikariConfig().apply {
            poolName = "GoiPostgresPool"
            jdbcUrl = "jdbc:postgresql://localhost:5432/goi"
            username = "postgres"
            password = "postgres"
            minimumIdle = 2
            maximumPoolSize = 10
        }
    )

    data class Arguments(
        val actions: List<Action>
    )

    enum class Action {
        Vocabulary, Grammar
    }

    data class Environment(
        val actions: List<Action>,
        val dataSource: ServiceDataSource,
        val vocabularyPipeline: VocabularyPipeline,
        val grammarRulePipeline: GrammarRulePipeline
    )

    override val config: Configuration = Configuration().apply(configBuilder)

    override suspend fun invokeEnvironment(environment: Environment): Result<Unit> =
        environment.actions.map { action ->
            when (action) {
                Action.Vocabulary -> invokePipeline(environment.vocabularyPipeline, action)
                Action.Grammar -> invokePipeline(environment.grammarRulePipeline, action)
            }
        }.sequenceToResult().map { Unit }

    private suspend fun <T> invokePipeline(pipeline: Pipeline<Collection<T>>, action: Action): Result<Collection<T>> =
        Result.success(
            TimerLog("Pipeline ${action.name}")
        ).flatMap { timer ->
            val startMark = timer.mark("Starting pipeline for action = $action")
            val result = pipeline()
            val endMark = timer.mark("Completed pipeline for action = $action with success=${result.isSuccess}")

            result.onFailure {
                logger.error("FAILURE: $it", it)
            }.onSuccess {
                val rate = it.size.toDouble() / (endMark.delta - startMark.delta).toMillis().toDouble() * 1000.0
                logger.info("SUCCESS: records processed: ${it.size}, time was ${endMark.delta.toMillis()} ms, rate was $rate rec/sec")
            }.also {
                logger.info(timer.formatted())
            }
        }


    override suspend fun setupEnvironment(args: Arguments, config: Configuration): Result<Environment> =
        Result.runCatching {
            val dataSource = HikariServicedataSource(config.hikariConfig)
            Environment(
                actions = args.actions,
                dataSource = dataSource,
                vocabularyPipeline = VocabularyPipeline(vocabularyPipelineConfig(config.filesDirectory, dataSource)),
                grammarRulePipeline = GrammarRulePipeline(grammarRulePipelineConfig(config.filesDirectory, dataSource)),
            ).also { env ->
                logger.warn(env.toString())
                logger.debug("args: {}", args)
                logger.debug("config: {}", config)
                logger.debug("env: {}", env)
            }
        }

    override suspend fun teardownEnvironment(environment: Environment): Result<Unit> = Result.runCatching {
        environment.dataSource.close()
    }

    private fun vocabularyPipelineConfig(
        filesDirectory: Path,
        dataSource: ServiceDataSource
    ): VocabularyPipeline.Configuration =
        VocabularyPipeline.Configuration(
            importer = GoogleSheetVocabularyImporter(
                GoogleSheetVocabularyImporter.Config(
                    filePath = filesDirectory append Path("日本語 Vocab - Vocab.csv")
                )
            ),
            transformers = listOf(
                AutoConjugationTransformer()
            ),
            exporters = listOf(
                AnkiVocabularyExporter(
                    AnkiVocabularyExporter.Config(
                        filePath = filesDirectory append Path("vocabulary", "anki.csv")
                    )
                ),
                GoogleVocabularyExporter(
                    GoogleVocabularyExporter.Config(
                        filePath = filesDirectory append Path("vocabulary", "google_sheet.csv"
                        )
                    )
                ),
                SqlFileVocabularyExporter(
                    SqlFileVocabularyExporter.Config(
                        filePath = filesDirectory append Path("vocabulary", "data.sql")
                    )
                ),
                SqlVocabularyExporter(SqlVocabularyExporter.Config(dataSource = dataSource)),
            )
        )

    private fun grammarRulePipelineConfig(
        filesDirectory: Path,
        dataSource: ServiceDataSource
    ): GrammarRulePipeline.Configuration =
        GrammarRulePipeline.Configuration(
            importer = JsonFileGrammarRuleImporter(
                JsonFileGrammarRuleImporter.Config(
                    filePath = filesDirectory append Path("日本語 Vocab - Grammar.json")
                )
            ),
            transformers = listOf(
                ValidationGrammarRuleTransformer()
            ),
            exporters = listOf(
                AnkiGrammarRuleExporter(
                    AnkiGrammarRuleExporter.Config(
                        filePath = filesDirectory append Path("grammar", "anki.csv")
                    )
                ),
                SqlFileGrammarRuleExporter(
                    SqlFileGrammarRuleExporter.Config(
                        filePath = filesDirectory append Path("grammar", "data.sql")
                    )
                ),
                SqlGrammarRuleExporter(
                    SqlGrammarRuleExporter.Config(
                        dataSource = dataSource
                    )
                )
            )
        )


}