package net.paploo.goi.domain.tools.furiganatemplate.parsers

import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplateParser

class AnkiTemplateParser : FuriganaTemplateParser<FuriganaTemplate.Anki> {
    override fun invoke(furiganaTemplate: FuriganaTemplate.Anki): Result<FuriganaParseTree> {
        TODO("Anki template parsing is not yest supported.")
    }

    companion object {
        val default: AnkiTemplateParser = AnkiTemplateParser()
    }
}