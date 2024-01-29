package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.GodanRewriteRule
import net.paploo.goi.domain.tools.conjugator.Rewriter

class GodanVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex: Regex = """([${validDictionaryEndings.joinToString("")}])$""".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "$1")

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

    override val positivePlainTe = GodanRewriteRule(
        uRepl = "って",
        kuRepl = "いて",
        guRepl = "いで",
        suRepl = "して",
        tsuRepl = "って",
        nuRepl = "んで",
        buRepl = "んで",
        muRepl = "んで",
        ruRepl = "って",
    )

    override val negativePlainPresent = GodanRewriteRule(
        uRepl = "わない",
        kuRepl = "かない",
        guRepl = "がない",
        suRepl = "さない",
        tsuRepl = "たない",
        nuRepl = "なない",
        buRepl = "ばない",
        muRepl = "まない",
        ruRepl = "らない",
    )

    override val negativePlainPast = GodanRewriteRule(
        uRepl = "わなかった",
        kuRepl = "かなかった",
        guRepl = "がなかった",
        suRepl = "さなかった",
        tsuRepl = "たなかった",
        nuRepl = "ななかった",
        buRepl = "ばなかった",
        muRepl = "まなかった",
        ruRepl = "らなかった",
    )

    override val negativePlainTe = GodanRewriteRule(
        uRepl = "わなくて",
        kuRepl = "かなくて",
        guRepl = "がなくて",
        suRepl = "さなくて",
        tsuRepl = "たなくて",
        nuRepl = "ななくて",
        buRepl = "ばなくて",
        muRepl = "まなくて",
        ruRepl = "らなくて",
    )

    override val positivePolitePresent = GodanRewriteRule.iColumnWithSuffix("ます")

    override val positivePolitePast = GodanRewriteRule.iColumnWithSuffix("ました")

    override val negativePolitePresent = GodanRewriteRule.iColumnWithSuffix("ません")

    override val negativePolitePast = GodanRewriteRule.iColumnWithSuffix("ませんでした")

    override val positivePlainPotential = GodanRewriteRule(
        uRepl = "える",
        kuRepl = "ける",
        guRepl = "げる",
        suRepl = "せる",
        tsuRepl = "てる",
        nuRepl = "ねる",
        buRepl = "べる",
        muRepl = "める",
        ruRepl = "れる",
    )

    override val positivePlainVolitional: Rewriter = GodanRewriteRule(
        uRepl = "おう",
        kuRepl = "こう",
        guRepl = "ごう",
        suRepl = "そう",
        tsuRepl = "とう",
        nuRepl = "のう",
        buRepl = "ぼう",
        muRepl = "もう",
        ruRepl = "ろう",
    )

    companion object {
        val validDictionaryEndings: List<String> = listOf("う", "く", "ぐ", "す", "つ", "ぬ", "ぶ", "む", "る")

        val default: GodanVerbInflector = GodanVerbInflector()
    }

}