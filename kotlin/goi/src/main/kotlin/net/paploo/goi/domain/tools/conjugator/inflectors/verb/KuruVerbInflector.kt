package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Inflector for 来る
 *
 * Note that there are two possible ways to inflect:
 * 1. Always convert to kana, and
 * 2. Preserve the kanji form if used.
 *
 * While the second is more correct for literary needs, the former is more useful for my purposes,
 * namely making flashcards, where drilling the phonetics are more important.
 *
 * TODO: Have an enum class Mode, handle both modes, but make converting to kana the default
 */
class KuruVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex = "([く来]る)$".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "くる")
    override val positivePlainPast = Rewriter.replace(validEndingsRegex, "きた")
    override val positivePlainTe = Rewriter.replace(validEndingsRegex, "きて")

    override val negativePlainPresent = Rewriter.replace(validEndingsRegex, "こない")
    override val negativePlainPast = Rewriter.replace(validEndingsRegex, "こなかった")
    override val negativePlainTe = Rewriter.replace(validEndingsRegex, "こなくて")

    override val positivePolitePresent = Rewriter.replace(validEndingsRegex, "きます")
    override val positivePolitePast = Rewriter.replace(validEndingsRegex, "きました")

    override val negativePolitePresent = Rewriter.replace(validEndingsRegex, "きません")
    override val negativePolitePast = Rewriter.replace(validEndingsRegex, "きませんでした")

    override val positivePlainPotential = Rewriter.replace(validEndingsRegex, "こられる")

    override val positivePlainVolitional = Rewriter.replace(validEndingsRegex, "こよう")
    override val positivePoliteVolitional = Rewriter.replace(validEndingsRegex, "きましょう")

    override val positivePlainPassive = Rewriter.replace(validEndingsRegex, "こられる")

    companion object {
        val default: KuruVerbInflector = KuruVerbInflector()
    }

}