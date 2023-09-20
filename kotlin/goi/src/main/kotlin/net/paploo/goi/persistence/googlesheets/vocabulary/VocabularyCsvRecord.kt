package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.common.extensions.flatMap
import org.apache.commons.csv.CSVRecord

data class VocabularyCsvRecord(
    val record: CSVRecord
) {

    operator fun get(field: Field): Result<String?> =
        Result.runCatching { record.get(field.headerName) }

    fun getNotNull(field: Field): Result<String> =
        get(field).flatMap { rawString ->
            rawString?.let { Result.success(it) } ?: Result.failure(NullFieldDataException(field = field, record = this))
        }

    enum class Field(val headerName: String) {
        Definition("definition"),
        PreferredSpelling("preferred_spelling"),
        PhoneticSpelling("phonetic_spelling"),
        AltPhonSpell("alt_phon_spell"),
        KanjiSpelling("kanji_spelling"),
        WordClassCode("word_class_code"),
        ConjugationKindCode("conjugation_kind_code"),
        JlptLevel("jlpt_level"),
        LessonCodes("lesson_codes"),
        Tags("tags"),
        Id("id"),
        RowNum("row_num"),
        DateAdded("date_added"),
        X("x"), //The empty spacer field
        DictionaryForm("dictionary_form"),
        PastForm("past_form"),
        TeForm("te_form"),
        NegativeForm("negative_form"),
        NegativePastForm("negative_past_form"),
        NegativeTeForm("negative_te_form"),
        PoliteForm("polite_form"),
        PolitePastForm("polite_past_form"),
        PoliteTeForm("polite_te_form"),
        PoliteNegativeForm("polite_negative_form"),
        PoliteNegativePastForm("polite_negative_past_form"),
        PoliteNegativeTeForm("polite_negative_te_form"),
        PositivePlainPotential("positive_plain_potential"),
        NegativePlainPotential("negative_plain_potential"),
        PositivePolitePotential("positive_polite_potential"),
        NegativePolitePotential("negative_polite_potential"),
    }

    class NullFieldDataException(field: Field, record: VocabularyCsvRecord) : NoSuchElementException(
        "Expected non-null value for field '$field' on $record"
    )

}