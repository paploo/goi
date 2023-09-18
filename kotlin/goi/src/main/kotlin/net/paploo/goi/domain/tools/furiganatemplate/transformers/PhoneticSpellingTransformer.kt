package net.paploo.goi.domain.tools.furiganatemplate.transformers

import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTreeTransformer

class PhoneticSpellingTransformer : FuriganaParseTreeTransformer<Spelling> {
    override fun invoke(tree: FuriganaParseTree): Result<Spelling> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: PhoneticSpellingTransformer = PhoneticSpellingTransformer()
    }
}