package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form

interface Inflector : (Conjugation.Inflection) -> Rewriter? {
    override fun invoke(inflection: Conjugation.Inflection): Rewriter?
}

/**
 * Internal method; this is useful when building inflectors from other inflectors, where there is
 * knowledge that a [Rewriter] exists but no good way to tell the compiler (without a lot of boilerplate).
 */
internal fun Inflector.getOrThrow(inflection: Conjugation.Inflection): Rewriter =
    invoke(inflection) ?: throw IllegalArgumentException("No Rewriter for inflection $inflection")


open class DelegatedInflector(inflector: Inflector): Inflector by inflector

/**
 * Type-safe mapping of the [Rewriter] instances expected for an adjective conjugation.
 */
private class AdjectiveStandardInflector(
    private val positivePlainPresent: Rewriter,
    private val positivePlainPast: Rewriter,
    private val positivePlainTe: Rewriter,

    private val negativePlainPresent: Rewriter,
    private val negativePlainPast: Rewriter,
    private val negativePlainTe: Rewriter,
) : Inflector {

    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) -> positivePlainTe

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) -> negativePlainPast
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) -> negativePlainTe

        else -> null
    }

}

/**
 * Type-safe mapping of the [Rewriter] instances expected for most adjectives.
 *
 * In some cases, a default may exist if none is provided.
 *
 * Note: This isn't all possible ones, just the ones that I've needed thus far.
 */
private class VerbStandardInflector(
    private val positivePlainPresent: Rewriter,
    private val positivePlainPast: Rewriter,
    private val positivePlainTe: Rewriter,

    private val negativePlainPresent: Rewriter,
    private val negativePlainPast: Rewriter,
    private val negativePlainTe: Rewriter,

    private val positivePolitePresent: Rewriter,
    private val positivePolitePast: Rewriter,

    private val negativePolitePresent: Rewriter,
    private val negativePolitePast: Rewriter,

    private val positivePlainPotential: Rewriter,
    private val negativePlainPotential: Rewriter = positivePlainPotential + IchidanVerbInflector.getOrThrow(Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present)),
    private val positivePolitePotential: Rewriter = positivePlainPotential + IchidanVerbInflector.getOrThrow(Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present)),
    private val negativePolitePotential: Rewriter = positivePlainPotential + IchidanVerbInflector.getOrThrow(Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present)),
) : Inflector {

    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) -> positivePlainTe

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) -> negativePlainPast
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) -> negativePlainTe

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) -> positivePolitePresent
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) -> positivePolitePast

        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) -> negativePolitePresent
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) -> negativePolitePast

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) -> positivePlainPotential
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) -> positivePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) -> positivePlainPast

        else -> null
    }

}

object IchidanVerbInflector : DelegatedInflector(
    "(る)＄".toRegex().let { verbRegex ->
        VerbStandardInflector(
            positivePlainPresent = Rewriter.replacement(verbRegex, "る"),
            positivePlainPast = Rewriter.replacement(verbRegex, "た"),
            positivePlainTe = Rewriter.replacement(verbRegex, "て"),

            negativePlainPresent = Rewriter.replacement(verbRegex, "ない"),
            negativePlainPast = Rewriter.replacement(verbRegex, "なかった"),
            negativePlainTe = Rewriter.replacement(verbRegex, "なくて"),

            positivePolitePresent = Rewriter.replacement(verbRegex, "ます"),
            positivePolitePast = Rewriter.replacement(verbRegex, "ました"),

            negativePolitePresent = Rewriter.replacement(verbRegex, "ません"),
            negativePolitePast = Rewriter.replacement(verbRegex, "ませんでした"),

            positivePlainPotential = Rewriter.replacement(verbRegex, "られる"),
        )   
    }
)