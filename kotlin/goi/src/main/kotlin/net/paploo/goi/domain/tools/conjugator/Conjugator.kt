package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary

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
        Vocabulary.ConjugationKind.GodanVerb -> null
        Vocabulary.ConjugationKind.IchidanVerb -> IchidanVerbInflector
        Vocabulary.ConjugationKind.SuruVerb -> null
        Vocabulary.ConjugationKind.KuruVerb -> null
        Vocabulary.ConjugationKind.IkuVerb -> null
        Vocabulary.ConjugationKind.AruVerb -> null
        Vocabulary.ConjugationKind.CopulaVerb -> null
        Vocabulary.ConjugationKind.AiSuruVerb -> null

        Vocabulary.ConjugationKind.IAdjective -> null
        Vocabulary.ConjugationKind.NaAdjective -> null
        Vocabulary.ConjugationKind.YoiAdjective -> null
    }
}
