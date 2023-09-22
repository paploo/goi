package net.paploo.goi.persistence.anki.vocabulary

import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import java.nio.file.Path

class AnkiVocabularyDao(
    val filePath: Path
) {

    suspend fun writeAll(vocularyList: List<Vocabulary>): Result<Unit> =
        vocularyList.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().map { records ->
            VocabularyWriter().invoke(filePath, records)
        }

}


