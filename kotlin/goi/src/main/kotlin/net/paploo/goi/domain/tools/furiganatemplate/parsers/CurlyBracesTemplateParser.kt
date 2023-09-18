package net.paploo.goi.domain.tools.furiganatemplate.parsers

import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplateParser

class CurlyBracesTemplateParser : FuriganaTemplateParser<FuriganaTemplate.CurlyBraces> {
    override fun invoke(furiganaTemplate: FuriganaTemplate.CurlyBraces): Result<FuriganaParseTree> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: CurlyBracesTemplateParser = CurlyBracesTemplateParser()
    }
}