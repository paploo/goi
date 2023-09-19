package net.paploo.goi.pipeline.vocabulary

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.pipeline.core.BasePipeline
import net.paploo.goi.pipeline.core.Exporter
import net.paploo.goi.pipeline.core.Importer
import net.paploo.goi.pipeline.core.Transformer

class VocabularyPipeline(
    config: Configuration
) : BasePipeline<List<GrammarRule>>() {

    class Configuration(
        val importer: Importer<List<GrammarRule>>,
        val transformers: List<Transformer<List<GrammarRule>>>,
        val exporters: List<Exporter<List<GrammarRule>>>
    )

    override val importer: Importer<List<GrammarRule>> = config.importer

    override val transformers: Collection<Transformer<List<GrammarRule>>> = config.transformers

    override val exporters: Collection<Exporter<List<GrammarRule>>> = config.exporters

}