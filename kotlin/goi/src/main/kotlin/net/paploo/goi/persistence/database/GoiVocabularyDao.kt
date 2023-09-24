package net.paploo.goi.persistence.database

import net.paploo.goi.common.extensions.constCase
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.extensions.snakeCase
import net.paploo.goi.common.util.createUuidV5
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.*
import net.paploo.goi.persistence.db.goi.vocabulary.tables.references.*
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.conf.StatementType
import org.jooq.impl.DSL
import java.nio.file.Path
import java.util.*

class GoiVocabularyDao(
    val dataSource: ServiceDataSource,
    val filePath: Path
) {

    suspend fun writeAll(vocabularyList: List<Vocabulary>): Result<Unit> = TODO()

    suspend fun writeAllSql(vocabularyList: List<Vocabulary>): Result<Unit> =
        vocabularyList.map { VocabularyDomainToRecordTransform().invoke(it) }.sequenceToResult().map { records ->
            println(records.count())
            println(records.first())
            VocabularySqlWriter().invoke(filePath, records)
        }

}

internal class VocabularySqlWriter : suspend (Path, List<VocabularyRecordGroup>) -> Unit {

    override suspend fun invoke(filePath: Path, recordGroups: List<VocabularyRecordGroup>) =
        recordGroups.forEach { recordGroup ->
            recordGroupSql(recordGroup).map { lines ->
                //TODO: Actually write to a file!
                if(recordGroup.vocabularyRecord.rowNum in setOf(40)) {
                    lines.forEach { println(it) }
                }
            }
        }

    private val dsl: DSLContext = DSL.using(
        SQLDialect.POSTGRES, Settings().apply {
            statementType = StatementType.STATIC_STATEMENT //Makes output static sql and not prepared statements.
        }
    )

    private fun recordGroupSql(recordGroup: VocabularyRecordGroup): Result<List<String>> = Result.runCatching {
        listOf<List<String>>(
            listOf(headerComment(recordGroup)),
            listOf(vocabularySql(recordGroup.vocabularyRecord)),
            recordGroup.definitions.map { definitionSql(it) },
            recordGroup.spellings.map { spellingSql(it) },
            recordGroup.conjugationSet?.let { listOf(conjugationSetSql(it)) } ?: emptyList(),
            listOf(linkagesSql(recordGroup.linkagesRecord)),
            recordGroup.references.map { referenceSql(it) },
            recordGroup.conjugations?.map { conjugationSql(it) } ?: emptyList(),
        ).flatten()
    }

    private fun headerComment(recordGroup: VocabularyRecordGroup): String =
        "-- ${recordGroup.spellings.firstOrNull()?.value} | ${recordGroup.definitions.firstOrNull()?.value} --;"

    fun vocabularySql(record: VocabularyRecord): String =
        dsl.insertInto(VOCABULARY_).set(record).sql

    fun definitionSql(record: DefinitionRecord): String =
        dsl.insertInto(DEFINITION).set(record).sql

    fun spellingSql(record: SpellingRecord): String =
        dsl.insertInto(SPELLING).set(record).sql

    fun conjugationSetSql(record: ConjugationSetRecord): String =
        dsl.insertInto(CONJUGATION_SET).set(record).sql

    fun linkagesSql(record: LinkagesRecord): String =
        dsl.insertInto(LINKAGES).set(record).sql

    fun referenceSql(record: ReferenceRecord): String =
        dsl.insertInto(REFERENCE).set(record).sql

    fun conjugationSql(record: ConjugationRecord): String =
        dsl.insertInto(CONJUGATION).set(record).sql

}

/**
 * A group of records all related to the same vocabulary object.
 */
internal data class VocabularyRecordGroup(
    val vocabularyRecord: VocabularyRecord,
    val linkagesRecord: LinkagesRecord,
    val definitions: List<DefinitionRecord>,
    val spellings: List<SpellingRecord>,
    val references: List<ReferenceRecord>,
    val conjugationSet: ConjugationSetRecord?,
    val conjugations: List<ConjugationRecord>?,
)


internal class VocabularyDomainToRecordTransform : (Vocabulary) -> Result<VocabularyRecordGroup> {

