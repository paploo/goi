package net.paploo.goi.domain.tools.spellingclassifier

import net.paploo.goi.domain.data.common.Spelling

interface SpellingClassifier {
    fun classify(string: String): Spelling.Kind?

    companion object {

        val default: SpellingClassifier = DefaultClassifier()

        operator fun invoke(): SpellingClassifier = default

    }
}

/**
 * Default classifier of spelling strings.
 *
 * This uses a simple priority rule based on what skill you need to have to read it:
 * - If it contains at least one Kanji, we return as kanji,
 * - Else if it contains at least one Katakana, we return as katakana,
 * - Else if it contains at least one Hiragana, we return as hiragana,
 * - Else if it contains at least one CJK Punctuation, we return as CJK Punctuation,
 * - Else if it contains at least one Latin character, we returns a Latin,
 * - Else we return null.
 *
 * These rules allow us to classify some weirder entries such as:
 * - 'あまり + negative' => 'HIRAGANA',
 * - '全然 + negative' => 'KANJI',
 * - 'Ｔシャツ' => 'KATAKANA'
 * - '時々' => 'KANJI'
 * - '々' => 'CJK_PUNCTUATION'
 * - 'サボります' => 'KATAKANA'
 */
class DefaultClassifier : SpellingClassifier {

    override fun classify(string: String): Spelling.Kind? =
        string.trim().let { if (it.isEmpty()) null else it }?.let {
            val classified = string.map { classifyCharacter(it).spellingKind }

            if (classified.contains(Spelling.Kind.Kanji)) Spelling.Kind.Kanji
            else if (classified.contains(Spelling.Kind.Katakana)) Spelling.Kind.Katakana
            else if (classified.contains(Spelling.Kind.Hiragana)) Spelling.Kind.Hiragana
            else if (classified.contains(Spelling.Kind.CjkPunctuation)) Spelling.Kind.CjkPunctuation
            else if (classified.contains(Spelling.Kind.Latin)) Spelling.Kind.Latin
            else null
        }

    //TODO: Refine this and make it a public classification to go with Spelling?
    //TODO: Add properties like language (Latin, 日本語) and isKana?
    private enum class CharacterKind(val spellingKind: Spelling.Kind?) {
        // Latin
        LatinAlpha(Spelling.Kind.Latin),
        LatinNumeric(Spelling.Kind.Latin),
        LatinPunctuation(Spelling.Kind.Latin),

        // 日本語
        Hiragana(Spelling.Kind.Hiragana),
        Katakana(Spelling.Kind.Katakana),
        Kanji(Spelling.Kind.Kanji),
        CjkPunctuation(Spelling.Kind.CjkPunctuation),
        HalfAndFullWidth(null), //TODO: Split into two separate values

        // Other
        Other(null),
    }

    private fun classifyCharacter(character: Char): CharacterKind = when (character) {
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
        else -> CharacterKind.Other
    }

}