package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.Valued

data class Spelling(
    val spellingKind: SpellingKind,
    override val value: String,
) : Valued<String> {

    enum class SpellingKind {
        Hiragana,
        Katakana,
        Kanji,
        Punctuation,
    }

}