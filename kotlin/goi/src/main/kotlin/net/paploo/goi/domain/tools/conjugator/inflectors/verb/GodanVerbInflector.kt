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

    override val positivePolitePresent = GodanRewriteRule.stemWithSuffix("ます")

    override val positivePolitePast = GodanRewriteRule.stemWithSuffix("ました")

    override val negativePolitePresent = GodanRewriteRule.stemWithSuffix("ません")

    override val negativePolitePast = GodanRewriteRule.stemWithSuffix("ませんでした")

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

    override val positivePlainVolitional = GodanRewriteRule(
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

    override val positivePoliteVolitional = GodanRewriteRule.stemWithSuffix("ましょう")

    override val positivePlainPassive = GodanRewriteRule(
        uRepl = "われる",
        kuRepl = "かれる",
        guRepl = "がれる",
        suRepl = "される",
        tsuRepl = "たれる",
        nuRepl = "なれる",
        buRepl = "ばれる",
        muRepl = "まれる",
        ruRepl = "られる",
    )

    override val positivePlainCausative = GodanRewriteRule(
        uRepl = "わせる",
        kuRepl = "かせる",
        guRepl = "がせる",
        suRepl = "させる",
        tsuRepl = "たせる",
        nuRepl = "なせる",
        buRepl = "ばせる",
        muRepl = "ませる",
        ruRepl = "らせる",
    )

    companion object {
        val validDictionaryEndings: List<String> = listOf("う", "く", "ぐ", "す", "つ", "ぬ", "ぶ", "む", "る")

        val default: GodanVerbInflector = GodanVerbInflector()
    }

}