package net.paploo.goi.persistence.database.vocabulary

import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.BulkWriteDao
import java.nio.file.Path

class SqlFileVocabularyDao(
    val filePath: Path
) : BulkWriteDao<Vocabulary> {

    override suspend fun writeAll(values: List<Vocabulary>): Result<Unit> =
        values.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().map { records ->
            VocabularySqlWriter().invoke(filePath, records)
        }

}