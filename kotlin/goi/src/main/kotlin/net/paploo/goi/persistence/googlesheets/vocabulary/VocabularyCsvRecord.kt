package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.persistence.common.BaseCsvRecord
import net.paploo.goi.persistence.common.CsvRecord
import net.paploo.goi.persistence.common.extensions.getOrNull
import org.apache.commons.csv.CSVRecord
import kotlin.enums.EnumEntries



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
) : BaseCsvRecord<VocabularyCsvRecord, VocabularyCsvRecord.Field>() {

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
            dictionaryForm = csvRecord.getOrNull(Field.DictionaryForm.headerName),
            pastForm = csvRecord.getOrNull(Field.PastForm.headerName),
            teForm = csvRecord.getOrNull(Field.TeForm.headerName),
            negativeForm = csvRecord.getOrNull(Field.NegativeForm.headerName),
            negativePastForm = csvRecord.getOrNull(Field.NegativePastForm.headerName),
            negativeTeForm = csvRecord.getOrNull(Field.NegativeTeForm.headerName),
            politeForm = csvRecord.getOrNull(Field.PoliteForm.headerName),
            politePastForm = csvRecord.getOrNull(Field.PolitePastForm.headerName),
            politeTeForm = csvRecord.getOrNull(Field.PoliteTeForm.headerName),
            politeNegativeForm = csvRecord.getOrNull(Field.PoliteNegativeForm.headerName),
            politeNegativePastForm = csvRecord.getOrNull(Field.PoliteNegativePastForm.headerName),
            politeNegativeTeForm = csvRecord.getOrNull(Field.PoliteNegativeTeForm.headerName),
            positivePlainPotential = csvRecord.getOrNull(Field.PositivePlainPotential.headerName),
            negativePlainPotential = csvRecord.getOrNull(Field.NegativePlainPotential.headerName),
            positivePolitePotential = csvRecord.getOrNull(Field.PositivePolitePotential.headerName),
            negativePolitePotential = csvRecord.getOrNull(Field.NegativePolitePotential.headerName),
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

    /**
     * Ordered list of anki CSV columns
     */
    enum class Field(
        override val headerName: String,
        override val getter: VocabularyCsvRecord.() -> String?
    ) : CsvRecord.Field<VocabularyCsvRecord, Field> {
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

        ConjugationSectionDivider("", { null }),

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

    override val record: VocabularyCsvRecord = this

    override val fields: EnumEntries<Field> = Field.entries

}
