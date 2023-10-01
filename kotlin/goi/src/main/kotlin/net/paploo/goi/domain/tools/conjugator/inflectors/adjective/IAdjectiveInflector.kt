package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.tools.conjugator.Rewriter

class IAdjectiveInflector : StandardAdjectiveInflector() {

    private val validEndingsRegex = "(い)$".toRegex()

    override val positivePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "い")
    override val positivePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "かった")
    override val positivePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "くて")

    override val negativePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "くない")
    override val negativePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "くなかった")
    override val negativePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "くなくて")

    companion object {
        val default: IAdjectiveInflector = IAdjectiveInflector()
    }

}
