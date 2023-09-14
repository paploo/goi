package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.GodanVerbInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.IchidanVerbInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.KuruVerbInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.SuruVerbInflector

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
        Vocabulary.ConjugationKind.IkuVerb -> null
        Vocabulary.ConjugationKind.AruVerb -> null
        Vocabulary.ConjugationKind.CopulaVerb -> null
        Vocabulary.ConjugationKind.AiSuruVerb -> null

        Vocabulary.ConjugationKind.IAdjective -> null
        Vocabulary.ConjugationKind.NaAdjective -> null
        Vocabulary.ConjugationKind.YoiAdjective -> null
    }
}

