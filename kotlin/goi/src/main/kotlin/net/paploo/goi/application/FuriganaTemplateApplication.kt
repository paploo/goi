package net.paploo.goi.application

import net.paploo.goi.common.extensions.append
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.util.TimerLog
import net.paploo.goi.pipeline.template.TemplatePipeline
import net.paploo.goi.pipeline.template.exporter.CSVTemplateExporter
import net.paploo.goi.pipeline.template.importer.FileListTemplateImporter
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolute

class FuriganaTemplateApplication(
    configBuilder: Configuration.() -> Unit = {}
) : BaseApplication<FuriganaTemplateApplication.Configuration, List<String>, FuriganaTemplateApplication.Environment>() {

    data class Configuration(
        var filesDirectory: Path = Path(".", "files").absolute()
    )

    data class Environment(
        val pipeline: TemplatePipeline
    )

    override val config: Configuration = Configuration().apply(configBuilder)

    override suspend fun invokeEnvironment(environment: Environment): Result<Unit> =
        Result.success(
            TimerLog("Pipeline furigana template")
        ).flatMap { timer ->
            val startMark = timer.mark("Starting pipeline")
            val result = environment.pipeline()
            val endMark = timer.mark("Completed pipeline with success=${result.isSuccess}")

            result.onFailure {
                logger.error("FAILURE: $it", it)
            }.onSuccess {
                val rate = it.size.toDouble() / (endMark.delta - startMark.delta).toMillis().toDouble() * 1000.0
                logger.info("SUCCESS: records processed: ${it.size}, time was ${endMark.delta.toMillis()} ms, rate was $rate rec/sec")
            }.also {
                logger.info(timer.formatted())
            }.map { Unit }
        }

    override suspend fun setupEnvironment(args: List<String>, config: Configuration): Result<Environment> =
        Result.runCatching {
            Environment(
                pipeline = TemplatePipeline(pipelineConfig(config.filesDirectory))
            )
        }


    override suspend fun teardownEnvironment(environment: Environment): Result<Unit> =
        Result.success(Unit)

    private fun pipelineConfig(
        filesDirectory: Path
    ): TemplatePipeline.Configuration =
        TemplatePipeline.Configuration(
            importer = FileListTemplateImporter(
                FileListTemplateImporter.Config(
                    filePath = filesDirectory append Path("furigana_template.txt")
                )
            ),
            transformers = emptyList(),
            exporters = listOf(
                CSVTemplateExporter(
                    CSVTemplateExporter.Config(
                        filePath = filesDirectory append Path("furigana_template", "furigana_values.csv"),
                        format = CSVTemplateExporter.Config.Format.CSV
                    )
                ),
                CSVTemplateExporter(
                    CSVTemplateExporter.Config(
                        filePath = filesDirectory append Path("furigana_template", "furigana_values.tsv"),
                        format = CSVTemplateExporter.Config.Format.TSV
                    )
                )
            )
        )

}