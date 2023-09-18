package net.paploo.goi.domain.tools.furiganatemplate

interface FuriganaParseTreeTransformer<out T> : (FuriganaParseTree) -> Result<T> {
    override operator fun invoke(tree: FuriganaParseTree): Result<T>
}

