import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import net.paploo.goi.common.util.TimerLog
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

fun main(args: Array<String>): Unit = runBlocking(Dispatchers.Default) {
    println("Hello World!")

    listOf(
        async { testTimer() },
        async { testTimer() }
    ).awaitAll()

}

fun testTimer() {
    val timer = TimerLog("foo") { println(it.formatted()) }

    timer.mark("foo")
    timer.mark("bar")
    val x = timer.markAround("asdf") {
        100 + 100 / 2.0
    }
    timer.mark(x.toString())

    println(timer.formatted())
}