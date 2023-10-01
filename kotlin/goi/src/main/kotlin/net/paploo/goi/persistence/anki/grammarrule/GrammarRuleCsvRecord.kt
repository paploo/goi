package net.paploo.goi.persistence.anki.grammarrule

import net.paploo.goi.persistence.common.BaseCsvRecord
import net.paploo.goi.persistence.common.CsvRecord
import org.apache.commons.csv.CSVRecord
import kotlin.enums.EnumEntries

internal data class GrammarRuleCsvRecord(
    val id: String?,
    val title: String?,
    val titlePhonetic: String?,
    val meaning: String?,
    val howToUse: String?,
    val jlptLevel: String?,
    val dateAdded: String?,
    val rowNum: String?,
    val examples: List<Example>,
    val tags: String?,
) : BaseCsvRecord<GrammarRuleCsvRecord, GrammarRuleCsvRecord.Field>() {

    constructor(csvRecord: CSVRecord) : this(
        id = csvRecord[Field.Id],
        title = csvRecord[Field.Title],
        titlePhonetic = csvRecord[Field.TitlePhonetic],
        meaning = csvRecord[Field.Meaning],
        howToUse = csvRecord[Field.HowToUse],
        jlptLevel = csvRecord[Field.JlptLevel],
        dateAdded = csvRecord[Field.DateAdded],
        rowNum = csvRecord[Field.RowNum],
        examples = listOf(
            Example(
                text = csvRecord[Field.Example1Text],
                textPhonetic = csvRecord[Field.Example1TextPhonetic],
                meaning = csvRecord[Field.Example1Meaning],
            ),
            Example(
                text = csvRecord[Field.Example2Text],
                textPhonetic = csvRecord[Field.Example2TextPhonetic],
                meaning = csvRecord[Field.Example2Meaning],
            ),
            Example(
                text = csvRecord[Field.Example3Text],
                textPhonetic = csvRecord[Field.Example3TextPhonetic],
                meaning = csvRecord[Field.Example3Meaning],
            ),
            Example(
                text = csvRecord[Field.Example4Text],
                textPhonetic = csvRecord[Field.Example4TextPhonetic],
                meaning = csvRecord[Field.Example4Meaning],
            ),
            Example(
                text = csvRecord[Field.Example5Text],
                textPhonetic = csvRecord[Field.Example5TextPhonetic],
                meaning = csvRecord[Field.Example5Meaning],
            ),
            Example(
                text = csvRecord[Field.Example6Text],
                textPhonetic = csvRecord[Field.Example6TextPhonetic],
                meaning = csvRecord[Field.Example6Meaning],
            ),
        ),
        tags = csvRecord[Field.Tags],
    )

    data class Example(
        val text: String?,
        val textPhonetic: String?,
        val meaning: String?
    )

    /**
     * Ordered list of the anki CSV columns.
     */
    enum class Field(
        override val headerName: String,
        override val getter: GrammarRuleCsvRecord.() -> String?
    ): CsvRecord.Field<GrammarRuleCsvRecord, Field> {
        Id("id", { id }),
        Title("title", { title }),
        TitlePhonetic("title_phonetic", { titlePhonetic }),
        Meaning("meaning", { meaning }),
        HowToUse("how_to_use", { howToUse }),
        JlptLevel("jlpt_level", { jlptLevel }),
        DateAdded("date_added", { dateAdded }),
        RowNum("row_num", { rowNum }),
        // If I ditched my exsiting type-safe CSV record object, I could do this more dynamically.
        // however I prefer to have some type safety on the columsn, so managing the arity of the output with boilerplate
        // is he compromise I'll make.
        Example1Text("example_1_text", { examples.getOrNull(0)?.text }),
        Example1TextPhonetic("example_1_text_phonetic", { examples.getOrNull(0)?.textPhonetic }),
        Example1Meaning("example_1_meaning", { examples.getOrNull(0)?.meaning }),
        Example2Text("example_2_text", { examples.getOrNull(1)?.text }),
        Example2TextPhonetic("example_2_text_phonetic", { examples.getOrNull(1)?.textPhonetic }),
        Example2Meaning("example_2_meaning", { examples.getOrNull(1)?.meaning }),
        Example3Text("example_3_text", { examples.getOrNull(2)?.text }),
        Example3TextPhonetic("example_3_text_phonetic", { examples.getOrNull(2)?.textPhonetic }),
        Example3Meaning("example_3_meaning", { examples.getOrNull(2)?.meaning }),
        Example4Text("example_4_text", { examples.getOrNull(3)?.text }),
        Example4TextPhonetic("example_4_text_phonetic", { examples.getOrNull(3)?.textPhonetic }),
        Example4Meaning("example_4_meaning", { examples.getOrNull(3)?.meaning }),
        Example5Text("example_5_text", { examples.getOrNull(4)?.text }),
        Example5TextPhonetic("example_5_text_phonetic", { examples.getOrNull(4)?.textPhonetic }),
        Example5Meaning("example_5_meaning", { examples.getOrNull(4)?.meaning }),
        Example6Text("example_6_text", { examples.getOrNull(5)?.text }),
        Example6TextPhonetic("example_6_text_phonetic", { examples.getOrNull(5)?.textPhonetic }),
        Example6Meaning("example_6_meaning", { examples.getOrNull(5)?.meaning }),
        //Tags always go on the end for anki sheets
        Tags("tags", { tags });
    }

    override val record: GrammarRuleCsvRecord = this

    override val fields: EnumEntries<Field> = Field.entries

}