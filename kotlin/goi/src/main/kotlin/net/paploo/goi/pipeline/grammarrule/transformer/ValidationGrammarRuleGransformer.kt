package net.paploo.goi.pipeline.grammarrule.transformer

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Transformer

/**
 * TODO: Things to validate (because they are mistakes I actually make):
 * - All IDs are non-zero.
 * - Test that the example phonetic spelling is non-null (this almost always means we're missing template values)!
 * - All dates are after 2020-01-01
 * - All rules have examples
 * - The rule titles only have full-width characters
 * - The rule meanings do NOT have full-width characters
 */
class ValidationGrammarRuleGransformer : Transformer<List<GrammarRule>> {

    override suspend fun invoke(rules: List<GrammarRule>, context: Context): Result<List<GrammarRule>> =
        Result.success(rules) //TODO: Implement validation.

}