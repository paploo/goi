package net.paploo.goi.pipeline.core

interface Exporter<in T> : suspend (T, Context) -> Result<Unit> {

    companion object {
        operator fun <T> invoke(block: suspend (T, Context) -> Result<Unit>): Exporter<T> =
            SuspendFunctionExporter(block)

        fun <T> lift(block: suspend (T) -> Result<Unit>): Exporter<T> =
            LiftedSuspendFunctionExporter(block)

        fun <T> empty(): Exporter<T> =
            invoke { _, _ -> Result.success(Unit) }
    }

}

@JvmInline
private value class SuspendFunctionExporter<T>(
    private val function: suspend (T, Context) -> Result<Unit>
) : Exporter<T> {
    override suspend fun invoke(value: T, context: Context): Result<Unit> = function(value, context)
}


@JvmInline
private value class LiftedSuspendFunctionExporter<T>(
    private val function: suspend (T) -> Result<Unit>
) : Exporter<T> {
    override suspend fun invoke(value: T, context: Context): Result<Unit> = function(value)
}