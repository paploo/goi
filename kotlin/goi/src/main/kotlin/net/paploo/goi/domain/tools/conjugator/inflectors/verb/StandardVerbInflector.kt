package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.Rewriter
import net.paploo.goi.domain.tools.conjugator.plus

/**
 * A standardized implementation of VerbInflector.
 *
 * This:
 * 1. handles the `invoke` method for you, and
 * 2. Gives default implementations for derived conjugations where possible
 *    - for example, potential forms are all ichidan conjugations of the positive plain potential
 */
abstract class StandardVerbInflector : VerbInflector {

    override val negativePlainPotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.negativePlainPresent
    }

    override val positivePolitePotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.positivePolitePresent
    }

    override val negativePolitePotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.negativePolitePresent
    }

    // Fill in 〜たら rules from https://www.tofugu.com/japanese-grammar/conditional-form-tara/

    override val positivePlainTaraConditional: Rewriter by lazy {
        positivePlainPast + Rewriter { Result.success(it + "ら") }
    }

    override val negativePlainTaraConditional: Rewriter by lazy {
        negativePlainPast + Rewriter { Result.success(it + "ら") }
    }

    override val positivePoliteTaraConditional: Rewriter by lazy {
        positivePolitePast + Rewriter { Result.success(it + "ら") }
    }

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
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) -> negativePlainPotential
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) -> positivePolitePotential
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) -> negativePolitePotential

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) -> positivePlainVolitional
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) -> positivePoliteVolitional

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) -> positivePlainTaraConditional
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) -> negativePlainTaraConditional
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) -> positivePoliteTaraConditional

        //TODO
//        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) -> positivePlainEbaConditional
//        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) -> negativePlainEbaConditional

        else -> null
    }

}
