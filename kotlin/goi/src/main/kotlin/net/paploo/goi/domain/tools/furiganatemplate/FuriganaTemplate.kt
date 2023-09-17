package net.paploo.goi.domain.tools.furiganatemplate

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.domain.data.common.Spelling

//TODO: Let's back-up:
// What we should do:
// - We have a template object that can various properties.
// - These are one-to-one related to lexers, which tokenize the templates.
// - Parsers can take those tokens and produce any result.
// From a public perspective:
// - We should hide lexers.
// - We should be able to instantiate templates type-safely.
// - We should directly apply parsers to a template to produce values.
// - We should pass these templates around in places like JpString.
// Other options:
// - We split things up entirely; this means that if you have a template you have to
//   to pick a lexer and parser to apply to get a value. But this is rough because:
//     1. The JpString class can't work on templates unless you also create them with a lexer, and
//     2. Realistically, there is only one meaninful lexer for a template type.
// - We make the various templates part of the lexer, not the other way around.
//     - This probably won't work right with sealing, and
//     - The organizational change doesn't help anything anyway?
// - We actually don't pass around a template, but rather an objet that wraps a template
//   and a lexer. This would use composition to make working with a template easier, but
//   then we lose the ability to pattern match on templates.
// - We use extension methods to make the tokens() and parse() methods; then we only need
//   to set the lexer as a property on the base class, and the implementations just work.
//     - This doesn't work unless the base template type has a parameter OR

//Summary so far:
// If I want to be able to easily use the overarching template type generically and
// if I want to be able to do a when over the sealed classes, then
// I need to make the top-level interface not use type parameters on the arguments
// which forces the rest of the decision hierarchy.

class Temp<out P : Temp.Parameters>(
    val parameters: P
) {

    sealed interface Parameters {
        data class Union(
            val preferredSpelling: Spelling,
            val phoneticSpelling: Spelling
        ) : Parameters

        data class CurlyBraces(
            val templateString: String
        ) : Parameters
    }

}


sealed interface FuriganaTemplate {
    fun tokens(): Result<List<Token>>
    fun <R : TemplateParser.Response> parse(parser: TemplateParser<R>): Result<R>

    //Can't be a template or it seals over it.
    private class LexingDelegate<T : FuriganaTemplate>(
        val template: T,
        val lexer: TemplateLexer<T>
    ) {
        private val tokens: Result<List<Token>> by lazy {
            lexer(template).map { it.tokens }
        }

        fun tokens(): Result<List<Token>> = tokens

        fun <R : TemplateParser.Response> parse(parser: TemplateParser<R>): Result<R> =
            tokens().flatMap { parser(it) }
    }

    data class Union(
        val preferredSpelling: Spelling,
        val phoneticSpelling: Spelling
    ) : FuriganaTemplate {
        private val delegate = LexingDelegate(this, TODO())

        override fun tokens(): Result<List<Token>> = delegate.tokens()

        override fun <R : TemplateParser.Response> parse(parser: TemplateParser<R>): Result<R> = delegate.parse(parser)
    }

    //Uses the input I use in the grammar json
    data class CurlyBraces(
        val templateString: String
    ) : FuriganaTemplate {
        private val lexer: TemplateLexer<CurlyBraces> = TODO()

        override fun tokens(): Result<List<Token>> =
            lexer(this).map { it.tokens }

        override fun <R : TemplateParser.Response> parse(parser: TemplateParser<R>): Result<R> =
            tokens().flatMap { parser(it) }
    }

    //Uses the input
    data class Anki(
        val templateString: String
    ) : FuriganaTemplate {
        private val lexer: TemplateLexer<Anki> = TODO()

        override fun tokens(): Result<List<Token>> =
            lexer(this).map { it.tokens }

        override fun <R : TemplateParser.Response> parse(parser: TemplateParser<R>): Result<R> =
            tokens().flatMap { parser(it) }
    }
}

sealed interface Token {
    val baseText: String
    val rubyText: String?

    data class BaseText(override val baseText: String) : Token {
        override val rubyText: String? = null
    }

    data class RubyText(
        override val baseText: String,
        override val rubyText: String
    ) : Token
}

interface TemplateLexer<in T : FuriganaTemplate> : (T) -> Result<TemplateLexer.Response> {
    override operator fun invoke(template: T): Result<Response>

    data class Response(
        val tokens: List<Token>
    )
}

interface TemplateParser<out R : TemplateParser.Response> : (List<Token>) -> Result<R> {
    override operator fun invoke(tokens: List<Token>): Result<R>

    sealed interface Response {
        // For parsing into spellings
        data class Spellings(
            val preferredSpelling: Spelling,
            val phoneticSpelling: Spelling
        ) : Response

        // For conversion to another template type
        data class Template(
            val template: FuriganaTemplate
        ) : Response
    }

}

interface TemplateSpellingParser : TemplateParser<TemplateParser.Response.Spellings>
interface TemplateConversionParser : TemplateParser<TemplateParser.Response.Template>