    override fun invoke(vocab: Vocabulary): Result<VocabularyRecordGroup> = Result.runCatching {
        buildRecord(vocab)
    }

    private fun buildRecord(vocab: Vocabulary): VocabularyRecordGroup =
        VocabRecords(
            vocabularyRecord = buildVocabularyRecord(vocab),
            preferredDefinition = buildPreferredDefinition(vocab),
            preferredSpelling = buildPreferredSpelling(vocab),
            phoneticSpelling = buildPhoneticSpelling(vocab),
            altPhoneticSpelling = buildAltPhoneticSpelling(vocab),
            kanjiSpelling = buildKanjiSpelling(vocab),
            references = buildReferences(vocab),
            conjugationSet = buildConjugationSet(vocab),
            conjugations = buildConjugations(vocab),
        ).toRecordGroup()

    /**
     * Intermediate grouping of records with their contextual information.
     *
     * This allows propert creation of a linkages record without making assumptions.
     */
    private data class VocabRecords(
        val vocabularyRecord: VocabularyRecord,
        val preferredDefinition: DefinitionRecord,
        val preferredSpelling: SpellingRecord,
        val phoneticSpelling: SpellingRecord,
        val altPhoneticSpelling: SpellingRecord?,
        val kanjiSpelling: SpellingRecord?,
        val references: List<ReferenceRecord>,
        val conjugationSet: ConjugationSetRecord?,
        val conjugations: List<ConjugationRecord>?,
    ) {

        val linkages: LinkagesRecord = LinkagesRecord(
            vocabularyId = vocabularyRecord.id,
            preferredDefinitionId = preferredDefinition.id,
            preferredSpellingId = preferredSpelling.id,
            phoneticSpellingId = phoneticSpelling.id,
            altPhoneticSpellingId = altPhoneticSpelling?.id,
            kanjiSpellingId = kanjiSpelling?.id,
            conjugationSetId = conjugationSet?.id
        )

        fun toRecordGroup(): VocabularyRecordGroup = VocabularyRecordGroup(
            vocabularyRecord = vocabularyRecord,
            linkagesRecord = linkages,
            definitions = listOf(preferredDefinition),
            spellings = listOfNotNull(preferredSpelling, phoneticSpelling, altPhoneticSpelling, kanjiSpelling),
            references = references,
            conjugationSet = conjugationSet,
            conjugations = conjugations,
        )

    }

    private fun buildvocabularyRecord(vocab: Vocabulary): VocabularyRecord =
        VocabularyRecord(
            id = vocab.id.value,
            wordClassCode = vocab.wordClass.name.constCase(),
            conjugationKindCode = vocab.conjugationKind?.name?.constCase(),
            jlptLevel = vocab.jlptLevel?.levelNumber,
            rowNum = vocab.rowNumber,
            dateAdded = vocab.dateAdded,
            tags = vocab.tags.sorted().map { it.value.snakeCase() }.toTypedArray(),
        )

    /*
     * Note: The old Ruby Goi code generated the IDs using UUIDv5 from a selection of fields.
     *       It did this to ensure IDs were preserved across loads. We start with compatible
     *       ID generation.
     */

    private fun buildVocabularyRecord(vocab: Vocabulary): VocabularyRecord =
        VocabularyRecord(
            id = vocab.id.value,
            wordClassCode = vocab.wordClass.name.constCase(),
            conjugationKindCode = vocab.conjugationKind?.name?.constCase(),
            jlptLevel = vocab.jlptLevel?.levelNumber,
            rowNum = vocab.rowNumber,
            dateAdded = vocab.dateAdded,
            tags = vocab.tags.map { it.value.snakeCase() }.toTypedArray()
        )

    private val definitionIdNamespace: UUID = UUID.fromString("c7812647-678a-4bf5-bed3-b33fe499469c")

    private fun buildPreferredDefinition(vocab: Vocabulary): DefinitionRecord =
        vocab.preferredDefinition.let {
            DefinitionRecord(
                id = listOf(
                    vocab.id.value.toString(),
                    "preferred_definition"
                ).createUuidV5(definitionIdNamespace),
                vocabularyId = vocab.id.value,
                sortRank = 1, //Preferred definition is always rank one, other definitions (if added later) fall below it.
                value = it.value
            )
        }

