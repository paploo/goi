package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.common.extensions.*
import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.domain.data.common.JlptLevel
import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.data.common.Tag
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Definition
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.data.vocabulary.isConjugable
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import java.time.LocalDate
import java.util.UUID

internal class VocabularyRecordToDomainTransform : (VocabularyCsvRecord) -> Result<Vocabulary> {

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
            IllegalArgumentException("Encountered exception converting record: $record;\n\treason: $err", err)
        }

    //This essentially can work like a one-off monadic collection object;
    //so that if I do more complex handling of the results I have a place to do it.
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
        val references: Result<Set<Lesson.Code>>,
        val tags: Result<Set<Tag>>,
    ) {

        //TODO: gather all the exceptions in to a multi-exception to report all at once.
        fun toDomain(): Result<Vocabulary> = Result.runCatching {
            //Easier to runCatching than flatMap over this many rows, but may have to switch
            //if I want to do anything more sophisticated.
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
            enumResultValueOf<Vocabulary.WordClass>(it.pascalCase())
        }

    private fun conjugationKind(record: VocabularyCsvRecord): Result<Vocabulary.ConjugationKind?> =
        record[VocabularyCsvRecord.Field.ConjugationKindCode].let { code ->
            code?.let { enumResultValueOf<Vocabulary.ConjugationKind>(it.pascalCase()) } ?: Result.success(null)
        }

    private fun preferredDefinition(record: VocabularyCsvRecord): Result<Definition> =
        record.getNotNull(VocabularyCsvRecord.Field.Definition).map {
            Definition(value = it, sortRank = 1) //The preferred is always rank 1.
        }

    private fun preferredWritten(record: VocabularyCsvRecord): Result<FuriganaString> =
        record.getNotNull(VocabularyCsvRecord.Field.PreferredSpelling).flatMap { preferred ->
            record.getNotNull(VocabularyCsvRecord.Field.PhoneticSpelling).map { phonetic ->
                FuriganaString(
                    FuriganaTemplate.Ruby(
                        preferred = preferred,
                        phonetic = phonetic
                    )
                )
            }
        }

    private fun altPhoneticSpelling(record: VocabularyCsvRecord): Result<Spelling?> =
        record[VocabularyCsvRecord.Field.AltPhonSpell].let { Result.success(it) }.map { spelling ->
            spelling?.let { Spelling(it) }
        }

    private fun kanjiSpelling(record: VocabularyCsvRecord): Result<Spelling?> =
        record[VocabularyCsvRecord.Field.KanjiSpelling].let { Result.success(it) }.map { spelling ->
            spelling?.let { Spelling(it) }
        }

    private fun jlptLevel(record: VocabularyCsvRecord): Result<JlptLevel?> =
        record[VocabularyCsvRecord.Field.JlptLevel].let { levelString ->
            levelString?.let {
                JlptLevel.forLevelNumber(it)
            } ?: Result.success(null)
        }

    private fun rowNumber(record: VocabularyCsvRecord): Result<Int> =
        record.getNotNull(VocabularyCsvRecord.Field.RowNum).flatMap { rowNumString ->
            Result.runCatching { rowNumString.toInt() }
        }

    private fun dateAdded(record: VocabularyCsvRecord): Result<LocalDate> =
        record.getNotNull(VocabularyCsvRecord.Field.DateAdded).flatMap { iosString ->
            Result.runCatching { LocalDate.parse(iosString) }
        }

    private fun references(record: VocabularyCsvRecord): Result<Set<Lesson.Code>> =
        record[VocabularyCsvRecord.Field.LessonCodes].let { rawCodes ->
            rawCodes?.let {
                parseCodeList(rawCodes) { Lesson.Code(it.constCase()) }.map { it.toSet() }
            } ?: Result.success(emptySet())
        }

    private fun tags(record: VocabularyCsvRecord): Result<Set<Tag>> =
        record[VocabularyCsvRecord.Field.Tags].let { rawCodes ->
            rawCodes?.let {
                parseCodeList(rawCodes) { Tag(it.kebabCase()) }.map { it.toSet() }
            } ?: Result.success(emptySet())
        }

    private fun conjugations(record: VocabularyCsvRecord): Result<Collection<Conjugation>?> =
        listOf(
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) to record[VocabularyCsvRecord.Field.DictionaryForm],
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) to record[VocabularyCsvRecord.Field.PastForm],
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) to record[VocabularyCsvRecord.Field.TeForm],
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) to record[VocabularyCsvRecord.Field.NegativeForm],
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) to record[VocabularyCsvRecord.Field.NegativePastForm],
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) to record[VocabularyCsvRecord.Field.NegativeTeForm],
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) to record[VocabularyCsvRecord.Field.PoliteForm],
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) to record[VocabularyCsvRecord.Field.PolitePastForm],
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Te) to record[VocabularyCsvRecord.Field.PoliteTeForm],
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) to record[VocabularyCsvRecord.Field.PoliteNegativeForm],
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) to record[VocabularyCsvRecord.Field.PoliteNegativePastForm],
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Te) to record[VocabularyCsvRecord.Field.PoliteNegativeTeForm],
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) to record[VocabularyCsvRecord.Field.PositivePlainPotential],
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) to record[VocabularyCsvRecord.Field.NegativePlainPotential],
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) to record[VocabularyCsvRecord.Field.PositivePolitePotential],
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) to record[VocabularyCsvRecord.Field.NegativePolitePotential],
        ).mapNotNull { (inflection, value) ->
            value?.let {
                Conjugation(inflection, it)
            }
        }.let { conjugations ->
            wordClass(record).map {wordClass ->
                if (wordClass.isConjugable) conjugations
                else null
            }
        }


    // Parses input of the form "{foo, bar,baz}"
    private fun <T> parseCodeList(rawList: String, transform: (String) -> T): Result<List<T>> =
        "^\\{(.*)\\}\$".toRegex().matchEntire(rawList)?.let {match ->
            match.groupValues[1].split(',').map { it.trim() }.map(transform).let { Result.success(it) }
        } ?: Result.success(emptyList())


}