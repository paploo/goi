package net.paploo.goi.domain.tools.furiganatemplate

interface FuriganaTemplateParser<in P : FuriganaTemplate> : (P) -> Result<FuriganaParseTree> {
    override operator fun invoke(furiganaTemplate: P): Result<FuriganaParseTree>
}

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

class AnkiTemplateParser : FuriganaTemplateParser<FuriganaTemplate.Anki> {
    override fun invoke(furiganaTemplate: FuriganaTemplate.Anki): Result<FuriganaParseTree> {
        TODO("Anki template parsing is not yest supported.")
    }

    companion object {
        val default: AnkiTemplateParser = AnkiTemplateParser()
    }
}

class CurlyBracesTemplateParser : FuriganaTemplateParser<FuriganaTemplate.CurlyBraces> {
    override fun invoke(furiganaTemplate: FuriganaTemplate.CurlyBraces): Result<FuriganaParseTree> {
        TODO("Not yet implemented")
    }

    companion object {
        val default: CurlyBracesTemplateParser = CurlyBracesTemplateParser()
    }
}