package net.paploo.goi.pipeline.furiganatemplate

import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.pipeline.core.BasePipeline
import net.paploo.goi.pipeline.core.Exporter
import net.paploo.goi.pipeline.core.Importer
import net.paploo.goi.pipeline.core.Transformer

class FuriganaTemplatePipeline(
    config: Configuration
) : BasePipeline<List<FuriganaString>>() {

    class Configuration(
        val importer: Importer<List<FuriganaString>>,
        val transformers: Collection<Transformer<List<FuriganaString>>>,
        val exporters: Collection<Exporter<List<FuriganaString>>>
    )

    override val importer: Importer<List<FuriganaString>> = config.importer

    override val transformers: Collection<Transformer<List<FuriganaString>>> = config.transformers

    override val exporters: Collection<Exporter<List<FuriganaString>>> = config.exporters

}