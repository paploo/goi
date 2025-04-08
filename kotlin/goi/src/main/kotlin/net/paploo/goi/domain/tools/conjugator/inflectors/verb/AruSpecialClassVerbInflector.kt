package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.GodanRewriteRule
import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Inflector for a number of honorific verb types that have a special masu form using い instead of る.
 * These are known as "aru special class" conjugation.
 *
 * See https://en.wikipedia.org/wiki/Japanese_irregular_verbs#Polite_verbs
 */
class AruSpecialClassVerbInflector : StandardVerbInflector() {

    private val validEndingsRegex = "(る)$".toRegex()

    override val positivePlainPresent = Rewriter.replace(validEndingsRegex, "$1")
    override val positivePlainPast = Rewriter.replace(validEndingsRegex, "った")
    override val positivePlainTe = Rewriter.replace(validEndingsRegex, "って")

    override val negativePlainPresent = Rewriter.replace(validEndingsRegex, "らない")
    override val negativePlainPast = Rewriter.replace(validEndingsRegex, "らなかった")
    override val negativePlainTe = Rewriter.replace(validEndingsRegex, "らなくて")

    override val positivePolitePresent = Rewriter.replace(validEndingsRegex, "います")
    override val positivePolitePast = Rewriter.replace(validEndingsRegex, "いました")

    override val negativePolitePresent = Rewriter.replace(validEndingsRegex, "いません")
    override val negativePolitePast = Rewriter.replace(validEndingsRegex, "いませんでした")

    override val positivePlainPotential = Rewriter.replace(validEndingsRegex, "れる")

    override val positivePlainVolitional = Rewriter.replace(validEndingsRegex, "ろう")
    override val positivePoliteVolitional = Rewriter.replace(validEndingsRegex, "いましょう")


    companion object {
        val default: AruSpecialClassVerbInflector = AruSpecialClassVerbInflector()
    }

}