package net.paploo.goi.pipeline.vocabulary.importer

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.googlesheets.vocabulary.GoogleSheetsVocabularyDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Importer
import java.nio.file.Path

class GoogleSheetImporter(
    val configuration: Configuration
) : Importer<List<Vocabulary>> {

    data class Configuration(
        val filePath: Path
    )

    override suspend fun invoke(context: Context): Result<List<Vocabulary>> =
        context.timerLog.markAround("Import from GoogleSheet") {
            GoogleSheetsVocabularyDao(configuration.filePath).getAll()
        }

}