package net.paploo.goi.domain.tools.furiganatemplate

data class FuriganaParseTree(
    val elements: List<Element>
) {

    sealed interface Element {
        data class Text(
            val text: String
        ) : Element

        data class RubyText(
            val text: String,
            val rubyText: String
        ) : Element
    }

}