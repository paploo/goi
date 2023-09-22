import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.paploo.goi.common.util.TimerLog
import net.paploo.goi.pipeline.core.Exporter
import net.paploo.goi.pipeline.core.Transformer
import net.paploo.goi.pipeline.vocabulary.VocabularyPipeline
import net.paploo.goi.pipeline.vocabulary.exporter.AnkiVocabularyExporter
import net.paploo.goi.pipeline.vocabulary.importer.GoogleSheetVocabularyImporter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolute

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

    timer.mark("fielsDirectory: $filesDirectory")

    timer.markAround("Invoke Application") {
        invokeApplication(timer, logger)
    }
}

val filesDirectory: Path = Path(".", "files").absolute()

operator fun Path.plus(other: Path): Path =
    this.resolve(other)



suspend fun invokeApplication(timer: TimerLog, logger: Logger) {
    val config = VocabularyPipeline.Configuration(
        importer = GoogleSheetVocabularyImporter(GoogleSheetVocabularyImporter.Config(filePath = filesDirectory + Path("日本語 Vocab - Vocab.csv"))),
        transformers = listOf(Transformer.identity()),
        exporters = listOf(AnkiVocabularyExporter(AnkiVocabularyExporter.Config(filePath = filesDirectory + Path("vocabulary", "anki.csv"))))
    )
    val pipeline = VocabularyPipeline(config)
    val result = pipeline()
    timer.mark("completed with status ${result.isSuccess}")

    result.onFailure {
        logger.error("FAILURE", it)
    }.onSuccess {
        logger.info("SUCCESS\n{}", it.size)
    }
}