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
    val preferredWritten: JpString,
    val altPhoneticSpelling: Spelling?,
    val kanjiSpelling: Spelling?,
    val conjugations: Collection<Conjugation>?,
    val jlptLevel: JlptLevel?,
    val rowNumber: Int,
    val dateAdded: LocalDate,
    override val references: Set<Lesson>,
    override val tags: Set<Tag>,
) : Identifiable<Vocabulary.Id>, Referencable, Tagable {

    //TODO: Move this validation to a more generalized validation layer and/or a custom validation exception type.
    init {
        assert(wordClass.isConjugable && conjugations != null) {
            "Expected vocabulary $id to have conjugations."
        }

        assert(wordClass.isConjugable && (conjugationKind?.associatedWordClass == wordClass)) {
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
        PreNounAdjectival,
        Auxiliary,
        Prefix,
        Punctuation,
    }

    enum class ConjugationKind(val associatedWordClass: WordClass) {
        IrregularVerb(WordClass.Verb),
        GodanVerb(WordClass.Verb),
        IchidanVerb(WordClass.Verb),
        IrregularAdjective(WordClass.Adjective),
        IAdjective(WordClass.Adjective),
        NaAdjective(WordClass.Adjective),
        SuruVerb(WordClass.Verb),
        YoiAdjective(WordClass.Adjective),
        KuruVerb(WordClass.Verb),
        IkuVerb(WordClass.Verb),
        AruVerb(WordClass.Verb),
        AiSuruVerb(WordClass.Verb),
        CopulaVerb(WordClass.Verb),
    }
}

val Vocabulary.WordClass.isConjugable: Boolean get() = Vocabulary.ConjugationKind.entries.any { it.associatedWordClass == this }