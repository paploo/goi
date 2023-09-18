package net.paploo.goi.domain.tools.furiganatemplate.transformers

import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTreeTransformer

class PreferredSpellingTransformer : FuriganaParseTreeTransformer<Spelling> {
    override fun invoke(tree: FuriganaParseTree): Result<Spelling> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: PreferredSpellingTransformer = PreferredSpellingTransformer()
    }
}

