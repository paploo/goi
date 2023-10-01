package net.paploo.goi.pipeline.grammarrule.transformer

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.domain.tools.validation.ValidationReport
import net.paploo.goi.domain.tools.validation.log
import net.paploo.goi.domain.tools.validation.validatedResult
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Transformer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.util.UUID

/**
 * Things that are validated (because they are mistakes I actually make):
 * - All IDs are non-zero.
 * - Test that the example phonetic spelling is non-null (this almost always means we're missing template values)!
 * - All dates are after 2020-01-01
 * - All rules have examples
 * - The rule titles only have full-width characters
 * - The rule meanings do NOT have full-width characters
 */
class ValidationGrammarRuleTransformer : Transformer<List<GrammarRule>> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun invoke(rules: List<GrammarRule>, context: Context): Result<List<GrammarRule>> =
        rules.validatedResult {
            rootValidationReport(it).also { report ->
                logger.log(report)
            }
        }

    private fun rootValidationReport(rules: List<GrammarRule>): ValidationReport =
        ValidationReport.Group(
            name = "Grammar Rule Validation Report",
            messages = listOfNotNull(
                nonZeroIdsReport(rules),
                nonEmptyExamples(rules),
                addedDateReport(rules),
                nonNullPhoneticSpelling(rules),
                titlePlaceholderCharacterReport(rules),
                meaningPlaceholderCharacterReport(rules),
            )
        )

    private fun nonZeroIdsReport(rules: List<GrammarRule>): ValidationReport? {
        //This is mostly useful for catching errors from my copy/pastable template, which has zero id placeholders.
        val zeroUUID = UUID(0L, 0L)
        val zeroIdRuleCount = rules.count { it.id.value == zeroUUID }
        val zeroIdExamplesCount = rules.sumOf { it.examples.count { it.id.value == zeroUUID } }
        return ValidationReport.Group(
            name = "Empty Ids",
            messages = listOf(
                if (zeroIdRuleCount > 0) ValidationReport.Message.error("Rules: There are $zeroIdRuleCount rules with an empty ID")
                else ValidationReport.Message.info("Rules: There are no rules with empty IDs"),

                if (zeroIdExamplesCount > 0) ValidationReport.Message.error("Rules: There are $zeroIdExamplesCount examples with an empty ID")
                else ValidationReport.Message.info("Rules: There are no examples with empty IDs"),
            )
        )
    }

    private fun addedDateReport(rules: List<GrammarRule>): ValidationReport? =
        //This is mostly useful for catching errors from my copy/pastable template, which has 1970-01-01 placeholders.
        LocalDate.of(2020, 1, 1).let { thresholdDate ->
            rules.filter { it.dateAdded < thresholdDate }
        }.let { invalidRules ->
            ValidationReport.Group(
                name = "Rules: Invalid Date Added",
                messages = if (invalidRules.isEmpty()) {
                    listOf( ValidationReport.Message.info("There are no rules with invalid added date") )
                } else {
                    listOf(
                        ValidationReport.Message.error("There are ${invalidRules.size} Rules with invalid added dates"),
                    ) + invalidRules.map {
                        ValidationReport.Message.debug("Rule ${it.id.value}: ${it.dateAdded}")
                    }
                }
            )
        }

    private fun nonEmptyExamples(rules: List<GrammarRule>): ValidationReport? =
        rules.filter { rule ->
            rule.examples.isEmpty()
        }.let { emptyRules ->
            ValidationReport.Group(
                name = "Rules Missing Examples",
                messages = if (emptyRules.isEmpty()) {
                    listOf( ValidationReport.Message.info("There are no rules missing examples") )
                } else {
                    listOf(
                        ValidationReport.Message.error("There are ${emptyRules.size} Rules missing Examples"),
                    ) + emptyRules.map {
                        ValidationReport.Message.debug("Rule ${it.id.value}")
                    }
                }
            )
        }

    private fun nonNullPhoneticSpelling(rules: List<GrammarRule>): ValidationReport? =
        rules.flatMap { rule ->
            rule.examples.filter { example ->
                example.text.phoneticSpelling == null
            }
        }.let { nullExamples ->
            ValidationReport.Group(
                name = "Missing Phonetic Spellings",
                messages = if (nullExamples.isEmpty()) {
                    listOf( ValidationReport.Message.info("There are no missing phonetic spellings") )
                } else {
                    listOf(
                        ValidationReport.Message.error("There are ${nullExamples.size} missing phonetic spellings from Examples"),
                        ValidationReport.Message.info("These are usually due to a mistake in the furigana template")
                    ) + nullExamples.map {
                        ValidationReport.Message.debug("Example ${it.id.value}: ${it.text.furiganaTemplate}")
                    }
                }
            )
        }

    private fun titlePlaceholderCharacterReport(rules: List<GrammarRule>): ValidationReport? =
        rules.filter { rule ->
            rule.title.preferredSpelling.value.contains("[A-Z]".toRegex())
        }.let { illegalRules ->
            ValidationReport.Group(
                name = "Rule Titles Expect Full-Width Placeholders",
                messages = if (illegalRules.isEmpty()) {
                    listOf( ValidationReport.Message.info("All titles have valid width placeholders") )
                } else {
                    listOf(
                        ValidationReport.Message.error("There are ${illegalRules.size} rules with illegal placeholders")
                    ) + illegalRules.map {
                        ValidationReport.Message.debug("Rule ${it.id.value}: ${it.title.preferredSpelling}")
                    }
                }
            )
        }

    private fun meaningPlaceholderCharacterReport(rules: List<GrammarRule>): ValidationReport? =
        rules.filter { rule ->
            rule.meaning.contains("[Ａ-Ｚ]".toRegex())
        }.let { illegalRules ->
            ValidationReport.Group(
                name = "Rule Meanings Expect Normal-Width Placeholders",
                messages = if (illegalRules.isEmpty()) {
                    listOf( ValidationReport.Message.info("All titles have valid width placeholders") )
                } else {
                    listOf(
                        ValidationReport.Message.error("There are ${illegalRules.size} rules with illegal placeholders")
                    ) + illegalRules.map {
                        ValidationReport.Message.debug("Rule ${it.id.value}: ${it.meaning}")
                    }
                }
            )
        }

}