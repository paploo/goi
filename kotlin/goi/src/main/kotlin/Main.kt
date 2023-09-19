import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import net.paploo.goi.common.util.TimerLog
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.kana.KanaTable
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateLexer
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParser
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParserBaseListener
import net.paploo.goi.gen.antlr.FuriganaCurlyBraceTemplateParserListener
import net.paploo.goi.pipeline.core.Exporter
import net.paploo.goi.pipeline.core.Importer
import net.paploo.goi.pipeline.core.Transformer
import net.paploo.goi.pipeline.vocabulary.VocabularyPipeline
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.TerminalNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Auto-created main stub; this links to the "application" declaration in the gradle project, and is the root of the
 * command line commands.
 *
 * TODO: Make an Applicaiton class and main should just call it.
 * Applicaiton should be able to process arguments, hold the overarching timer/logger, and delegate execution to a pipeline.
 */
fun main(args: Array<String>): Unit = runBlocking(Dispatchers.Default) {
    val logger: Logger = LoggerFactory.getLogger("Main.kt")
    val timer = TimerLog("Main.kt") { logger.info(it.formatted()) }
    timer.markAround("Invoke Application") {
        invokeApplication(timer)
    }
}

suspend fun invokeApplication(timer: TimerLog) {
    val config = VocabularyPipeline.Configuration(
        importer = Importer.constValue(emptyList()),
        transformers = listOf(Transformer.identity()),
        exporters = listOf(Exporter.empty())
    )
    val pipeline = VocabularyPipeline(config)
    val result = pipeline()
    timer.mark("completed with $result")
}