package net.paploo.goi.pipeline.grammarrule.exporter

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.pipeline.core.Exporter

@Deprecated("I don't think we need this; just use the root interface")
interface GrammarRuleExporter : Exporter<List<GrammarRule>>