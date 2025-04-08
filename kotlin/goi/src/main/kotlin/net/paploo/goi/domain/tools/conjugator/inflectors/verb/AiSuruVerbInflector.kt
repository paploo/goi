package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Rewriter
import net.paploo.goi.domain.tools.conjugator.plus

class AiSuruVerbInflector : StandardVerbInflector() {
    private val suruVerbInflector = SuruVerbInflector.default

    override val positivePlainPresent: Rewriter = suruVerbInflector.positivePlainPresent
    override val positivePlainPast: Rewriter = suruVerbInflector.positivePlainPast
    override val positivePlainTe: Rewriter = suruVerbInflector.positivePlainTe

    //override val negativePlainPresent: Rewriter = Rewriter.replace("(する)$".toRegex(), "さない")
    override val negativePlainPresent: Rewriter = suruVerbInflector.negativePlainPresent + Rewriter.replace("(しない)$".toRegex(), "さない")
    override val negativePlainPast: Rewriter = suruVerbInflector.negativePlainPast
    override val negativePlainTe: Rewriter = suruVerbInflector.negativePlainTe

    override val positivePolitePresent: Rewriter = suruVerbInflector.positivePolitePresent
    override val positivePolitePast: Rewriter = suruVerbInflector.positivePolitePast

    override val negativePolitePresent: Rewriter = suruVerbInflector.negativePolitePresent
    override val negativePolitePast: Rewriter = suruVerbInflector.negativePolitePast

    override val positivePlainPotential: Rewriter = suruVerbInflector.positivePlainPotential

    override val positivePlainVolitional: Rewriter = suruVerbInflector.positivePlainVolitional
    override val positivePoliteVolitional: Rewriter = suruVerbInflector.positivePoliteVolitional

    override val negativePlainEbaConditional: Rewriter = suruVerbInflector.negativePlainPresent + Rewriter.replace("(い)$".toRegex(), "ければ")

    override val positivePlainPassive: Rewriter = suruVerbInflector.positivePlainPassive

    companion object {
        val default: AiSuruVerbInflector = AiSuruVerbInflector()
    }
}