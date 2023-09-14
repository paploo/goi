package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.common.extensions.contains
import net.paploo.goi.common.extensions.flatMap

interface Rewriter : (String) -> Result<String> {

    override fun invoke(dictionaryValue: String): Result<String>

    companion object {
        operator fun invoke(function: (String) -> Result<String>): Rewriter = FunctionRewriter(function)
        fun join(first: Rewriter, second: Rewriter): Rewriter = JoinRewriter(first, second)
        fun replacement(pattern: Regex, replacement: String): Rewriter = PatternRewriter(pattern, replacement)
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
            first(dictionaryValue).flatMap { second.invoke(dictionaryValue) }
    }

    private class PatternRewriter(
        val pattern: Regex,
        val replacement: String
    ) : Rewriter {
        override fun invoke(dictionaryValue: String): Result<String> =
            if (dictionaryValue.matches(pattern)) Result.runCatching { dictionaryValue.replace(pattern, replacement) }
            else Result.failure(IllegalArgumentException("Cannot rewrite: no match for $dictionaryValue using pattern $pattern"))
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

    override fun invoke(dictionaryValue: String): Result<String> = when (dictionaryValue) {
        in "(う)$".toRegex() -> Result.success(uRepl)
        in "(く)$".toRegex() -> Result.success(kuRepl)
        in "(ぐ)$".toRegex() -> Result.success(guRepl)
        in "(す)$".toRegex() -> Result.success(suRepl)
        in "(ず)$".toRegex() -> Result.failure(IllegalArgumentException("Unknown ending 'ず' on godan verb '$dictionaryValue'"))
        in "(つ)$".toRegex() -> Result.success(tsuRepl)
        in "(づ)$".toRegex() -> Result.failure(IllegalArgumentException("Unknown ending 'づ' on godan verb '$dictionaryValue'"))
        in "(ぬ)$".toRegex() -> Result.success(nuRepl)
        in "(ふ)$".toRegex() -> Result.failure(IllegalArgumentException("Unknown ending 'ふ' on godan verb '$dictionaryValue'"))
        in "(ぶ)$".toRegex() -> Result.success(buRepl)
        in "(ぷ)$".toRegex() -> Result.failure(IllegalArgumentException("Unknown ending 'ぷ' on godan verb '$dictionaryValue'"))
        in "(む)$".toRegex() -> Result.success(muRepl)
        in "(る)$".toRegex() -> Result.success(ruRepl)
        else -> Result.failure(IllegalArgumentException("Invalid ending on godan verb '$dictionaryValue'"))
    }

    companion object {

        /**
         *  e.g. 話す goes to 話します, 話しません, etc, moving the s-row to the i-column but having a different (uniform)
         *  suffix across the different inflections; this gives an easy way to build those.
         *  **/
        fun iColumnWithSuffix(suffix: String): GodanRewriteRule = GodanRewriteRule(
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