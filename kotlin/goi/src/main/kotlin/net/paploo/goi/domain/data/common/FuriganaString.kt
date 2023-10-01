package net.paploo.goi.domain.data.common

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.domain.tools.furiganatemplate.*
import net.paploo.goi.domain.tools.furiganatemplate.parsers.AnkiTemplateParser
import net.paploo.goi.domain.tools.furiganatemplate.parsers.CurlyBracesTemplateParser
import net.paploo.goi.domain.tools.furiganatemplate.parsers.PreferredTextParser
import net.paploo.goi.domain.tools.furiganatemplate.parsers.UnionTemplateParser
import net.paploo.goi.domain.tools.furiganatemplate.transformers.PhoneticSpellingTransformer
import net.paploo.goi.domain.tools.furiganatemplate.transformers.PreferredSpellingTransformer


data class FuriganaString(
    val furiganaTemplate: FuriganaTemplate
) : Valued<String> {

    fun parseTree(): Result<FuriganaParseTree> = parseTree

    fun <T> transform(transformer: FuriganaParseTreeTransformer<T>): Result<T> =
        parseTree().flatMap { transformer(it) }

    override val value: String get() = transform(PreferredSpellingTransformer.default).map { it.value }.getOrDefault("")

    val preferredSpelling: Spelling by lazy { transform(PreferredSpellingTransformer.default).getOrThrow() }

    val phoneticSpelling: Spelling? by lazy { transform(PhoneticSpellingTransformer.default).getOrThrow() }

    private val parseTree: Result<FuriganaParseTree> by lazy {
        when (furiganaTemplate) {
            is FuriganaTemplate.Text -> PreferredTextParser.default(furiganaTemplate)
            is FuriganaTemplate.Ruby -> UnionTemplateParser.default(furiganaTemplate)
            is FuriganaTemplate.CurlyBraces -> CurlyBracesTemplateParser.default(furiganaTemplate)
            is FuriganaTemplate.Anki -> AnkiTemplateParser.default(furiganaTemplate)
        }
    }

    companion object {

        fun fromPreferred(preferred: String): FuriganaString =
            FuriganaString(FuriganaTemplate.Text(preferred))

        fun fromRuby(preferred: String, phonetic: String): FuriganaString =
            FuriganaString(FuriganaTemplate.Ruby(preferred, phonetic))

        fun fromPreferredSpelling(preferredSpelling: Spelling) =
            fromPreferred((preferredSpelling.value))

        fun fromSpellings(preferredSpelling: Spelling, phoneticSpelling: Spelling): FuriganaString =
            fromRuby(preferredSpelling.value, phoneticSpelling.value)

        fun fromCurlyBraces(templateString: String): FuriganaString =
            FuriganaString(FuriganaTemplate.CurlyBraces(templateString))

    }


}

