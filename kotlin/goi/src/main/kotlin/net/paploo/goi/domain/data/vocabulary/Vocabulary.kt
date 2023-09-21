package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.interfaces.Identifiable
import net.paploo.goi.common.interfaces.Identifier
import net.paploo.goi.domain.data.common.*
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.data.source.Referencable
import java.time.LocalDate
import java.util.*

data class Vocabulary(
    override val id: Id,
    val wordClass: WordClass,
    val conjugationKind: ConjugationKind?,
    val preferredDefinition: Definition,
    val preferredWritten: FuriganaString,
    val altPhoneticSpelling: Spelling?,
    val kanjiSpelling: Spelling?,
    val conjugations: Collection<Conjugation>?, //We don't forbid multiple conjugations for an inflection, some verbs have multiples; first in list takes priority
    val jlptLevel: JlptLevel?,
    val rowNumber: Int,
    val dateAdded: LocalDate,
    override val references: Set<Lesson.Code>,
    override val tags: Set<Tag>,
) : Identifiable<Vocabulary.Id>, Referencable, Tagable {

    //TODO: Move this validation to a more generalized validation layer and/or a custom validation exception type.
    init {
        assert(wordClass.isConjugable && conjugations != null) {
            "Expected vocabulary $id to have conjugations."
        }

        assert(wordClass.isConjugable && (conjugationKind?.associatedWordClasses?.contains(wordClass) ?: false)) {
            "Expected vocabulary $id to have a legal conjugation kind but instead has $conjugationKind."
        }

        assert(!(wordClass.isConjugable) && conjugationKind == null && conjugations == null) {
            "Expected vocabulary $id to have no conjugationKind and no conjugations."
        }
    }

    @JvmInline
    value class Id(override val value: UUID) : Identifier<UUID>

    val isConjugable = conjugationKind != null

    enum class WordClass {
        Noun,
        ProperNoun,
        Pronoun,
        Adjective,
        Verb,
        Adverb,
        Preposition,
        Interjection,
        Conjunction,
        Particle,
        Expression,
        Contraction,
        Counter,
        Suffix,
        AuxiliaryAdjective,
        PreNounAdjectival,
        Auxiliary,
        Prefix,
        Punctuation,
    }

    enum class ConjugationKind(val associatedWordClasses: Set<WordClass>) {
        GodanVerb(setOf(WordClass.Verb)),
        IchidanVerb(setOf(WordClass.Verb)),
        SuruVerb(setOf(WordClass.Verb)),
        KuruVerb(setOf(WordClass.Verb)),
        IkuVerb(setOf(WordClass.Verb)),
        AruVerb(setOf(WordClass.Verb)),
        CopulaVerb(setOf(WordClass.Verb)),
        AiSuruVerb(setOf(WordClass.Verb)),
        IAdjective(setOf(WordClass.Adjective, WordClass.AuxiliaryAdjective)),
        NaAdjective(setOf(WordClass.Adjective, WordClass.AuxiliaryAdjective)),
        YoiAdjective(setOf(WordClass.Adjective, WordClass.AuxiliaryAdjective)),
    }
}

val Vocabulary.WordClass.isConjugable: Boolean get() = Vocabulary.ConjugationKind.entries.any { this in it.associatedWordClasses }