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

    val positivePlainVolitional: Rewriter
    val positivePoliteVolitional: Rewriter //TODO: trivial ましょう

    val positivePlainTaraConditional: Rewriter
    val negativePlainTaraConditional: Rewriter
    val positivePoliteTaraConditional: Rewriter
    //val negativePoliteTaraConditional: Rewriter //Not usually used [https://www.tofugu.com/japanese-grammar/conditional-form-tara/]

    val positivePlainEbaConditional: Rewriter
    val negativePlainEbaConditional: Rewriter

    val positivePlainPassive: Rewriter
    val negativePlainPassive: Rewriter
    val positivePolitePassive: Rewriter
    val negativePolitePassive: Rewriter

    val positivePlainCausative: Rewriter
    val negativePlainCausative: Rewriter
    val positivePoliteCausative: Rewriter
    val negativePoliteCausative: Rewriter

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

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive),
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative),
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative),
        )

    }
}
