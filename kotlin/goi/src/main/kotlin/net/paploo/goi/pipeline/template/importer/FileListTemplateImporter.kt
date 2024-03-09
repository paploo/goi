package net.paploo.goi.pipeline.template.importer

import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Importer
import java.nio.file.Path
import kotlin.io.path.readLines

class FileListTemplateImporter(
    val config: Config
) : Importer<List<FuriganaString>> {

    data class Config(
        val filePath: Path
    )

    override suspend fun invoke(context: Context): Result<List<FuriganaString>> =
        context.timerLog.markAround("Import text lines from a file") {
            Result.success(
                config.filePath.readLines()
            ).map { lines ->
                lines.map {
                    FuriganaString.fromCurlyBraces(it.trim())
                }
            }
        }

}