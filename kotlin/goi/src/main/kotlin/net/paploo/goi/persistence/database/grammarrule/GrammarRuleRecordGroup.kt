package net.paploo.goi.persistence.database.grammarrule

import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleReferenceRecord

internal data class GrammarRuleRecordGroup(
    val ruleRecord: RuleRecord,
    val exampleRecords: List<ExampleRecord>,
    val ruleReferences: List<RuleReferenceRecord>,
    val exampleReferences: List<ExampleReferenceRecord>,
)