package net.paploo.goi.pipeline.vocabulary

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.pipeline.core.BasePipeline
import net.paploo.goi.pipeline.core.Exporter
import net.paploo.goi.pipeline.core.Importer
import net.paploo.goi.pipeline.core.Transformer

class VocabularyPipeline(
    config: Configuration
) : BasePipeline<List<Vocabulary>>() {

    class Configuration(
        val importer: Importer<List<Vocabulary>>,
        val transformers: List<Transformer<List<Vocabulary>>>,
        val exporters: List<Exporter<List<Vocabulary>>>
    )

    override val importer: Importer<List<Vocabulary>> = config.importer

    override val transformers: Collection<Transformer<List<Vocabulary>>> = config.transformers

    override val exporters: Collection<Exporter<List<Vocabulary>>> = config.exporters

}