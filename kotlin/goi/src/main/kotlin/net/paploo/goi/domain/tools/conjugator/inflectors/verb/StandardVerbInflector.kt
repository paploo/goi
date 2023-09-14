package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.Rewriter
import net.paploo.goi.domain.tools.conjugator.getOrThrow
import net.paploo.goi.domain.tools.conjugator.plus

abstract class StandardVerbInflector : VerbInflector {

    override val negativePlainPotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.getOrThrow(
            Conjugation.Inflection(
                Charge.Negative,
                Politeness.Plain,
                Form.Present
            )
        )
    }

    override val positivePolitePotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.getOrThrow(
            Conjugation.Inflection(
                Charge.Positive,
                Politeness.Polite,
                Form.Present
            )
        )
    }

    override val negativePolitePotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.getOrThrow(
            Conjugation.Inflection(
                Charge.Negative,
                Politeness.Polite,
                Form.Present
            )
        )
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

        else -> null
    }

}
