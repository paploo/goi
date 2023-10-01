package net.paploo.goi.domain.data.common

import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.domain.tools.spellingclassifier.SpellingClassifier
import java.lang.IllegalArgumentException

data class Spelling(
    val kind: Kind,
    override val value: String,
) : Valued<String> {

    init {
        if (value.isEmpty()) throw IllegalArgumentException("${this::class.simpleName} must contain a value, but got an empty string.")
    }

    enum class Kind(val isKana: Boolean, val isNihongo: Boolean) {
        Unknown(isKana = false, isNihongo = false),
        Kanji(isKana = false, isNihongo = true),
        Katakana(isKana = true, isNihongo = true),
        Hiragana(isKana = true, isNihongo = true),
        CjkPunctuation(isKana = false, isNihongo = true),
        HalfAndFullWidth(isKana = false, isNihongo = true),
        Latin(isKana = false, isNihongo = false),
    }

    companion object {

        operator fun invoke(value: String): Spelling =
            SpellingClassifier.default.invoke(value).let { kind ->
                Spelling(kind = kind, value = value)
            }

    }

}