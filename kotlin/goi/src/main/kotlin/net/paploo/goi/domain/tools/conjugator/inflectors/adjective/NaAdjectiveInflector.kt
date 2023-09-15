package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.tools.conjugator.Rewriter

class NaAdjectiveInflector : StandardAdjectiveInflector() {

    private val validEndingsRegex = "$".toRegex()

    override val positivePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "だ")
    override val positivePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "だった")
    override val positivePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "で")

    override val negativePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "じゃない")
    override val negativePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "じゃなかった")
    override val negativePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "ではなくて")

    companion object {
        val default: NaAdjectiveInflector = NaAdjectiveInflector()
    }

}
