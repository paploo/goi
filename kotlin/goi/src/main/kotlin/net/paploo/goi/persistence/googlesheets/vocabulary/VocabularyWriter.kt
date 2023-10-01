package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.persistence.common.CsvFormats
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.FileWriter
import java.nio.file.Path

internal class VocabularyWriter : suspend (Path, List<VocabularyCsvRecord>) -> Result<Unit> {

    val format: CSVFormat = CsvFormats.googleSheet

    override suspend fun invoke(filePath: Path, records: List<VocabularyCsvRecord>): Result<Unit> =
        Result.runCatching {
            FileWriter(filePath.toFile())
        }.map { writer ->
            CSVPrinter(writer, format)
        }.map { printer ->
            printer.use {
                it.printRecord(headers)

                records.forEach { record ->
                    it.printRecord(record.toRow())
                }
            }
        }

    private val headers: List<String> by lazy {
        VocabularyCsvRecord.Field.entries.map { it.headerName }
    }

}