package net.paploo.goi.domain.tools.furiganatemplate

import net.paploo.goi.domain.data.common.Spelling

interface FuriganaParseTreeTransformer<out T> : (FuriganaParseTree) -> Result<T> {
    override operator fun invoke(tree: FuriganaParseTree): Result<T>
}

class PreferredSpellingTransformer : FuriganaParseTreeTransformer<Spelling> {
    override fun invoke(tree: FuriganaParseTree): Result<Spelling> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: PreferredSpellingTransformer = PreferredSpellingTransformer()
    }
}

class PhoneticSpellingTransformer : FuriganaParseTreeTransformer<Spelling> {
    override fun invoke(tree: FuriganaParseTree): Result<Spelling> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: PhoneticSpellingTransformer = PhoneticSpellingTransformer()
    }
}

class AnkiTemplateTransformer : FuriganaParseTreeTransformer<FuriganaTemplate.Anki> {
    override fun invoke(tree: FuriganaParseTree): Result<FuriganaTemplate.Anki> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: AnkiTemplateTransformer = AnkiTemplateTransformer()
    }
}
