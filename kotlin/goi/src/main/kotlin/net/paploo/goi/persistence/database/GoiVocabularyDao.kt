package net.paploo.goi.persistence.database

import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.ServiceDataSource
import java.nio.file.Path

class GoiVocabularyDao(
    val dataSource: ServiceDataSource,
    val filePath: Path
) {

    suspend fun writeAll(vocabularyList: List<Vocabulary>): Result<Unit> = TODO()

    suspend fun writeAllSql(vocabularyList: List<Vocabulary>): Result<Unit> =
        vocabularyList.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().map { records ->
            VocabularySqlWriter().invoke(filePath, records)
        }

}
