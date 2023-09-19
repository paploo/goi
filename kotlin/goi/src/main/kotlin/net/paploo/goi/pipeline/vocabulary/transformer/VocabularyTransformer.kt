package net.paploo.goi.pipeline.vocabulary.transformer

import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.pipeline.core.Transformer

@Deprecated("I don't think we need this; just use the root interface")
interface VocabularyTransformer : Transformer<List<Vocabulary>>