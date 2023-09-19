package net.paploo.goi.pipeline.grammarrule.importer

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.pipeline.core.Importer

@Deprecated("I don't think we need this; just use the root interface")
interface GrammarRuleImporter : Importer<List<GrammarRule>>