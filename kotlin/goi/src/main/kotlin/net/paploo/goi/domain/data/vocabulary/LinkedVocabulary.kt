package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.Identifiable
import net.paploo.goi.common.Valued

data class LinkedVocabulary(
    val vocabulary: Vocabulary,
    val preferredDefinition: Definition,
    val preferredSpelling: Spelling,
    val phoneticSpelling: Spelling,
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

    override val value: String = preferredSpelling.value
}