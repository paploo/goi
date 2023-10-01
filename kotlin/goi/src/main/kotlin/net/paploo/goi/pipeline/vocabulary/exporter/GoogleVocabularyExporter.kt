package net.paploo.goi.pipeline.vocabulary.exporter

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.googlesheets.vocabulary.GoogleSheetsVocabularyDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter
import java.nio.file.Path

class GoogleVocabularyExporter(
    val config: Config
) : Exporter<List<Vocabulary>> {

    data class Config(
        val filePath: Path
    )

    override suspend fun invoke(vocabularyList: List<Vocabulary>, context: Context): Result<Unit> =
        GoogleSheetsVocabularyDao(config.filePath).writeAll(vocabularyList)

}