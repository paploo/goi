package net.paploo.goi.domain.tools.furiganatemplate

sealed interface FuriganaTemplate {

    data class Text(
        val preferred: String
    ) : FuriganaTemplate

    data class Ruby(
        val preferred: String,
        val phonetic: String
    ) : FuriganaTemplate

    data class CurlyBraces(
        val templateString: String
    ) : FuriganaTemplate

    data class Anki(
        val templateString: String
    ) : FuriganaTemplate

}