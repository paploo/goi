import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.kana.KanaTable
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateLexer
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParser
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParserBaseListener
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParserListener
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    //println(KanaTable["き"])

//    Conjugation.Inflection.entries.forEach {
//        println(it)
//        val name = it.charge.toString() + it.politeness + it.form
//        println("val $name = Inflection(Charge.${it.charge}, Politeness.${it.politeness}, Form.${it.form})")
//    }

    val input = "{九月|く・がつ}に{帰|かえ}ります{ＦＯＯ１２３|ふぐ}。"
    //val input = "{九月|く・がつ}に{帰|かえ}ります"
    //val input = "AB{九|く}CD"
    //val input = "foo{かな}"
    println(input)

    //Pinched from book, section 3.3, p.57
    val charStream = CharStreams.fromString(input)
    val lexer = FuriganaCurlyBraceTemplateLexer(charStream)
    val tokenStream = CommonTokenStream(lexer)
    val parser = FuriganaCurlyBraceTemplateParser(tokenStream)

    println(parser.errorHandler)
    parser.addParseListener(Foo())
    parser.addParseListener(Bar())
    parser.addErrorListener(Eeelo())
    //parser.errorHandler = BailErrorStrategy()

    println(lexer.tokenNames.toList())
    println(parser.ruleNames.toList())

    //The token stream appears to be lazy: as it doesn't fill in until after we parse!
    //println(tokenStream.tokens.joinToString(separator="\n", prefix="<<<\n", postfix="\n>>"))
    //parser.buildParseTree = true
    println("\n\n")
    val context: FuriganaCurlyBraceTemplateParser.TemplateContext = Result.runCatching {
        parser.template()
    }.recover {
        println(it)
        println(it?.cause)
        println(it.message)
        println(it.cause?.message)
        throw it
    }.getOrThrow()
    println("\n\n")
    println(tokenStream.tokens.joinToString(separator = "\n", prefix = "<<<\n", postfix = "\n>>"))
    println(context.toStringTree())
    println(context.toStringTree(parser))
}

class Bar : FuriganaCurlyBraceTemplateParserBaseListener() {

    override fun enterTemplate(ctx: FuriganaCurlyBraceTemplateParser.TemplateContext?) {
        println("* enterTemplate: $ctx")
    }

    override fun enterGroup(ctx: FuriganaCurlyBraceTemplateParser.GroupContext?) {
        println("* enterGroup: $ctx")
    }

    override fun visitErrorNode(node: ErrorNode?) {
        println("! visitErrorNode: $node")
    }
}

class Foo : ParseTreeListener {
    override fun visitTerminal(node: TerminalNode?) {
        println("visitTerminal: $node")
    }

    override fun visitErrorNode(node: ErrorNode?) {
        println("! visitErrorNode: $node")
    }

    override fun enterEveryRule(ctx: ParserRuleContext?) {
        println("enterEveryRule: $ctx")
    }

    override fun exitEveryRule(ctx: ParserRuleContext?) {
        val x = ctx?.ruleIndex
        println("exitEveryRule: $ctx")
    }
}

class Eeelo : BaseErrorListener() {
    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        println("!!!!! syntaxError: $recognizer, $offendingSymbol, $line : $charPositionInLine, $msg")
        e?.let { throw e }
    }

    override fun reportAmbiguity(
        recognizer: Parser?,
        dfa: DFA?,
        startIndex: Int,
        stopIndex: Int,
        exact: Boolean,
        ambigAlts: BitSet?,
        configs: ATNConfigSet?
    ) {
        println("!!!!! reportAmbiguity")
    }

    override fun reportAttemptingFullContext(
        recognizer: Parser?,
        dfa: DFA?,
        startIndex: Int,
        stopIndex: Int,
        conflictingAlts: BitSet?,
        configs: ATNConfigSet?
    ) {
        println("!!!!! reportAttemptingFullContext")
    }

    override fun reportContextSensitivity(
        recognizer: Parser?,
        dfa: DFA?,
        startIndex: Int,
        stopIndex: Int,
        prediction: Int,
        configs: ATNConfigSet?
    ) {
        println("!!!!! reportContextSensitivity")
    }
}