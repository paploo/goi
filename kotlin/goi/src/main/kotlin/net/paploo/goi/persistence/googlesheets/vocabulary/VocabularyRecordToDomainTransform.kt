package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.common.extensions.enumResultValueOf
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.mapFailure
import net.paploo.goi.common.extensions.toCamelCase
import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.domain.data.common.JlptLevel
import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.data.common.Tag
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Definition
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import java.time.LocalDate
import java.util.UUID

class VocabularyRecordToDomainTransform : (VocabularyCsvRecord) -> Result<Vocabulary> {

    override fun invoke(record: VocabularyCsvRecord): Result<Vocabulary> =
        VocabularyResultRecord(
            id = id(record),
            wordClass = wordClass(record),
            conjugationKind = conjugationKind(record),
            preferredDefinition = preferredDefinition(record),
            preferredWritten = preferredWritten(record),
            altPhoneticSpelling = altPhoneticSpelling(record),
            kanjiSpelling = kanjiSpelling(record),
            conjugations = conjugations(record),
            jlptLevel = jlptLevel(record),
            rowNumber = rowNumber(record),
            dateAdded = dateAdded(record),
            references = references(record),
            tags = tags(record),
        ).toDomain().mapFailure { err ->
            IllegalArgumentException("Encountered exception processing record: $record", err)
        }

    private data class VocabularyResultRecord(
        val id: Result<Vocabulary.Id>,
        val wordClass: Result<Vocabulary.WordClass>,
        val conjugationKind: Result<Vocabulary.ConjugationKind?>,
        val preferredDefinition: Result<Definition>,
        val preferredWritten: Result<FuriganaString>,
        val altPhoneticSpelling: Result<Spelling?>,
        val kanjiSpelling: Result<Spelling?>,
        val conjugations: Result<Collection<Conjugation>?>,
        val jlptLevel: Result<JlptLevel?>,
        val rowNumber: Result<Int>,
        val dateAdded: Result<LocalDate>,
        val references: Result<Set<Lesson>>,
        val tags: Result<Set<Tag>>,
    ) {

        //TODO: If we had a validation exception that could be composed of other exceptions, we could report all failures.
        fun toDomain(): Result<Vocabulary> = Result.runCatching {
            //Easier to runCatching than flatMap over this many rows!
            Vocabulary(
                id = id.getOrThrow(),
                wordClass = wordClass.getOrThrow(),
                conjugationKind = conjugationKind.getOrThrow(),
                preferredDefinition = preferredDefinition.getOrThrow(),
                preferredWritten = preferredWritten.getOrThrow(),
                altPhoneticSpelling = altPhoneticSpelling.getOrThrow(),
                kanjiSpelling = kanjiSpelling.getOrThrow(),
                conjugations = conjugations.getOrThrow(),
                jlptLevel = jlptLevel.getOrThrow(),
                rowNumber = rowNumber.getOrThrow(),
                dateAdded = dateAdded.getOrThrow(),
                references = references.getOrThrow(),
                tags = tags.getOrThrow(),
            )
        }

    }

    private fun id(record: VocabularyCsvRecord): Result<Vocabulary.Id> =
        record.getNotNull(VocabularyCsvRecord.Field.Id).mapCatching {
            Vocabulary.Id(UUID.fromString(it))
        }

    private fun wordClass(record: VocabularyCsvRecord): Result<Vocabulary.WordClass> =
        record.getNotNull(VocabularyCsvRecord.Field.WordClassCode).flatMap {
            enumResultValueOf<Vocabulary.WordClass>(it.toCamelCase())
        }

    private fun conjugationKind(record: VocabularyCsvRecord): Result<Vocabulary.ConjugationKind?> =
        record.get(VocabularyCsvRecord.Field.ConjugationKindCode).flatMap { code ->
            code?.let { enumResultValueOf<Vocabulary.ConjugationKind>(it.toCamelCase()) } ?: Result.success(null)
        }

    private fun preferredDefinition(record: VocabularyCsvRecord): Result<Definition> =
        record.getNotNull(VocabularyCsvRecord.Field.Definition).map {
            Definition(value = it, sortRank = 1) //The preferred is always rank 1.
        }

    private fun preferredWritten(record: VocabularyCsvRecord): Result<FuriganaString> =
        record.getNotNull(VocabularyCsvRecord.Field.PreferredSpelling).flatMap { preferred ->
            record.getNotNull(VocabularyCsvRecord.Field.PhoneticSpelling).map { phonetic ->
                FuriganaString(FuriganaTemplate.Ruby(
                    preferred = preferred,
                    phonetic = phonetic
                ))
            }
        }

    private fun altPhoneticSpelling(record: VocabularyCsvRecord): Result<Spelling?> =
        record.get(VocabularyCsvRecord.Field.AltPhonSpell).map { spelling ->
            spelling?.let { Spelling(it) }
        }

    private fun kanjiSpelling(record: VocabularyCsvRecord): Result<Spelling?> =
        record.get(VocabularyCsvRecord.Field.KanjiSpelling).map { spelling ->
            spelling?.let { Spelling(it) }
        }

    private fun conjugations(record: VocabularyCsvRecord): Result<Collection<Conjugation>?> =
        TODO()

    private fun jlptLevel(record: VocabularyCsvRecord): Result<JlptLevel?> =
        record.get(VocabularyCsvRecord.Field.JlptLevel).flatMap { levelString ->
            levelString?.let {lvl ->
                JlptLevel.entries.find {
                    it.value.toString() == lvl
                }?.let {
                    Result.success(it)
                } ?: Result.failure(NoSuchElementException("Invalid JLPT Level: $levelString"))
            } ?: Result.success(null)
        }

    private fun rowNumber(record: VocabularyCsvRecord): Result<Int> =
        TODO()

    private fun dateAdded(record: VocabularyCsvRecord): Result<LocalDate> =
        TODO()

    private fun references(record: VocabularyCsvRecord): Result<Set<Lesson>> =
        TODO()

    private fun tags(record: VocabularyCsvRecord): Result<Set<Tag>> =
        TODO()


}