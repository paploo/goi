package net.paploo.goi.persistence.anki.grammarrule

import net.paploo.goi.common.extensions.kebabCase
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.domain.data.grammar.Example
import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.domain.tools.furiganatemplate.transformers.AnkiTemplateTransformer
import java.time.format.DateTimeFormatter

internal class GrammarRuleDomainToRecordTransform : (GrammarRule) -> Result<GrammarRuleCsvRecord> {

    override fun invoke(rule: GrammarRule): Result<GrammarRuleCsvRecord> =
            buildRecord(rule)

    private fun buildRecord(rule: GrammarRule): Result<GrammarRuleCsvRecord> =
        rule.examples.map { buildExample(it) }.sequenceToResult().mapCatching { examples ->
            GrammarRuleCsvRecord(
                id = rule.id.value.toString(),
                title = rule.title.preferredSpelling.value,
                titlePhonetic = null, //This field isn't used in the cards, but still needs to be in the CSV.
                meaning = rule.meaning,
                howToUse = rule.howToUse.joinToString(separator = "") { "<li>$it</li>" },
                jlptLevel = rule.jlptLevel?.levelNumber?.toString(),
                dateAdded = rule.dateAdded.format(DateTimeFormatter.ISO_LOCAL_DATE),
                rowNum = rule.rowNumber.toString(),
                examples = examples,
                tags = buildTags(rule).joinToString(" "),
            )
        }

    private fun buildTags(rule: GrammarRule): List<String> =
        listOf<List<Valued<String>>>(
            rule.tags.toList(),
            rule.references.toList(),
            rule.examples.flatMap { it.tags },
            rule.examples.flatMap { it.references },
        ).flatten().map { it.value.kebabCase() }.distinct().sorted()

    private fun buildExample(example: Example): Result<GrammarRuleCsvRecord.Example> =
        example.text.transform(AnkiTemplateTransformer.default).map { template ->
            GrammarRuleCsvRecord.Example(
                text = template.templateString,
                textPhonetic = null, //The cards all just use the raw text; field is deprecated but still needed in CSV.
                meaning = example.meaning,
            )
        }


}