import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.paploo.goi.application.DistributedApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit = runBlocking(Dispatchers.Default) {
    val logger: Logger = LoggerFactory.getLogger("Main.kt")

    DistributedApplication().invoke(
        args.toList()
    ).onFailure {
        logger.error("FATAL ERROR ON APPLICATION RUN: $it", it)
    }.getOrThrow() //At the top level here, we throw and hard fail with a bad return code.
}
