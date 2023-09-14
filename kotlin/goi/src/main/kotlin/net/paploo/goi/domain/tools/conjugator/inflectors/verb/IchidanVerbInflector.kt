package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

class IchidanVerbInflector : StandardVerbInflector() {
    private val validEndingsRegex = "(る)$".toRegex()

    override val positivePlainPresent =
        Rewriter.replacement(validEndingsRegex, "る") //By using a matcher, we validate it matches the pattern
    override val positivePlainPast = Rewriter.replacement(validEndingsRegex, "た")
    override val positivePlainTe = Rewriter.replacement(validEndingsRegex, "て")

    override val negativePlainPresent = Rewriter.replacement(validEndingsRegex, "ない")
    override val negativePlainPast = Rewriter.replacement(validEndingsRegex, "なかった")
    override val negativePlainTe = Rewriter.replacement(validEndingsRegex, "なくて")

    override val positivePolitePresent = Rewriter.replacement(validEndingsRegex, "ます")
    override val positivePolitePast = Rewriter.replacement(validEndingsRegex, "ました")

    override val negativePolitePresent = Rewriter.replacement(validEndingsRegex, "ません")
    override val negativePolitePast = Rewriter.replacement(validEndingsRegex, "ませんでした")

    override val positivePlainPotential = Rewriter.replacement(validEndingsRegex, "られる")

    companion object {
        val default: IchidanVerbInflector = IchidanVerbInflector()
    }
}