package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.persistence.common.CsvFormats
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.charset.StandardCharsets
import java.nio.file.Path

internal class VocabularyReader : suspend (Path) -> Result<List<VocabularyCsvRecord>> {

    val format: CSVFormat = CsvFormats.googleSheet

    override suspend fun invoke(filePath: Path): Result<List<VocabularyCsvRecord>> =
        Result.runCatching {
            CSVParser.parse(filePath, StandardCharsets.UTF_8, format)
        }.map { parser ->
            parser.map { VocabularyCsvRecord(it) }
        }
}
