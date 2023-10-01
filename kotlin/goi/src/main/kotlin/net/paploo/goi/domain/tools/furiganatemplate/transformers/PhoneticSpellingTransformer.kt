package net.paploo.goi.domain.tools.furiganatemplate.transformers

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.domain.data.common.Spelling
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTreeTransformer
import net.paploo.goi.domain.tools.spellingclassifier.SpellingClassifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException

/**
 * Transforms a given parse tree into a phonetic spelling.
 */
class PhoneticSpellingTransformer : FuriganaParseTreeTransformer<Spelling?> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun invoke(tree: FuriganaParseTree): Result<Spelling?> =
        if (tree.elements.isEmpty()) Result.failure(IllegalArgumentException("Cannot parse empty tree into a phonetic spelling"))
        else process(tree)

    private fun process(tree: FuriganaParseTree): Result<Spelling?> =
        tree.elements.joinToString("") { element ->
            when (element) {
                is FuriganaParseTree.Element.Text -> element.text
                is FuriganaParseTree.Element.RubyText -> element.rubyText
            }
        }.let { string ->
            Result.runCatching { Spelling(string) }
        }.flatMap { spelling ->
            if (!spelling.kind.isKana) logger.warn("phonetic spelling (isKana = {}): {}", spelling.kind.isKana, spelling)
            if (spelling.kind.isKana) Result.success(spelling)
            else Result.success(null)
        }

    companion object {
        val default: PhoneticSpellingTransformer = PhoneticSpellingTransformer()
    }

}
