package net.paploo.goi.domain.tools.furiganatemplate.parsers

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.domain.tools.furiganatemplate.*
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateLexer
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParser
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParser.TemplateContext
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParserBaseListener
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.ErrorNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CurlyBracesTemplateParser : FuriganaTemplateParser<FuriganaTemplate.CurlyBraces> {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun invoke(furiganaTemplate: FuriganaTemplate.CurlyBraces): Result<FuriganaParseTree> =
        Result.success(furiganaTemplate).map { template ->
            parser(furiganaTemplate)
        }.mapCatching { parser ->
            Listeners().also { listeners ->
                listeners.addTo(parser)
                logger.debug("Parsing $furiganaTemplate")
                parser.template().also { context ->
                    logger.debug("Parsed {} into {}", furiganaTemplate, context.toStringTree(parser))
                }
            }
        }.flatMap { listeners ->
            logger.debug("listeners.error.errors() = {}", listeners.error.errors())
            logger.debug("listeners.parse.errors() = {}", listeners.parse.errors())
            logger.debug("listeners.parse.parseTree() = {}", listeners.parse.parseTree())

            listeners.firstErrorOrNull()?.let { err ->
                Result.failure(
                    FuriganaTemplateParseException("Could not parse template: $furiganaTemplate because $err", err)
                )
            } ?: Result.success(listeners.parse.parseTree())
        }

    private fun parser(furiganaTemplate: FuriganaTemplate.CurlyBraces): FuriganaCurlyBraceTemplateParser =
        CharStreams.fromString(furiganaTemplate.templateString).let { charStream ->
            FuriganaCurlyBraceTemplateLexer(charStream)
        }.let { lexer ->
            FuriganaCurlyBraceTemplateParser(CommonTokenStream(lexer))
        }.apply {
            errorHandler = DefaultErrorStrategy()
        }

    companion object {
        val default: CurlyBracesTemplateParser = CurlyBracesTemplateParser()
    }

    private data class Listeners(
        val parse: CurlyBracesTemplateParserListener = CurlyBracesTemplateParserListener(),
        val error: CurlyBracesTemplateParserErrorListener = CurlyBracesTemplateParserErrorListener()
    ) {
        fun addTo(parser: FuriganaCurlyBraceTemplateParser): FuriganaCurlyBraceTemplateParser = parser.apply {
            addParseListener(parse)
            addErrorListener(error)
        }

        fun firstErrorOrNull(): Throwable? =
            parse.errors().firstOrNull() ?: error.errors().firstOrNull()
    }

}

private class CurlyBracesTemplateParserListener : FuriganaCurlyBraceTemplateParserBaseListener() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private val errors: MutableList<Throwable> = mutableListOf()

    private var elements: MutableList<FuriganaParseTree.Element> = mutableListOf()

    private var rubyGroupBuffers: RubyGroupBuffers = RubyGroupBuffers()

    fun errors(): List<Throwable> = errors

    fun parseTree(): FuriganaParseTree = FuriganaParseTree(elements.toList())

    override fun enterTemplate(ctx: TemplateContext?) {
        logger.debug("enterTemplate: {}", ctx)
        require(elements.isEmpty()) //Sanity check that we haven't entered twice!
    }

    override fun exitTemplate(ctx: TemplateContext?) {
        logger.debug("exitTemplate: {}", ctx)
    }

    override fun exitString(ctx: FuriganaCurlyBraceTemplateParser.StringContext?) {
        logger.debug("exitString: {}", ctx)
        val text = ctx?.text
        text?.let {
            elements += FuriganaParseTree.Element.Text(it)
        }
    }

    override fun enterRubyGroup(ctx: FuriganaCurlyBraceTemplateParser.RubyGroupContext?) {
        logger.debug("enterRubyGroup: {}", ctx)
        rubyGroupBuffers = RubyGroupBuffers()
    }

    override fun exitRubyGroup(ctx: FuriganaCurlyBraceTemplateParser.RubyGroupContext?) {
        logger.debug("exitRubyGroup: {}, rubyGroupBuffers = {}", ctx, rubyGroupBuffers)
        rubyGroupBuffers.toElements().onSuccess {
            elements.addAll(it)
        }.onFailure {
            errors += it
        }
    }

    override fun exitCjkChar(ctx: FuriganaCurlyBraceTemplateParser.CjkCharContext?) {
        logger.debug("exitCjkChar: {}", ctx)
        ctx?.text?.let {
            rubyGroupBuffers.characters.addAll(it.toList())
        }
    }

    override fun exitNativeRubyText(ctx: FuriganaCurlyBraceTemplateParser.NativeRubyTextContext?) {
        logger.debug("exitNativeRubyText: {}", ctx)
        ctx?.text?.let {
            rubyGroupBuffers.rubyText += it
        }
    }

    override fun exitJpChar(ctx: FuriganaCurlyBraceTemplateParser.JpCharContext?) {
        logger.debug("exitJpChar: {}", ctx)
        ctx?.text?.let {
            rubyGroupBuffers.characters.addAll(it.toList())
        }
    }

    override fun exitPronunciationRubyText(ctx: FuriganaCurlyBraceTemplateParser.PronunciationRubyTextContext?) {
        logger.debug("exitJpChar: {}", ctx)
        ctx?.text?.let {
            rubyGroupBuffers.rubyText += it
        }
    }

    override fun visitErrorNode(node: ErrorNode?) {
        val error = FuriganaTemplateParseException(node.toString())
        logger.warn("Error", error)
        errors += error
    }

    private data class RubyGroupBuffers(
        var characters: MutableList<Char> = mutableListOf(),
        var rubyText: MutableList<String> = mutableListOf(),
    ) {
        fun toElements(): Result<List<FuriganaParseTree.Element>> =
            if (characters.size == rubyText.size) {
                (characters zip rubyText).map { (char, rubyText) ->
                    FuriganaParseTree.Element.RubyText(
                        text = char.toString(),
                        rubyText = rubyText
                    )
                }.let { Result.success(it) }
            } else if (rubyText.size == 1) {
                listOf(
                    FuriganaParseTree.Element.RubyText(
                        text = characters.joinToString(""),
                        rubyText = rubyText.first()
                    )
                ).let { Result.success(it) }
            } else {
                Result.failure(MistmatchedRubyTextLengthException(characters, rubyText))
            }
    }
}

private class CurlyBracesTemplateParserErrorListener : BaseErrorListener() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private val errors: MutableList<Throwable> = mutableListOf()

    fun errors(): List<Throwable> = errors.toList()

    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        val error = FuriganaTemplateSyntaxException(
            offendingSymbol = offendingSymbol,
            line = line,
            charPositionInLine = charPositionInLine,
            msg = msg,
            exception = e
        )
        logger.warn("Error", error)
        errors += error
    }

}
