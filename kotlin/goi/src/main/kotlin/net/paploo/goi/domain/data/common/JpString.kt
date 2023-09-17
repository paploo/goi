package net.paploo.goi.domain.data.common

import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate

//TODO: It seems like I might've mixed a couple concepts in the Ruby code. Reqs:
// - We have to be able to get a preferred and phonetic spellings.
// - We also have a need to convert to a template that can get converted to various template outputs.
// - We can instantiate as either a preferred/phonetic and derive a template and derive the spellings.
// -

sealed interface JpString : Valued<String> {
    val preferredSpelling: Spelling
    val phoneticSpelling: Spelling
    val furiganaTemplate: FuriganaTemplate

    override val value: String get() = preferredSpelling.value

    data class Union(
        override val preferredSpelling: Spelling,
        override val phoneticSpelling: Spelling
    ) : JpString {

        override val furiganaSpelling: FuriganaTemplate
            get() = FuriganaTemplate(preferredSpelling = preferredSpelling, phoneticSpelling = phoneticSpelling)

    }

    data class Furigana(
        val template: FuriganaTemplate
    ) : JpString {

        override val preferredSpelling: Spelling = TODO("Parse from template")

        override val phoneticSpelling: Spelling = TODO("Parse from template")

        override val furiganaTemplate: FuriganaTemplate = template

    }

}