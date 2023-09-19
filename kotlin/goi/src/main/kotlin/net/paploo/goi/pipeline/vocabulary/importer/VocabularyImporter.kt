package net.paploo.goi.pipeline.vocabulary.importer

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.pipeline.core.Importer

@Deprecated("I don't think we need this; just use the root interface")
interface VocabularyImporter : Importer<List<Vocabulary>>