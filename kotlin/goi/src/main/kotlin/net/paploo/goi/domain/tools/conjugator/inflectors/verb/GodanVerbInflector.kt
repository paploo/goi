package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.GodanRewriteRule
import net.paploo.goi.domain.tools.conjugator.Rewriter

class GodanVerbInflector : StandardVerbInflector() {
    private val validEndingsRegex: Regex = """([${validDictionaryEndings.joinToString("")}])$""".toRegex()

    override val positivePlainPresent = Rewriter.replacement(validEndingsRegex, "$1")

    override val positivePlainPast = GodanRewriteRule(
        uRepl = "った",
        kuRepl = "いた",
        guRepl = "いだ",
        suRepl = "した",
        tsuRepl = "った",
        nuRepl = "んだ",
        buRepl = "んだ",
        muRepl = "んだ",
        ruRepl = "った",
    )

    override val positivePlainTe = Rewriter.identity

    override val negativePlainPresent = Rewriter.identity

    override val negativePlainPast = Rewriter.identity

    override val negativePlainTe = Rewriter.identity

    override val positivePolitePresent = Rewriter.identity

    override val positivePolitePast = Rewriter.identity

    override val negativePolitePresent = Rewriter.identity

    override val negativePolitePast = Rewriter.identity

    override val positivePlainPotential = Rewriter.identity

    companion object {
        val default: GodanVerbInflector by lazy { GodanVerbInflector() }

        val validDictionaryEndings: List<String> = listOf("う", "く", "ぐ", "す", "つ", "ぬ", "ぶ", "む", "る")
    }

}