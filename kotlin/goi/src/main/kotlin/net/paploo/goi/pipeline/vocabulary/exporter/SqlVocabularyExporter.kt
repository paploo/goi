package net.paploo.goi.pipeline.vocabulary.exporter

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.persistence.database.vocabulary.SqlVocabularyDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter

class SqlVocabularyExporter(
    val config: Config
) : Exporter<List<Vocabulary>> {

    data class Config(
        val dataSource: ServiceDataSource
    )

    override suspend fun invoke(vocabularyList: List<Vocabulary>, context: Context): Result<Unit> =
        SqlVocabularyDao(config.dataSource).writeAll(vocabularyList)

}