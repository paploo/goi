package net.paploo.goi.application

import net.paploo.goi.common.extensions.append
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.util.TimerLog
import net.paploo.goi.pipeline.furiganatemplate.FuriganaTemplatePipeline
import net.paploo.goi.pipeline.furiganatemplate.exporter.CSVFuriganaTemplateExporter
import net.paploo.goi.pipeline.furiganatemplate.importer.FileListFuriganaTemplateImporter
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolute

/**
 * TODO: Re-write into the existing PipelineApplication as a template action?
 *       If so, also move the logic of the helpers into the persistence layer.
 */
class FuriganaTemplateApplication(
    configBuilder: Configuration.() -> Unit = {}
) : BaseApplication<FuriganaTemplateApplication.Configuration, List<String>, FuriganaTemplateApplication.Environment>() {

    data class Configuration(
        var filesDirectory: Path = Path(".", "files").absolute()
    )

    data class Environment(
        val pipeline: FuriganaTemplatePipeline
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
                pipeline = FuriganaTemplatePipeline(pipelineConfig(config.filesDirectory))
            )
        }


    override suspend fun teardownEnvironment(environment: Environment): Result<Unit> =
        Result.success(Unit)

    private fun pipelineConfig(
        filesDirectory: Path
    ): FuriganaTemplatePipeline.Configuration =
        FuriganaTemplatePipeline.Configuration(
            importer = FileListFuriganaTemplateImporter(
                FileListFuriganaTemplateImporter.Config(
                    filePath = filesDirectory append Path("furigana_template.txt")
                )
            ),
            transformers = emptyList(),
            exporters = listOf(
                CSVFuriganaTemplateExporter(
                    CSVFuriganaTemplateExporter.Config(
                        filePath = filesDirectory append Path("furigana_template", "furigana_values.csv"),
                        format = CSVFuriganaTemplateExporter.Config.Format.CSV
                    )
                ),
                CSVFuriganaTemplateExporter(
                    CSVFuriganaTemplateExporter.Config(
                        filePath = filesDirectory append Path("furigana_template", "furigana_values.tsv"),
                        format = CSVFuriganaTemplateExporter.Config.Format.TSV
                    )
                )
            )
        )

}