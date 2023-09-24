package net.paploo.goi.persistence.database

import net.paploo.goi.common.extensions.constCase
import net.paploo.goi.common.extensions.snakeCase
import net.paploo.goi.common.util.createUuidV5
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.*
import java.util.*

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


    private fun buildConjugationSet(vocab: Vocabulary): ConjugationSetRecord? =
        vocab.conjugations?.let {
            //Due to the constraints on Vocabulary, we can assume that it will only be non-null if it is conjugable.
            //Note that this will produce an empty conjugation set if conjugable but has no conjugations. This seems correct.
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
                    chargeCode = conjugation.inflection.charge.name.constCase(),
                    formCode = conjugation.inflection.form.name.constCase(),
                    sortRank = sortRank,
                    value = conjugation.value
                )
            }
        }

    private fun createConjugationSetId(vocabularyId: Vocabulary.Id): UUID =
        listOf(vocabularyId.value.toString()).createUuidV5(UUID.fromString("8724ca34-1e4a-4e78-8474-b359cdf33b66"))


}