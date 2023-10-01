package net.paploo.goi.domain.tools.spellingclassifier

import net.paploo.goi.domain.data.common.Spelling

interface SpellingClassifier : (String) -> Spelling.Kind {
    override fun invoke(string: String): Spelling.Kind

    companion object {

        val default: SpellingClassifier = DefaultSpellingClassifier()

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
 * - Else if it contains at least one half or full width, we return HalfAndFullWidth,
 * - Else if it contains at least one Latin character, we returns a Latin,
 * - Else we return Unknown.
 *
 * These rules allow us to classify some weirder entries such as:
 * - 'あまり + negative' => 'HIRAGANA',
 * - '全然 + negative' => 'KANJI',
 * - 'Ｔシャツ' => 'KATAKANA'
 * - '時々' => 'KANJI'
 * - '々' => 'CJK_PUNCTUATION'
 * - 'サボります' => 'KATAKANA'
 */
private class DefaultSpellingClassifier : SpellingClassifier {

    override fun invoke(string: String): Spelling.Kind =
        string.trim().let { if (it.isEmpty()) null else it }?.let {
            val charClasses = string.map {
                CharacterClassifier.default(it).spellingKind
            }

            if (charClasses.contains(Spelling.Kind.Kanji)) Spelling.Kind.Kanji
            else if (charClasses.contains(Spelling.Kind.Katakana)) Spelling.Kind.Katakana
            else if (charClasses.contains(Spelling.Kind.Hiragana)) Spelling.Kind.Hiragana
            else if (charClasses.contains(Spelling.Kind.CjkPunctuation)) Spelling.Kind.CjkPunctuation
            else if (charClasses.contains(Spelling.Kind.HalfAndFullWidth)) Spelling.Kind.HalfAndFullWidth
            else if (charClasses.contains(Spelling.Kind.Latin)) Spelling.Kind.Latin
            else Spelling.Kind.Unknown
        } ?: Spelling.Kind.Unknown

}

