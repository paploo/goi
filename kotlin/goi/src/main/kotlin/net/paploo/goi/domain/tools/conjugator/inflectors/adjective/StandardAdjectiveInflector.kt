package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.*
import net.paploo.goi.domain.tools.conjugator.Rewriter
import net.paploo.goi.domain.tools.conjugator.plus

abstract class StandardAdjectiveInflector : AdjectiveInflector {

    // Fill in 〜たら rules from https://www.tofugu.com/japanese-grammar/conditional-form-tara/

    override val positivePlainTaraConditional: Rewriter by lazy {
        positivePlainPast + Rewriter { Result.success(it + "ら") }
    }

    override val negativePlainTaraConditional: Rewriter by lazy {
        negativePlainPast + Rewriter { Result.success(it + "ら") }
    }



    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) -> positivePlainTe

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) -> negativePlainPast
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) -> negativePlainTe

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) -> positivePlainTaraConditional
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) -> negativePlainTaraConditional

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) -> positivePlainEbaConditional
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) -> negativePlainEbaConditional

        else -> null
    }

}
