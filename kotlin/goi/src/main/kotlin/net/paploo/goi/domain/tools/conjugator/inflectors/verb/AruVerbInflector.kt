package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

class AruVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex = "(ある)$".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "ある")
    override val positivePlainPast = Rewriter.replace(validEndingsRegex, "あった")
    override val positivePlainTe = Rewriter.replace(validEndingsRegex, "あって")

    override val negativePlainPresent = Rewriter.replace(validEndingsRegex, "ない")
    override val negativePlainPast = Rewriter.replace(validEndingsRegex, "なかった")
    override val negativePlainTe = Rewriter.replace(validEndingsRegex, "なくて")

    override val positivePolitePresent = Rewriter.replace(validEndingsRegex, "あります")
    override val positivePolitePast = Rewriter.replace(validEndingsRegex, "ありました")

    override val negativePolitePresent = Rewriter.replace(validEndingsRegex, "ありません")
    override val negativePolitePast = Rewriter.replace(validEndingsRegex, "ありませんでした")

    override val positivePlainPotential = Rewriter.replace(validEndingsRegex, "ありえる")

    override val positivePlainVolitional = Rewriter.replace(validEndingsRegex, "あろう")
    override val positivePoliteVolitional = Rewriter.replace(validEndingsRegex, "ありましょう")

    override val positivePlainPassive = Rewriter.replace(validEndingsRegex, "あられる")

    override val positivePlainCausative = Rewriter.replace(validEndingsRegex, "あらせる")

    companion object {
        val default: AruVerbInflector = AruVerbInflector()
    }

}
