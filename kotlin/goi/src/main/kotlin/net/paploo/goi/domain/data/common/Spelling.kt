package net.paploo.goi.domain.data.common

import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.domain.tools.spellingclassifier.SpellingClassifier
import java.lang.IllegalArgumentException

data class Spelling(
    val kind: Kind,
    override val value: String,
) : Valued<String> {

    //TODO: Add writing system classification?
    enum class Kind(val isKana: Boolean) {
        Kanji(isKana = false),
        Katakana(isKana = true),
        Hiragana(isKana = true),
        CjkPunctuation(isKana = false),
        Latin(isKana = false),
    }

    companion object {

        operator fun invoke(value: String): Spelling =
            SpellingClassifier.default.classify(value)?.let { kind ->
                Spelling(kind = kind, value = value)
            } ?: throw IllegalArgumentException("Could not classify value '$value'")

    }

}