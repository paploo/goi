package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.Inflector
import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Type-safe interface for all (supported) adjective inflections.
 */
interface AdjectiveInflector : Inflector {
    val positivePlainPresent: Rewriter
    val positivePlainPast: Rewriter
    val positivePlainTe: Rewriter

    val negativePlainPresent: Rewriter
    val negativePlainPast: Rewriter
    val negativePlainTe: Rewriter

    val positivePlainTaraConditional: Rewriter
    val negativePlainTaraConditional: Rewriter

    val positivePlainEbaConditional: Rewriter
    val negativePlainEbaConditional: Rewriter

    companion object {

        val supportedInflections: Set<Conjugation.Inflection> = setOf(
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present),
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past),
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te),

            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba),
        )

    }
}
