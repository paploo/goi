package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.BulkDao
import java.nio.file.Path

class GoogleSheetsVocabularyDao(
    val filePath: Path
) : BulkDao<Vocabulary> {

    override suspend fun getAll(): Result<List<Vocabulary>> =
        VocabularyReader().invoke(filePath).flatMap { records ->
            records.map { VocabularyRecordToDomainTransform().invoke(it) }.sequenceToResult()
        }


    override suspend fun writeAll(values: List<Vocabulary>): Result<Unit> =
        values.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().map { records ->
            VocabularyWriter().invoke(filePath, records)
        }

}