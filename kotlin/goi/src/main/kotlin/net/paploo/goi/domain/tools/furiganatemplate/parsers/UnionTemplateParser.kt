package net.paploo.goi.domain.tools.furiganatemplate.parsers

import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplateParser

class UnionTemplateParser : FuriganaTemplateParser<FuriganaTemplate.Ruby> {
    override fun invoke(furiganaTemplate: FuriganaTemplate.Ruby): Result<FuriganaParseTree> =
        Result.success(
            FuriganaParseTree(
                elements = listOf(
                    FuriganaParseTree.Element.RubyText(
                        text = furiganaTemplate.preferred,
                        rubyText = furiganaTemplate.phonetic,
                    )
                )
            )
        )

    companion object {
        val default: UnionTemplateParser = UnionTemplateParser()
    }
}
