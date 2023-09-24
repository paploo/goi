package net.paploo.goi.persistence.database

import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.*

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