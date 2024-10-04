package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.conjugator.inflectors.adjective.IAdjectiveInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.adjective.NaAdjectiveInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.adjective.YoiAdjectiveInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.*

/**
 * Conjugators conjugate a verb for a conjugation kind and inflection.
 *
 * The rule is that conjugation should return a Result that:
 * 1. Return a success of a conjugation if there is a rule and it rewrites successfully.
 * 2. Return a success of a null if the inflection doesn't make sense for the value,
 *    OR there is no rule defined.
 * 3. Returns a failure if it tried to conjugate and failed.
 *
 * TODO: To differentiate 2, we might want to introduce a result object for the success cases that switches between:
 * 1. Matched(value)
 * 2. Unmatched
 * 3. IllegalConjugation //We don't error, you just can't do anything with it, just like Unmatched.
 * Alternatively, we have two states: Matched(value) and Umatched(reason) with a reason enum for NoRule or IllegalConjugation.
 */
interface Conjugator : (Vocabulary.ConjugationKind) -> Inflector? {
    override fun invoke(conjugationKind: Vocabulary.ConjugationKind): Inflector?

    companion object {
        val default: Conjugator = DefaultConjugator()

        operator fun invoke(): Conjugator = default
    }
}

operator fun Conjugator.invoke(
    conjugationKind: Vocabulary.ConjugationKind,
    inflection: Conjugation.Inflection,
    dictionaryValue: String
): Result<String>? =
    invoke(conjugationKind)?.invoke(inflection)?.invoke(dictionaryValue)

fun Conjugator.conjugate(
    conjugationKind: Vocabulary.ConjugationKind,
    inflection: Conjugation.Inflection,
    dictionaryValue: String
): Result<Conjugation>? =
    invoke(conjugationKind = conjugationKind, inflection = inflection, dictionaryValue = dictionaryValue)?.map {
        Conjugation(inflection = inflection, value = it)
    }

class DefaultConjugator : Conjugator {

    override fun invoke(conjugationKind: Vocabulary.ConjugationKind): Inflector? = when (conjugationKind) {
        Vocabulary.ConjugationKind.GodanVerb -> GodanVerbInflector.default
        Vocabulary.ConjugationKind.IchidanVerb -> IchidanVerbInflector.default
        Vocabulary.ConjugationKind.SuruVerb -> SuruVerbInflector.default
        Vocabulary.ConjugationKind.KuruVerb -> KuruVerbInflector.default
        Vocabulary.ConjugationKind.IkuVerb -> IkuVerbInflector.default
        Vocabulary.ConjugationKind.AruVerb -> AruVerbInflector.default
        Vocabulary.ConjugationKind.AruSpecialClassVerb -> AruSpecialClassVerbInflector.default
        Vocabulary.ConjugationKind.CopulaVerb -> CopulaVerbInflector.default
        Vocabulary.ConjugationKind.AiSuruVerb -> AiSuruVerbInflector.default

        Vocabulary.ConjugationKind.IAdjective -> IAdjectiveInflector.default
        Vocabulary.ConjugationKind.NaAdjective -> NaAdjectiveInflector.default
        Vocabulary.ConjugationKind.YoiAdjective -> YoiAdjectiveInflector.default
    }
}

