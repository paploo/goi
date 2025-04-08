package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

class IchidanVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex = "(る)$".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "る")
    override val positivePlainPast = Rewriter.replace(validEndingsRegex, "た")
    override val positivePlainTe = Rewriter.replace(validEndingsRegex, "て")

    override val negativePlainPresent = Rewriter.replace(validEndingsRegex, "ない")
    override val negativePlainPast = Rewriter.replace(validEndingsRegex, "なかった")
    override val negativePlainTe = Rewriter.replace(validEndingsRegex, "なくて")

    override val positivePolitePresent = Rewriter.replace(validEndingsRegex, "ます")
    override val positivePolitePast = Rewriter.replace(validEndingsRegex, "ました")

    override val negativePolitePresent = Rewriter.replace(validEndingsRegex, "ません")
    override val negativePolitePast = Rewriter.replace(validEndingsRegex, "ませんでした")

    override val positivePlainPotential = Rewriter.replace(validEndingsRegex, "られる")

    override val positivePlainVolitional = Rewriter.replace(validEndingsRegex, "よう")
    override val positivePoliteVolitional = Rewriter.replace(validEndingsRegex, "ましょう")

    companion object {
        val default: IchidanVerbInflector = IchidanVerbInflector()
    }

}
