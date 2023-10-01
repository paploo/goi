package net.paploo.goi.domain.tools.furiganatemplate

interface FuriganaTemplateParser<in P : FuriganaTemplate> : (P) -> Result<FuriganaParseTree> {
    override operator fun invoke(furiganaTemplate: P): Result<FuriganaParseTree>
}
