package net.paploo.goi.domain.tools.furiganatemplate

data class FuriganaParseTree(
    val elements: List<Element>
) {

    sealed interface Element {
        val text: String

        data class Text(
            override val text: String
        ) : Element

        data class RubyText(
            override val text: String,
            val rubyText: String
        ) : Element
    }

}