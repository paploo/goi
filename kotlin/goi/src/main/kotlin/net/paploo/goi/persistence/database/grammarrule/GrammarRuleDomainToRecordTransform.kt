package net.paploo.goi.persistence.database.grammarrule

import net.paploo.goi.common.extensions.constCase
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.extensions.snakeCase
import net.paploo.goi.domain.data.grammar.Example
import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.domain.tools.furiganatemplate.transformers.AnkiTemplateTransformer
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleReferenceRecord

internal class GrammarRuleDomainToRecordTransform : (GrammarRule) -> Result<GrammarRuleRecordGroup> {

    override fun invoke(rule: GrammarRule): Result<GrammarRuleRecordGroup> =
        buildRecordGroup(rule)

    private fun buildRecordGroup(rule: GrammarRule): Result<GrammarRuleRecordGroup> =
        buildExampleRecords(rule).mapCatching { exampleRecords ->
            GrammarRuleRecordGroup(
                ruleRecord = buildRuleRecord(rule),
                exampleRecords = exampleRecords,
                ruleReferences = buildRuleReferences(rule),
                exampleReferences = buildExampleReferences(rule),
            )
        }

    private fun buildRuleRecord(rule: GrammarRule): RuleRecord =
        RuleRecord(
            id = rule.id.value,
            meaning = rule.meaning,
            titlePreferredSpelling = rule.title.preferredSpelling.value,
            titlePhoneticSpelling = null, //This is not meaningful in the current incarnation; TODO: drop from table?
            titleFuriganaTemplate = null, //This is not meaningful in the current incarnation; TODO: drop from table?
            howToUse = rule.howToUse.toTypedArray(),
            jlptLevel = rule.jlptLevel?.levelNumber,
            rowNum = rule.rowNumber,
            dateAdded = rule.dateAdded,
            tags = rule.tags.map { it.value.snakeCase() }.toTypedArray(),
        )

    private fun buildExampleRecords(rule: GrammarRule): Result<List<ExampleRecord>> =
        rule.examples.mapIndexed { index, example ->
            buildExampleRecord(rule, example, index)
        }.sequenceToResult()

    private fun buildExampleRecord(rule: GrammarRule, example: Example, index: Int): Result<ExampleRecord> =
        example.text.transform(AnkiTemplateTransformer()).map { template ->
            ExampleRecord(
                id = example.id.value,
                ruleId = rule.id.value,
                meaning = rule.meaning,
                textPreferredSpelling = example.text.preferredSpelling.value,
                textPhoneticSpelling = example.text.phoneticSpelling?.value,
                textFuriganaTemplate = template.templateString,
                sortRank = index + 1,
                tags = example.tags.map { it.value }.toTypedArray(),
            )
        }

    private fun buildRuleReferences(rule: GrammarRule): List<RuleReferenceRecord> =
        rule.references.map {
            RuleReferenceRecord(
                ruleId = rule.id.value,
                lessonCode = it.value.constCase()
            )
        }

    private fun buildExampleReferences(rule: GrammarRule): List<ExampleReferenceRecord> =
        rule.examples.flatMap { example ->
            example.references.map {
                ExampleReferenceRecord(
                    exampleId = example.id.value,
                    lessonCode = it.value.constCase()
                )
            }
        }


}