    private val spellingIdNamespace: UUID = UUID.fromString("546a4b2c-6b83-4fe9-902e-7c7ade990930")

    private fun buildPreferredSpelling(vocab: Vocabulary): SpellingRecord =
        vocab.preferredWritten.preferredSpelling.let {
            SpellingRecord(
                id = listOf(
                    vocab.id.value.toString(),
                    "preferred_spelling"
                ).createUuidV5(spellingIdNamespace),
                vocabularyId = vocab.id.value,
                spellingKindCode = it.kind.name.constCase(),
                value = it.value,
            )
        }

    private fun buildPhoneticSpelling(vocab: Vocabulary): SpellingRecord =
        vocab.preferredWritten.phoneticSpelling?.let {
            SpellingRecord(
                id = listOf(
                    vocab.id.value.toString(),
                    "phonetic_spelling"
                ).createUuidV5(spellingIdNamespace),
                vocabularyId = vocab.id.value,
                spellingKindCode = it.kind.name.constCase(),
                value = it.value,
            )
        } ?: throw IllegalArgumentException("Expected phonetic spelling for preferredWritten = ${vocab.preferredWritten} on vocabulary ${vocab.id}")

    private fun buildAltPhoneticSpelling(vocab: Vocabulary): SpellingRecord? =
        vocab.altPhoneticSpelling?.let {
            SpellingRecord(
                id = listOf(
                    vocab.id.value.toString(),
                    "alt_phon_spell" //Copy the typo from Goi Ruby to match its output. TODO: once its deprecated, fix this.
                ).createUuidV5(spellingIdNamespace),
                vocabularyId = vocab.id.value,
                spellingKindCode = it.kind.name.constCase(),
                value = it.value,
            )
        }

    private fun buildKanjiSpelling(vocab: Vocabulary): SpellingRecord? =
        vocab.kanjiSpelling?.let {
            SpellingRecord(
                id = listOf(
                    vocab.id.value.toString(),
                    "kanji_spelling"
                ).createUuidV5(spellingIdNamespace),
                vocabularyId = vocab.id.value,
                spellingKindCode = it.kind.name.constCase(),
                value = it.value,
            )
        }

    private fun buildReferences(vocab: Vocabulary): List<ReferenceRecord> =
        vocab.references.map {
            ReferenceRecord(
                vocabularyId = vocab.id.value,
                lessonCode = it.value.constCase()
            )
        }

    //Note: ruby goi only created the set if non-empty, but really we should create it if it is conjugable?.
    private fun buildConjugationSet(vocab: Vocabulary): ConjugationSetRecord? =
        vocab.conjugations?.let {
            ConjugationSetRecord(
                id = createConjugationSetId(vocab.id),
                vocabularyId = vocab.id.value
            )
        }

    private fun buildConjugations(vocab: Vocabulary): List<ConjugationRecord>? =
        vocab.conjugations?.groupBy { it.inflection }?.flatMap { (inflection, conjugations) ->
            conjugations.mapIndexed { index, conjugation ->
                val sortRank = index + 1

                ConjugationRecord(
                    id = listOf(
                        createConjugationSetId(vocab.id).toString(),
                        conjugation.inflection.politeness.name.constCase(),
                        conjugation.inflection.charge.name.constCase(),
                        conjugation.inflection.form.name.constCase(),
                        sortRank.toString()
                    ).createUuidV5(UUID.fromString("a55893fe-f4fd-4e84-a9f0-6a6d6495b53b")),
                    conjugationSetId = createConjugationSetId(vocab.id),
                    politenessCode = conjugation.inflection.politeness.name.constCase(),
                    chargeCode =  conjugation.inflection.charge.name.constCase(),
                    formCode = conjugation.inflection.form.name.constCase(),
                    sortRank = sortRank,
                    value = conjugation.value
                )
            }
        }

    private fun createConjugationSetId(vocabularyId: Vocabulary.Id): UUID =
        listOf(vocabularyId.value.toString()).createUuidV5(UUID.fromString("8724ca34-1e4a-4e78-8474-b359cdf33b66"))


}