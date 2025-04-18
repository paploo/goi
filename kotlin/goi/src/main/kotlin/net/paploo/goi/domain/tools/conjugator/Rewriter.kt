package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.common.extensions.contains
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.GodanVerbInflector

interface Rewriter : (String) -> Result<String> {

    override fun invoke(dictionaryValue: String): Result<String>

    companion object {

        operator fun invoke(function: (String) -> Result<String>): Rewriter = FunctionRewriter(function)

        val identity: Rewriter = IdentityRewriter

        fun join(first: Rewriter, second: Rewriter): Rewriter = JoinRewriter(first, second)

        fun replace(pattern: Regex, replacement: String): Rewriter = PatternRewriter(pattern, replacement)

    }

    private class FunctionRewriter(
        val function: (String) -> Result<String>
    ) : Rewriter {
        override fun invoke(dictionaryValue: String): Result<String> =
            function(dictionaryValue)
    }

    private class JoinRewriter(
        val first: Rewriter,
        val second: Rewriter
    ) : Rewriter {
        override fun invoke(dictionaryValue: String): Result<String> =
            first(dictionaryValue).flatMap { second(it) }
    }

    private object IdentityRewriter : Rewriter {
        override fun invoke(dictionaryValue: String): Result<String> = Result.success(dictionaryValue)
    }

    private class PatternRewriter(
        val pattern: Regex,
        val replacement: String
    ) : Rewriter {
        override fun invoke(dictionaryValue: String): Result<String> =
            if (dictionaryValue in pattern) Result.runCatching { dictionaryValue.replace(pattern, replacement) }
            else Result.failure(IllegalArgumentException("Cannot rewrite: no match for '$dictionaryValue' using pattern '$pattern'"))
    }

}

operator fun Rewriter.plus(other: Rewriter): Rewriter = Rewriter.join(this, other)

/**
 * Typesafe re-write rule for all possible godan verb endings.
 */
class GodanRewriteRule(
    private val uRepl: String,
    private val kuRepl: String,
    private val guRepl: String,
    private val suRepl: String,
    private val tsuRepl: String,
    private val nuRepl: String,
    private val buRepl: String,
    private val muRepl: String,
    private val ruRepl: String,
) : Rewriter {

    private val validPatterns: Map<String, Regex> = GodanVerbInflector.validDictionaryEndings.associateWith { "($it)$".toRegex() }

    private val replacements: Map<String, String> = mapOf(
        "う" to uRepl,
        "く" to kuRepl,
        "ぐ" to guRepl,
        "す" to suRepl,
        "つ" to tsuRepl,
        "ぬ" to nuRepl,
        "ぶ" to buRepl,
        "む" to muRepl,
        "る" to ruRepl,
    )
    override fun invoke(dictionaryValue: String): Result<String> =
        validPatterns.entries.find { (_, regex) -> dictionaryValue in regex }?.let { (ending, regex) ->
            replacements[ending]?.let { repl ->
                Rewriter.replace(regex, repl)(dictionaryValue)
            }
        } ?: Result.failure(IllegalArgumentException("Invalid ending on godan verb '$dictionaryValue'"))

    companion object {

        /**
         *  Makes a RewriteRule that is the stem form plus a suffix.
         *
         *  No suffix would yield the stem itself.
         *  **/
        fun stemWithSuffix(suffix: String): GodanRewriteRule = GodanRewriteRule(
            uRepl = "い$suffix",
            kuRepl = "き$suffix",
            guRepl = "ぎ$suffix",
            suRepl = "し$suffix",
            tsuRepl = "ち$suffix",
            nuRepl = "に$suffix",
            buRepl = "び$suffix",
            muRepl = "み$suffix",
            ruRepl = "り$suffix",
        )

    }

}