package net.paploo.goi.persistence.anki.grammarrule

import net.paploo.goi.persistence.common.CsvFormats
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.FileWriter
import java.nio.file.Path

internal class GrammarRuleWriter : suspend (Path, List<GrammarRuleCsvRecord>) -> Result<Unit> {

    val format: CSVFormat = CsvFormats.anki

    override suspend fun invoke(filePath: Path, records: List<GrammarRuleCsvRecord>): Result<Unit> =
                Result.runCatching {
                    FileWriter(filePath.toFile())
        }.map { writer ->
                    CSVPrinter(writer, format)
        }.map { printer ->
            printer.use {
                directives.forEach { (key, value) ->
                    it.printComment("${key}:${value}")
                }

                records.forEach { record ->
                    it.printRecord(record.toRow())
                }
            }
        }

    private val directives: List<Pair<String, String>> by lazy {
        listOf(
            "separator" to "Comma",
            "deck" to "日本語 Grammar",
            "notetype" to "日本語 Grammar",
            "tags column" to headers.size.toString(),
            "columns" to headers.joinToString(","),
        )
    }

    private val headers: List<String> by lazy {
        GrammarRuleCsvRecord.Field.entries.map { it.headerName }
    }
}