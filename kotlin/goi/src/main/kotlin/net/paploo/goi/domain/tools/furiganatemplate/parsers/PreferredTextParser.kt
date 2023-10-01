package net.paploo.goi.domain.tools.furiganatemplate.parsers

import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplateParser

class PreferredTextParser : FuriganaTemplateParser<FuriganaTemplate.Text> {
    override fun invoke(furiganaTemplate: FuriganaTemplate.Text): Result<FuriganaParseTree> =
        Result.success(
            FuriganaParseTree(
                elements = listOf(
                    FuriganaParseTree.Element.Text(
                        text = furiganaTemplate.preferred,
                    )
                )
            )
        )

    companion object {
        val default: PreferredTextParser = PreferredTextParser()
    }
}