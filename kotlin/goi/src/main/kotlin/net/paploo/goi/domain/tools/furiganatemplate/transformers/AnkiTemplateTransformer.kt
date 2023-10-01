package net.paploo.goi.domain.tools.furiganatemplate.transformers

import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTreeTransformer
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate

/**
 * Converts the parse tree into the Anki furigana template, e.g. `世[よ]の 中[なか]`
 *
 * @see https://docs.ankiweb.net/templates/fields.html?highlight=tts#ruby-characters
 */
class AnkiTemplateTransformer : FuriganaParseTreeTransformer<FuriganaTemplate.Anki> {

    override fun invoke(tree: FuriganaParseTree): Result<FuriganaTemplate.Anki> =
        tree.elements.mapIndexed { index, element ->
            when (element) {
                is FuriganaParseTree.Element.Text -> element.text
                is FuriganaParseTree.Element.RubyText -> "${rubyGroupSeparator(index)}${element.text}[${element.rubyText}]" //Don't forget the space in front!
            }
        }.let { parts ->
            parts.joinToString("")
        }.let {
            Result.success(FuriganaTemplate.Anki(it))
        }

    //Use spaces to break-up what the ruby text goes with, but don't put a space on hte first element.
    private fun rubyGroupSeparator(index: Int): String =
        if (index > 0) " " else ""

    companion object {
        val default: AnkiTemplateTransformer = AnkiTemplateTransformer()
    }
}
