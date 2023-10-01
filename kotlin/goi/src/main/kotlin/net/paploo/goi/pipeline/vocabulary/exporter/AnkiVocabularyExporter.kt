package net.paploo.goi.pipeline.vocabulary.exporter

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.anki.vocabulary.AnkiVocabularyDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter
import java.nio.file.Path

class AnkiVocabularyExporter(
    val config: Config
) : Exporter<List<Vocabulary>> {

    data class Config(
        val filePath: Path
    )

    override suspend fun invoke(vocabularyList: List<Vocabulary>, context: Context): Result<Unit> =
        AnkiVocabularyDao(config.filePath).writeAll(vocabularyList)

}

