package net.paploo.goi.persistence.database.vocabulary

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.BulkWriteDao
import net.paploo.goi.persistence.common.ServiceDataSource

class SqlVocabularyDao(
    val dataSource: ServiceDataSource,
) : BulkWriteDao<Vocabulary> {

    override suspend fun writeAll(values: List<Vocabulary>): Result<Unit> = TODO()

}
