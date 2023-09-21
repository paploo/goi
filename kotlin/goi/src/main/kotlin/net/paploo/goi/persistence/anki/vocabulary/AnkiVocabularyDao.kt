package net.paploo.goi.persistence.anki.vocabulary

import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.CsvFormats
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord
import java.io.FileOutputStream
import java.io.FileWriter
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.path.outputStream
import kotlin.io.path.writer

class AnkiVocabularyDao(
    val filePath: Path
) {

    suspend fun writeAll(vocularyList: List<Vocabulary>): Result<Unit> =
        vocularyList.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().map { records ->
            VocabularyWriter().invoke(filePath, records)
        }

}


internal class VocabularyDomainToRecordTransform : (Vocabulary) -> Result<VocabularyCsvRecord> {

    // Notes:
    // - The Parser is null since I'm construcitng them.
    // - The recordNumber is 1 indexed; if sorted they match the rowNum column
    //
    // - It uses `parser.getHeaderMapRaw()` to support headers, which is fine for Anki but will be trickier for GoogleSheets.
    // - Can possibly define in CSV Format?

    override fun invoke(vocab: Vocabulary): Result<VocabularyCsvRecord> = TODO()

}

internal class VocabularyWriter : suspend (Path, List<VocabularyCsvRecord>) -> Result<Unit> {

    val format: CSVFormat = CsvFormats.default

    override suspend fun invoke(filePath: Path, records: List<VocabularyCsvRecord>): Result<Unit> =
        Result.runCatching {
            FileWriter(filePath.toFile())
        }.map { writer ->
            CSVPrinter(writer, format)
        }.map { printer ->
            printer.use {
                directives.forEach { (key, value) ->
                    it.printComment("${key}:${value}")
                }

                it.printRecord(headers)

                records.forEach { record ->
                    it.printRecord(record)
                }
            }
        }

    private val directives: List<Pair<String, String>> by lazy {
        listOf(
            "separator" to "Comma",
            "deck" to "日本語 Vocab",
            "notetype" to "日本語 Vocab",
            "tags column" to headers.size.toString(),
            "columns" to headers.joinToString(","),
        )
    }

    private val headers: List<String> by lazy {
        VocabularyCsvRecord.Field.entries.map { it.headerName }
    }
}

internal data class VocabularyCsvRecord(
    val record: CSVRecord
) {

    //TODO: Ordered list of anki voca
    enum class Field(val headerName: String) {
        Id("id"),
        Definition("definition"),
        PreferredSpelling("preferred_spelling"),
        PhoneticSpelling("phonetic_spelling"),
        AltSpelling("alt_spelling"),
        WordClass("word_class"),
        ConjugationKind("conjugation_kind"),
        JlptLevel("jlpt_level"),
        DateAdded("date_added"),
        RowNum("row_num"),
        Lessons("lessons"),
        PositivePlainPresent("positive_plain_present"),
        PositivePlainPast("positive_plain_past"),
        PositivePlainTe("positive_plain_te"),
        PositivePlainConditionalEba("positive_plain_conditional_eba"),
        PositivePlainConditionalTara("positive_plain_conditional_tara"),
        PositivePlainPotential("positive_plain_potential"),
        PositivePlainPassive("positive_plain_passive"),
        PositivePlainCausative("positive_plain_causative"),
        PositivePlainImperative("positive_plain_imperative"),
        PositivePolitePresent("positive_polite_present"),
        PositivePolitePast("positive_polite_past"),
        PositivePoliteTe("positive_polite_te"),
        PositivePoliteConditionalEba("positive_polite_conditional_eba"),
        PositivePoliteConditionalTara("positive_polite_conditional_tara"),
        PositivePolitePotential("positive_polite_potential"),
        PositivePolitePassive("positive_polite_passive"),
        PositivePoliteCausative("positive_polite_causative"),
        PositivePoliteImperative("positive_polite_imperative"),
        NegativePlainPresent("negative_plain_present"),
        NegativePlainPast("negative_plain_past"),
        NegativePlainTe("negative_plain_te"),
        NegativePlainConditionalEba("negative_plain_conditional_eba"),
        NegativePlainConditionalTara("negative_plain_conditional_tara"),
        NegativePlainPotential("negative_plain_potential"),
        NegativePlainPassive("negative_plain_passive"),
        NegativePlainCausative("negative_plain_causative"),
        NegativePlainImperative("negative_plain_imperative"),
        NegativePolitePresent("negative_polite_present"),
        NegativePolitePast("negative_polite_past"),
        NegativePoliteTe("negative_polite_te"),
        NegativePoliteConditionalEba("negative_polite_conditional_eba"),
        NegativePoliteConditionalTara("negative_polite_conditional_tara"),
        NegativePolitePotential("negative_polite_potential"),
        NegativePolitePassive("negative_polite_passive"),
        NegativePoliteCausative("negative_polite_causative"),
        NegativePoliteImperative("negative_polite_imperative"),
        Tags("tags"),
    }

}