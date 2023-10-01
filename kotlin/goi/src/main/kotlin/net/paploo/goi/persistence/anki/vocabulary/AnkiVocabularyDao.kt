package net.paploo.goi.persistence.anki.vocabulary

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.BulkWriteDao
import java.nio.file.Path

class AnkiVocabularyDao(
    val filePath: Path
) : BulkWriteDao<Vocabulary> {

    override suspend fun writeAll(values: List<Vocabulary>): Result<Unit> =
        values.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().flatMap { records ->
            VocabularyWriter().invoke(filePath, records)
        }

}
