package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter

class CopulaVerbInflector : StandardVerbInflector() {
    private val validEndingsRegex = "(です)$".toRegex()

    override val positivePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "だ")
    override val positivePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "だった")
    override val positivePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "で")

    override val negativePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "じゃない")
    override val negativePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "じゃなかった")
    override val negativePlainTe: Rewriter = noConjugationRewriter("negative plain te")

    override val positivePolitePresent: Rewriter = Rewriter.replace(validEndingsRegex, "です")
    override val positivePolitePast: Rewriter = Rewriter.replace(validEndingsRegex, "でした")

    override val negativePolitePresent: Rewriter = Rewriter.replace(validEndingsRegex, "じゃないです")
    override val negativePolitePast: Rewriter = Rewriter.replace(validEndingsRegex, "じゃないかったです")

    override val positivePlainPotential: Rewriter = noConjugationRewriter("positive plain potential")
    override val negativePlainPotential: Rewriter = noConjugationRewriter("negative plain potential")
    override val positivePolitePotential: Rewriter = noConjugationRewriter("positive polite potential")
    override val negativePolitePotential: Rewriter = noConjugationRewriter("negative polite potential")

    private fun noConjugationRewriter(inflectionName: String): Rewriter = Rewriter {
        Result.failure(IllegalArgumentException("There is no valid copula conjugation for $inflectionName"))
    }

    companion object {
        val default: CopulaVerbInflector = CopulaVerbInflector()
    }
}
