package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

class SuruVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex = "(する)$".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "する")
    override val positivePlainPast = Rewriter.replace(validEndingsRegex, "した")
    override val positivePlainTe = Rewriter.replace(validEndingsRegex, "して")

    override val negativePlainPresent = Rewriter.replace(validEndingsRegex, "しない")
    override val negativePlainPast = Rewriter.replace(validEndingsRegex, "しなかった")
    override val negativePlainTe = Rewriter.replace(validEndingsRegex, "しなくて")

    override val positivePolitePresent = Rewriter.replace(validEndingsRegex, "します")
    override val positivePolitePast = Rewriter.replace(validEndingsRegex, "しました")

    override val negativePolitePresent = Rewriter.replace(validEndingsRegex, "しません")
    override val negativePolitePast = Rewriter.replace(validEndingsRegex, "しませんでした")

    override val positivePlainPotential = Rewriter.replace(validEndingsRegex, "できる")

    override val positivePlainVolitional = Rewriter.replace(validEndingsRegex, "しよう")
    override val positivePoliteVolitional = Rewriter.replace(validEndingsRegex, "しましょう")


    companion object {
        val default: SuruVerbInflector = SuruVerbInflector()
    }

}