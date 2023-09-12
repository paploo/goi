package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.Identifiable
import net.paploo.goi.common.Valued
import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.data.common.JpString

data class LinkedVocabulary(
    val vocabulary: Vocabulary,
    val preferredDefinition: Definition,
    val preferredWritten: JpString,
    val altPhoneticSpelling: Spelling?,
    val kanjiSpelling: Spelling?,
    val conjugations: Collection<Conjugation>?,
) : Identifiable<Vocabulary.Id>, Valued<String> {

    init {
        assert(vocabulary.isConjugable && conjugations != null) {
            "Expected vocabulary ${vocabulary.id} to have conjugations"
        }

        assert(!(vocabulary.isConjugable) && conjugations == null) {
            "Expected vocabulary ${vocabulary.id} to have no conjugations"
        }
    }

    override val id: Vocabulary.Id = vocabulary.id

    override val value: String = preferredWritten.value
    val preferredSpelling: Spelling get() = preferredWritten.preferredSpelling

    val phoneticSpelling: Spelling get() = preferredWritten.phoneticSpelling
}