package net.paploo.goi.pipeline.vocabulary.exporter

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.pipeline.core.Exporter

@Deprecated("I don't think we need this; just use the root interface")
interface VocabularyExporter : Exporter<List<Vocabulary>>