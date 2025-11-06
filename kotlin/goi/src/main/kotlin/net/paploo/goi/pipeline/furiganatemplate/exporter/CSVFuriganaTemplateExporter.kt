package net.paploo.goi.pipeline.furiganatemplate.exporter

import net.paploo.goi.common.extensions.mapFailure
import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import net.paploo.goi.domain.tools.furiganatemplate.transformers.AnkiTemplateTransformer
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.Path

class CSVFuriganaTemplateExporter (
    val config: Config
) : Exporter<List<FuriganaString>> {

    data class Config(
        val filePath: Path,
        val format: Format
    ) {

        enum class Format(val delimiter: Char) {
            CSV(','),
            TSV('\t')
        }

    }

    override suspend fun invoke(furiganaStrings: List<FuriganaString>, context: Context): Result<Unit> =
        Result.runCatching {
            PrintWriter(config.filePath.toFile()).use { writer ->
                writer.print(writeRow(headers))
                furiganaStrings.mapIndexed { index, furiganaString ->
                    println(furiganaString)
                    val rowValues = toRow(furiganaString).mapFailure { th ->
                        IllegalArgumentException("Error on entry number ${index+1}: $th", th)
                    }.getOrThrow()
                    writer.print(writeRow(rowValues))
                }
            }
        }

    private val headers: List<String> =
        listOf("template", "preferred", "phonetic", "anki")

    private fun toRow(furiganaString: FuriganaString): Result<List<String?>> =
        when(val template = furiganaString.furiganaTemplate) {
            is FuriganaTemplate.Text -> template.preferred
            is FuriganaTemplate.Ruby -> "{${template.preferred}|${template.phonetic}}"
            is FuriganaTemplate.CurlyBraces -> template.templateString
            is FuriganaTemplate.Anki -> template.templateString
        }.let { template ->
            Result.runCatching {
                listOf(
                    template,
                    furiganaString.preferredSpelling.value,
                    furiganaString.phoneticSpelling?.value,
                    furiganaString.transform(AnkiTemplateTransformer.default).getOrNull()?.templateString
                )
            }
        }

    private fun toRowUnsafe(furiganaString: FuriganaString): List<String?> =
        when(val template = furiganaString.furiganaTemplate) {
            is FuriganaTemplate.Text -> template.preferred
            is FuriganaTemplate.Ruby -> "{${template.preferred}|${template.phonetic}}"
            is FuriganaTemplate.CurlyBraces -> template.templateString
            is FuriganaTemplate.Anki -> template.templateString
        }.let { template ->
            listOf(
                template,
                furiganaString.preferredSpelling.value,
                furiganaString.phoneticSpelling?.value,
                furiganaString.transform(AnkiTemplateTransformer.default).getOrNull()?.templateString
            )
        }

    private fun writeRow(values: List<String?>): String =
        StringWriter().use { writer ->
            CSVPrinter(writer, csvFormat).use { csvPrinter ->
                csvPrinter.printRecord(values)
            }

            writer.toString()
        }

    private val csvFormat: CSVFormat = CSVFormat.Builder
        .create(CSVFormat.RFC4180)
        .setRecordSeparator("\n") //Use UNIX LF instead of RFC4180 specified CRLF
        .setDelimiter(config.format.delimiter) //The field delimeter (comma for CSV)
        .setNullString("") //Make sure nulls render as empty fields.
        .get()

}