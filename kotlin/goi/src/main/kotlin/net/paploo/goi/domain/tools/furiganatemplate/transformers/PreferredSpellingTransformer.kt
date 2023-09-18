package net.paploo.goi.domain.tools.furiganatemplate.transformers

import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTreeTransformer

class PreferredSpellingTransformer : FuriganaParseTreeTransformer<Spelling> {
    override fun invoke(tree: FuriganaParseTree): Result<Spelling> =
        tree.elements.joinToString("") { element ->
            when(element) {
                is FuriganaParseTree.Element.Text -> element.text
                is FuriganaParseTree.Element.RubyText -> element.rubyText
            }
        }.let { Result.success(Spelling(it)) }

    companion object {
        val default: PreferredSpellingTransformer = PreferredSpellingTransformer()
    }
}

