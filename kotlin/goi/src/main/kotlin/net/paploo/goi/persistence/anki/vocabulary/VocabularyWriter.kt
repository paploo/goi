package net.paploo.goi.persistence.anki.vocabulary

import net.paploo.goi.persistence.common.CsvFormats
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.FileWriter
import java.nio.file.Path

internal class VocabularyWriter : suspend (Path, List<VocabularyCsvRecord>) -> Result<Unit> {

    val format: CSVFormat = CsvFormats.default

    override suspend fun invoke(filePath: Path, records: List<VocabularyCsvRecord>): Result<Unit> =
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
            "deck" to "日本語 Vocab",
            "notetype" to "日本語 Vocab",
            "tags column" to headers.size.toString(),
            "columns" to headers.joinToString(","),
        )
    }

    private val headers: List<String> by lazy {
        VocabularyCsvRecord.Field.entries.map { it.headerName }
    }
}