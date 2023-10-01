package net.paploo.goi.pipeline.core

import kotlinx.coroutines.Dispatchers
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.parallelMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.util.TimerLog
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Pipeline<out T> : suspend () -> Result<T>

abstract class BasePipeline<T> : Pipeline<T> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    abstract val importer: Importer<T>

    abstract val transformers: Collection<Transformer<T>>

    abstract val exporters: Collection<Exporter<T>>

    override suspend fun invoke(): Result<T> =
        Result.success(
            Context(TimerLog("Pipeline") { logger.info(it.formatted()) })
        ).flatMap { context ->
            context.timerLog.markAround("Execute Pipeline") {
                context.timerLog.mark("# START IMPORT PHASE")
                import(context).flatMap {
                    context.timerLog.mark("# START TRANSFORM PHASE")
                    transform(it, context)
                }.flatMap {
                    context.timerLog.mark("# START EXPORT PHASE")
                    export(it, context)
                }.map {
                    context.timerLog.mark("# END PIPELINE")
                    it
                }
            }.also {
                logger.info(context.timerLog.formatted())
            }
        }

    private suspend fun import(context: Context): Result<T> =
        context.timerLog.markAround("Import with ${importer::class.simpleName}") {
            importer(context)
        }

    private suspend fun transform(value: T, context: Context): Result<T> =
        transformers.fold(Result.success(value)) { valueResult, transformer ->
            context.timerLog.markAround("Transform with ${transformer::class.simpleName}") {
                valueResult.flatMap { transformer(it, context) }
            }
        }

    private suspend fun export(value: T, context: Context): Result<T> =
        exporters.parallelMap(Dispatchers.IO) { exporter ->
            context.timerLog.markAround("Export with ${exporter::class.simpleName}") {
                exporter(value, context)
            }
        }.also {
            logger.info("EXPORT: $it")
        }.sequenceToResult().map { value }

}
