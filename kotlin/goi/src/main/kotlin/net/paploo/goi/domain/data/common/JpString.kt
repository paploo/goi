package net.paploo.goi.domain.data.common

import net.paploo.goi.common.Valued

sealed interface JpString : Valued<String> {
    val preferredSpelling: Spelling
    val phoneticSpelling: Spelling
    val furiganaSpelling: Furigana

    override val value: String get() = preferredSpelling.value

    data class Union(
        override val preferredSpelling: Spelling,
        override val phoneticSpelling: Spelling
    ) : JpString {

        override val furiganaSpelling: Furigana
            get() = Furigana(preferredSpelling = preferredSpelling, phoneticSpelling = phoneticSpelling)

    }

    data class Furigana(
        val template: String
    ) : JpString {

        override val preferredSpelling: Spelling = TODO("Parse from template")

        override val phoneticSpelling: Spelling = TODO("Parse from template")

        override val furiganaSpelling: Furigana = this

        companion object {

            operator fun invoke(preferredSpelling: Spelling, phoneticSpelling: Spelling): Furigana = TODO("Build template from parts")

        }

    }


}