package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.conjugator.Rewriter

abstract class StandardAdjectiveInflector : AdjectiveInflector {

    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) -> positivePlainPast
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) -> positivePlainTe

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) -> negativePlainPast
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) -> negativePlainTe

        else -> null
    }

}
