package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import java.nio.file.Path

class GoogleSheetsVocabularyDao(
    val filePath: Path
) {

    suspend fun getAll(): Result<List<Vocabulary>> =
        GoogleSheetsVocabularyReader().invoke(filePath).flatMap { records ->
            records.map { VocabularyRecordToDomainTransform().invoke(it) }.sequenceToResult()
        }


    suspend fun writeAll(vocabularyList: List<Vocabulary>): Result<List<Vocabulary>> = TODO()

}