package net.paploo.goi.application

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.pascalCase
import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.common.util.TimerLog
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.IllegalArgumentException

/**
 * The root application that processes the arguments and distributes to other applications.
 */
class DistributedApplication : Application<List<String>> {

    private val logger: Logger = LoggerFactory.getLogger("Main.kt")

    /**
     * Polymorphic command arguments.
     *
     * The class type tells you the command to execute, and the wrapped value is the arguments for the given command.
     */
    sealed interface Args<CommandArgs: Any> : Valued<CommandArgs> {
        data class Pipeline(override val value: PipelineApplication.Arguments): Args<PipelineApplication.Arguments>
        data class FuriganaTemplate(override val value: List<String>): Args<List<String>>
    }

    override suspend fun invoke(args: List<String>): Result<Unit> =
        TimerLog("Root Application").markAround("Invoke Root Application with args $args") {
            parseArgs(args).flatMap {
                logger.debug("Parsed args {} as {}", args, it)
                when(it) {
                    is Args.Pipeline -> PipelineApplication().invoke(it.value)
                    is Args.FuriganaTemplate -> FuriganaTemplateApplication().invoke(it.value)
                }
            }
        }

    private fun parseArgs(args: List<String>): Result<Args<*>> =
        when(val command = args.firstOrNull()?.pascalCase()) {
            "Pipeline" -> parsePipelineArgs(args.drop(1))
            "Template" -> parseTemplateArgs(args.drop(1))
            null -> throw IllegalArgumentException("Expected the first argument to be a command, e.g. 'pipeline'")
            else -> throw IllegalArgumentException("Unknown command '$command' on argument list $args")
        }

    private fun parsePipelineArgs(subArgs: List<String>): Result<Args.Pipeline> =
        subArgs.map {
            PipelineApplication.Action.valueOf(it.pascalCase())
        }.let {
            Args.Pipeline(PipelineApplication.Arguments(actions = it))
        }.let { Result.success(it) }

    private fun parseTemplateArgs(subArgs: List<String>): Result<Args.FuriganaTemplate> =
        if (subArgs.isEmpty()) Result.failure(IllegalArgumentException("Expected arguments but got none"))
        else Result.success(Args.FuriganaTemplate(subArgs))

}