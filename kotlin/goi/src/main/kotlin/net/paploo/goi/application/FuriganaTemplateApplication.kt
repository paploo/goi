package net.paploo.goi.application

import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.domain.tools.furiganatemplate.transformers.AnkiTemplateTransformer
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.OutputStream
import java.io.PrintWriter
import java.io.StringWriter

class FuriganaTemplateApplication(
    configBuilder: Configuration.() -> Unit = {}
) : Application<List<String>> {

    data class Configuration(
        var io: OutputStream = System.out,
        var delimiter: String = ","
    )

    private val config: Configuration = Configuration().apply(configBuilder)

    override suspend fun invoke(args: List<String>): Result<Unit> =
        PrintWriter(config.io).use { writer ->
            writer.print(toRow(headers))
            args.map { string ->
                process(string).onSuccess {
                    writer.print(toRow(it))
                }
            }.sequenceToResult().map { Unit }
        }

    private fun process(templateString: String): Result<List<String?>> =
        Result.runCatching {
            FuriganaString.fromCurlyBraces(templateString)
        }.mapCatching { furiganaString ->
            listOf(
                templateString,
                furiganaString.preferredSpelling.value,
                furiganaString.phoneticSpelling?.value,
                furiganaString.transform(AnkiTemplateTransformer.default).getOrNull()?.templateString
            )
        }

    private val headers: List<String> =
        listOf("template", "preferred", "phonetic", "anki")

    private fun toRow(values: List<String?>): String =
            StringWriter().use { writer ->
                CSVPrinter(writer, csvFormat).use { csvPrinter ->
                    csvPrinter.printRecord(values)
                }

                writer.toString()
            }

    private val csvFormat: CSVFormat = CSVFormat.Builder
        .create(CSVFormat.RFC4180)
        .setRecordSeparator("\n") //Use UNIX LF instead of RFC4180 specified CRLF
        .setDelimiter(config.delimiter) //The field delimeter (comma for CSV)
        .setNullString("") //Make sure nulls render as empty fields.
        .build()

}