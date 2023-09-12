package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.Identifiable
import net.paploo.goi.common.Identifier
import net.paploo.goi.common.Valued
import net.paploo.goi.domain.data.common.JlptLevel
import net.paploo.goi.domain.data.source.Lesson
import java.time.LocalDate
import java.util.*

data class Vocabulary(
    override val id: Id,
    val wordClass: WordClass,
    val conjugationKind: ConjugationKind?,
    val jlptLevel: JlptLevel?,
    val rowNum: Int?,
    val dateAdded: LocalDate?,
    val references: List<Lesson>, //TODO: should this be an enum or a data class with a code? Should we do sources with lessons in a hierarchy?
    val tags: Collection<Tag>,
) : Identifiable<Vocabulary.Id> {

    @JvmInline
    value class Id(override val value: UUID) : Identifier<UUID>

    @JvmInline
    value class Tag(override val value: String) : Valued<String>

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