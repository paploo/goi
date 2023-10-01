package net.paploo.goi.domain.tools.spellingclassifier

import net.paploo.goi.domain.data.common.Spelling

/**
 * Classifier of individual characters.
 *
 * This is similar to SpellingClassifier, but gives a higher resolution to the classification.
 */
interface CharacterClassifier : (Char) -> CharacterKind {
    override fun invoke(char: Char): CharacterKind

    companion object {

        val default: CharacterClassifier = DefaultCharacterClassifier()

        operator fun invoke(): CharacterClassifier = default

    }

}

/**
 * Enumeration of CharacterKinds that can come from classification.
 *
 * This is similar to Spelling.Kind, but has finer granularity.
 */
enum class CharacterKind(
    val spellingKind: Spelling.Kind,
    val isNihongo: Boolean,
    val isKana: Boolean
) {
    // Other
    Unknown(Spelling.Kind.Unknown, isNihongo = false, isKana = false),

    // Latin
    LatinAlpha(Spelling.Kind.Latin, isNihongo = false, isKana = false),
    LatinNumeric(Spelling.Kind.Latin, isNihongo = false, isKana = false),
    LatinPunctuation(Spelling.Kind.Latin, isNihongo = false, isKana = false),

    // 日本語
    Hiragana(Spelling.Kind.Hiragana, isNihongo = true, isKana = true),
    Katakana(Spelling.Kind.Katakana, isNihongo = true, isKana = true),
    Kanji(Spelling.Kind.Kanji, isNihongo = true, isKana = false),
    CjkPunctuation(Spelling.Kind.CjkPunctuation, isNihongo = true, isKana = false),
    HalfAndFullWidth(Spelling.Kind.HalfAndFullWidth, isNihongo = true, isKana = false),
}

private class DefaultCharacterClassifier : CharacterClassifier {
    override fun invoke(char: Char): CharacterKind = when (char) {
        // Latin
        in '\u0020'..'\u002f' -> CharacterKind.LatinPunctuation
        in '\u0030'..'\u0039' -> CharacterKind.LatinNumeric
        in '\u003a'..'\u0040' -> CharacterKind.LatinPunctuation
        in '\u0041'..'\u005a' -> CharacterKind.LatinAlpha
        in '\u005b'..'\u0060' -> CharacterKind.LatinPunctuation
        in '\u0061'..'\u007a' -> CharacterKind.LatinAlpha
        in '\u007b'..'\u007e' -> CharacterKind.LatinPunctuation

        // 日本語
        in '\u3040'..'\u309f' -> CharacterKind.Hiragana
        in '\u30a0'..'\u30ff' -> CharacterKind.Katakana
        in '\u4e00'..'\u9faf' -> CharacterKind.Kanji
        in '\u3000'..'\u303f' -> CharacterKind.CjkPunctuation
        in '\uff00'..'\uffef' -> CharacterKind.HalfAndFullWidth

        // Other
        else -> CharacterKind.Unknown
    }
}