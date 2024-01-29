package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

class IkuVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex = "([い行])く$".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "$1く")
    override val positivePlainPast = Rewriter.replace(validEndingsRegex, "$1った")
    override val positivePlainTe = Rewriter.replace(validEndingsRegex, "$1って")

    override val negativePlainPresent = Rewriter.replace(validEndingsRegex, "$1かない")
    override val negativePlainPast = Rewriter.replace(validEndingsRegex, "$1かなかった")
    override val negativePlainTe = Rewriter.replace(validEndingsRegex, "$1かなくて")

    override val positivePolitePresent = Rewriter.replace(validEndingsRegex, "$1きます")
    override val positivePolitePast = Rewriter.replace(validEndingsRegex, "$1きました")

    override val negativePolitePresent = Rewriter.replace(validEndingsRegex, "$1きません")
    override val negativePolitePast = Rewriter.replace(validEndingsRegex, "$1きませんでした")

    override val positivePlainPotential = Rewriter.replace(validEndingsRegex, "$1ける")

    override val positivePlainVolitional: Rewriter = Rewriter.replace(validEndingsRegex, "$1こう")

    companion object {
        val default: IkuVerbInflector = IkuVerbInflector()
    }

}
