package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.Identifiable
import net.paploo.goi.common.Identifier
import net.paploo.goi.domain.data.common.JlptLevel
import net.paploo.goi.domain.data.common.Tag
import net.paploo.goi.domain.data.common.Tagable
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.data.source.Referencable
import java.time.LocalDate
import java.util.*

data class Vocabulary(
    override val id: Id,
    val wordClass: WordClass,
    val conjugationKind: ConjugationKind?,
    val jlptLevel: JlptLevel?,
    val rowNumber: Int,
    val dateAdded: LocalDate,
    override val references: Set<Lesson>,
    override val tags: Set<Tag>,
) : Identifiable<Vocabulary.Id>, Referencable, Tagable {

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