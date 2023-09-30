package net.paploo.goi.persistence.jsonfile.grammarrule

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.domain.data.common.JlptLevel
import net.paploo.goi.domain.data.common.Tag
import net.paploo.goi.domain.data.grammar.Example
import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate

internal class GrammarRuleRecordToDomainTransform : (GrammarRuleDto) -> Result<GrammarRule> {

    override fun invoke(record: GrammarRuleDto): Result<GrammarRule> =
        jlptLevel(record).flatMap { jlptLevel ->
            rowNumber(record).flatMap { rownumber ->
                examples(record).mapCatching { examples ->
                    GrammarRule(
                        id = GrammarRule.Id(record.id),
                        title = record.title,
                        meaning = record.meaning,
                        howToUse = record.howToUse,
                        jlptLevel = jlptLevel,
                        rowNumber = rownumber,
                        dateAdded = record.dateAdded,
                        references = record.lessonCodes.map { Lesson.Code(it) }.toSet(),
                        tags = record.tags.map { Tag(it) }.toSet(),
                        examples = examples,
                    )
                }
            }
        }


    private fun jlptLevel(record: GrammarRuleDto): Result<JlptLevel?> =
        record.jlptLevel?.let { JlptLevel.forLevelNumber(it) } ?: Result.success(null)

    private fun rowNumber(record: GrammarRuleDto): Result<Int> =
        record.rowNum?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("Missing rowNumber for record: $record"))

    private fun examples(record: GrammarRuleDto): Result<List<Example>> =
        record.examples.map { example(it) }.sequenceToResult()

    private fun example(record: ExampleDto): Result<Example> =
        Result.runCatching {
            Example(
                id = Example.Id(record.id),
                meaning = record.meaning,
                text = FuriganaString(FuriganaTemplate.CurlyBraces(record.text.spelling)),
                references = record.lessonCodes.map { Lesson.Code(it) }.toSet(),
                tags = record.tags.map { Tag(it) }.toSet(),
            )
        }


}