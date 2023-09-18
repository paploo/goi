package net.paploo.goi.domain.tools.furiganatemplate

import org.antlr.v4.runtime.RecognitionException

open class FuriganaTemplateParseException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

data class FuriganaTemplateSyntaxException(
    val offendingSymbol: Any?,
    val line: Int,
    val charPositionInLine: Int,
    val msg: String?,
    val exception: RecognitionException? = null
) : FuriganaTemplateParseException(msg ?: "Unknown parse error at $line:$charPositionInLine", exception)

data class MistmatchedRubyTextLengthException(
    val characters: List<Char>,
    val rubyText: List<String>
) : FuriganaTemplateParseException("Expected match in size of characters to text: characters = $characters, rubyText = $rubyText")