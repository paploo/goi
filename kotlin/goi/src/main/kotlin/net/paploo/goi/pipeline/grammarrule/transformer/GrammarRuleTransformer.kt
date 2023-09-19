package net.paploo.goi.pipeline.grammarrule.transformer

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.pipeline.core.Transformer

@Deprecated("I don't think we need this; just use the root interface")
interface GrammarRuleTransformer : Transformer<List<GrammarRule>>