package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.GodanRewriteRule
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

    // Potential forms conjugate like a regular ichidan verb

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

    // Fill in the 〜ば rules using the existing short forms.

    override val positivePlainEbaConditional: Rewriter by lazy {
        // Interestingly, almost all verbs (including almost all irregular ones) fit this same godan-based rule.
        positivePlainPresent + GodanRewriteRule(
            uRepl = "えば",
            kuRepl = "けば",
            guRepl = "げば",
            suRepl = "せば",
            tsuRepl = "てば",
            nuRepl = "ねば",
            buRepl = "べば",
            muRepl = "めば",
            ruRepl = "れば",
        )
    }

    override val negativePlainEbaConditional: Rewriter by lazy {
        negativePlainPresent + Rewriter.replace("(い)$".toRegex(), "ければ")
    }

    // Passive forms conjugate like a regular ichidan verb

    override val negativePlainPassive by lazy {
        positivePlainPassive + IchidanVerbInflector.default.negativePlainPresent
    }

    override val positivePolitePassive by lazy {
        positivePlainPassive + IchidanVerbInflector.default.positivePolitePresent
    }

    override val negativePolitePassive by lazy {
        positivePlainPassive + IchidanVerbInflector.default.negativePolitePresent
    }

    override val positivePlainCausative by lazy { TODO("figure out impl") }
    override val negativePlainCausative by lazy { TODO("figure out impl") }
    override val positivePoliteCausative by lazy { TODO("figure out impl") }
    override val negativePoliteCausative by lazy { TODO("figure out impl") }

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

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) -> positivePlainEbaConditional
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) -> negativePlainEbaConditional

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) -> positivePlainPassive
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) -> negativePlainPassive
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) ->  positivePolitePassive
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) ->  negativePolitePassive

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) -> positivePlainCausative
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) -> negativePlainCausative
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) ->  positivePoliteCausative
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) ->  negativePoliteCausative

        else -> null
    }

}
