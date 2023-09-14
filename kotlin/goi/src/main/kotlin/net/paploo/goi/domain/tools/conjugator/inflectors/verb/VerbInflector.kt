package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.Inflector
import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Type-safe interface for all (supported) verb inflections.
 */
interface VerbInflector : Inflector {
    val positivePlainPresent: Rewriter
    val positivePlainPast: Rewriter
    val positivePlainTe: Rewriter

    val negativePlainPresent: Rewriter
    val negativePlainPast: Rewriter
    val negativePlainTe: Rewriter

    val positivePolitePresent: Rewriter
    val positivePolitePast: Rewriter

    val negativePolitePresent: Rewriter
    val negativePolitePast: Rewriter

    val positivePlainPotential: Rewriter
    val negativePlainPotential: Rewriter
    val positivePolitePotential: Rewriter
    val negativePolitePotential: Rewriter

    companion object {

        val supportedInflections: Set<Conjugation.Inflection> = setOf(
           Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present),
           Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past),
           Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te),
           Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present),
           Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past),
           Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te),
           Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present),
           Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past),
           Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present),
           Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past),
           Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential),
           Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential),
           Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential),
           Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential),
        )

    }
}
