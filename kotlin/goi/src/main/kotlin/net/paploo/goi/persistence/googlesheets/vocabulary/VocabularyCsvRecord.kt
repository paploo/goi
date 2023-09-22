package net.paploo.goi.persistence.googlesheets.vocabulary

import org.apache.commons.csv.CSVRecord
import java.lang.IllegalArgumentException

internal data class VocabularyCsvRecord(
    val definition: String?,
    val preferredSpelling: String?,
    val phoneticSpelling: String?,
    val altPhonSpell: String?,
    val kanjiSpelling: String?,
    val wordClassCode: String?,
    val conjugationKindCode: String?,
    val jlptLevel: String?,
    val lessonCodes: String?,
    val tags: String?,
    val id: String?,
    val rowNum: String?,
    val dateAdded: String?,
    val conjugations: Conjugations,
) {

    constructor(csvRecord: CSVRecord) : this(
        definition = csvRecord[Field.Definition.headerName],
        preferredSpelling = csvRecord[Field.PreferredSpelling.headerName],
        phoneticSpelling = csvRecord[Field.PhoneticSpelling.headerName],
        altPhonSpell = csvRecord[Field.AltPhonSpell.headerName],
        kanjiSpelling = csvRecord[Field.KanjiSpelling.headerName],
        wordClassCode = csvRecord[Field.WordClassCode.headerName],
        conjugationKindCode = csvRecord[Field.ConjugationKindCode.headerName],
        jlptLevel = csvRecord[Field.JlptLevel.headerName],
        lessonCodes = csvRecord[Field.LessonCodes.headerName],
        tags = csvRecord[Field.Tags.headerName],
        id = csvRecord[Field.Id.headerName],
        rowNum = csvRecord[Field.RowNum.headerName],
        dateAdded = csvRecord[Field.DateAdded.headerName],
        conjugations = Conjugations(
            dictionaryForm = csvRecord[Field.DictionaryForm.headerName],
            pastForm = csvRecord[Field.PastForm.headerName],
            teForm = csvRecord[Field.TeForm.headerName],
            negativeForm = csvRecord[Field.NegativeForm.headerName],
            negativePastForm = csvRecord[Field.NegativePastForm.headerName],
            negativeTeForm = csvRecord[Field.NegativeTeForm.headerName],
            politeForm = csvRecord[Field.PoliteForm.headerName],
            politePastForm = csvRecord[Field.PolitePastForm.headerName],
            politeTeForm = csvRecord[Field.PoliteTeForm.headerName],
            politeNegativeForm = csvRecord[Field.PoliteNegativeForm.headerName],
            politeNegativePastForm = csvRecord[Field.PoliteNegativePastForm.headerName],
            politeNegativeTeForm = csvRecord[Field.PoliteNegativeTeForm.headerName],
            positivePlainPotential = csvRecord[Field.PositivePlainPotential.headerName],
            negativePlainPotential = csvRecord[Field.NegativePlainPotential.headerName],
            positivePolitePotential = csvRecord[Field.PositivePolitePotential.headerName],
            negativePolitePotential = csvRecord[Field.NegativePolitePotential.headerName],
        )
    )

    data class Conjugations(
        val dictionaryForm: String?,
        val pastForm: String?,
        val teForm: String?,
        val negativeForm: String?,
        val negativePastForm: String?,
        val negativeTeForm: String?,
        val politeForm: String?,
        val politePastForm: String?,
        val politeTeForm: String?,
        val politeNegativeForm: String?,
        val politeNegativePastForm: String?,
        val politeNegativeTeForm: String?,
        val positivePlainPotential: String?,
        val negativePlainPotential: String?,
        val positivePolitePotential: String?,
        val negativePolitePotential: String?,
    )

    enum class Field(val headerName: String, val getter: VocabularyCsvRecord.() -> String?) {
        Definition("definition", { definition }),
        PreferredSpelling("preferred_spelling", { preferredSpelling }),
        PhoneticSpelling("phonetic_spelling", { phoneticSpelling }),
        AltPhonSpell("alt_phon_spell", { altPhonSpell }),
        KanjiSpelling("kanji_spelling", { kanjiSpelling }),
        WordClassCode("word_class_code", { wordClassCode }),
        ConjugationKindCode("conjugation_kind_code", { conjugationKindCode }),
        JlptLevel("jlpt_level", { jlptLevel }),
        LessonCodes("lesson_codes", { lessonCodes }),
        Tags("tags", { tags }),
        Id("id", { id }),
        RowNum("row_num", { rowNum }),
        DateAdded("date_added", { dateAdded }),

        DictionaryForm("dictionary_form", { conjugations.dictionaryForm }),
        PastForm("past_form", { conjugations.pastForm }),
        TeForm("te_form", { conjugations.teForm }),
        NegativeForm("negative_form", { conjugations.negativeForm }),
        NegativePastForm("negative_past_form", { conjugations.negativePastForm }),
        NegativeTeForm("negative_te_form", { conjugations.negativeTeForm }),
        PoliteForm("polite_form", { conjugations.politeForm }),
        PolitePastForm("polite_past_form", { conjugations.politePastForm }),
        PoliteTeForm("polite_te_form", { conjugations.politeTeForm }),
        PoliteNegativeForm("polite_negative_form", { conjugations.politeNegativeForm }),
        PoliteNegativePastForm("polite_negative_past_form", { conjugations.politeNegativePastForm }),
        PoliteNegativeTeForm("polite_negative_te_form", { conjugations.politeNegativeTeForm }),
        PositivePlainPotential("positive_plain_potential", { conjugations.positivePlainPotential }),
        NegativePlainPotential("negative_plain_potential", { conjugations.negativePlainPotential }),
        PositivePolitePotential("positive_polite_potential", { conjugations.positivePolitePotential }),
        NegativePolitePotential("negative_polite_potential", { conjugations.negativePolitePotential }),
    }

    private val fieldMap: Map<Field, String?> by lazy {
        Field.entries.associateWith { get(it) }
    }

    operator fun get(field: Field): String? =
        field.getter(this)

    fun getNotNull(field: Field): Result<String> =
        get(field)?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("$field contained a null value but should've been non-null"))

    fun toRow(): List<String?> =
        Field.entries.map { get(it) }

    fun toMap(): Map<Field, String?> =
        Field.entries.associateWith { get(it) }

}
