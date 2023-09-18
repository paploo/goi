package net.paploo.goi.domain.tools.furiganatemplate.transformers

import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTreeTransformer
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate

class AnkiTemplateTransformer : FuriganaParseTreeTransformer<FuriganaTemplate.Anki> {
    override fun invoke(tree: FuriganaParseTree): Result<FuriganaTemplate.Anki> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: AnkiTemplateTransformer = AnkiTemplateTransformer()
    }
}
