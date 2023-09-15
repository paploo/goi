package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.conjugator.inflectors.adjective.IAdjectiveInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.adjective.NaAdjectiveInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.adjective.YoiAdjectiveInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.*

interface Conjugator : (Vocabulary.ConjugationKind) -> Inflector? {
    override fun invoke(conjugationKind: Vocabulary.ConjugationKind): Inflector?

    companion object {
        val default: Conjugator = DefaultConjugator()

        operator fun invoke(): Conjugator = default
    }
}

fun Conjugator.apply(
    conjugationKind: Vocabulary.ConjugationKind,
    inflection: Conjugation.Inflection,
    dictionaryValue: String
): Result<String>? =
    invoke(conjugationKind)?.invoke(inflection)?.invoke(dictionaryValue)

class DefaultConjugator : Conjugator {

    override fun invoke(conjugationKind: Vocabulary.ConjugationKind): Inflector? = when (conjugationKind) {
        Vocabulary.ConjugationKind.GodanVerb -> GodanVerbInflector.default
        Vocabulary.ConjugationKind.IchidanVerb -> IchidanVerbInflector.default
        Vocabulary.ConjugationKind.SuruVerb -> SuruVerbInflector.default
        Vocabulary.ConjugationKind.KuruVerb -> KuruVerbInflector.default
        Vocabulary.ConjugationKind.IkuVerb -> IkuVerbInflector.default
        Vocabulary.ConjugationKind.AruVerb -> AruVerbInflector.default
        Vocabulary.ConjugationKind.CopulaVerb -> CopulaVerbInflector.default
        Vocabulary.ConjugationKind.AiSuruVerb -> AiSuruVerbInflector.default

        Vocabulary.ConjugationKind.IAdjective -> IAdjectiveInflector.default
        Vocabulary.ConjugationKind.NaAdjective -> NaAdjectiveInflector.default
        Vocabulary.ConjugationKind.YoiAdjective -> YoiAdjectiveInflector.default
    }
}

