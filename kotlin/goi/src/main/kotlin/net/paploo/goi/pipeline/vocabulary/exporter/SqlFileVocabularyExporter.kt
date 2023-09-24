package net.paploo.goi.pipeline.vocabulary.exporter

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.persistence.database.GoiVocabularyDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter
import java.nio.file.Path

class SqlFileVocabularyExporter(
    val config: Config
) : Exporter<List<Vocabulary>> {

    data class Config(
        val dataSource: ServiceDataSource,
        val filePath: Path
    )

    override suspend fun invoke(vocabularyList: List<Vocabulary>, context: Context): Result<Unit> =
        GoiVocabularyDao(config.dataSource, config.filePath).writeAllSql(vocabularyList)

}