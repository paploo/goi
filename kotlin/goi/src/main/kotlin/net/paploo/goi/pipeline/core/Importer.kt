package net.paploo.goi.pipeline.core

interface Importer<out T> : suspend (Context) -> Result<T> {

    companion object {
        operator fun <T> invoke(block: suspend (Context) -> Result<T>): Importer<T> =
            SuspendFunctionImporter(block)

        fun <T> lift(block: suspend () -> Result<T>): Importer<T> =
            LiftedSuspendFunctionImporter(block)

        fun <T> const(result: Result<T>): Importer<T> =
            invoke { _ -> result }

        fun <T> constValue(value: T): Importer<T> =
            const(Result.success((value)))

    }

}

@JvmInline
private value class LiftedSuspendFunctionImporter<T>(
    private val function: suspend () -> Result<T>
) : Importer<T> {
    override suspend fun invoke(context: Context): Result<T> = function()
}

@JvmInline
private value class SuspendFunctionImporter<T>(
    private val function: suspend (Context) -> Result<T>
) : Importer<T> {
    override suspend fun invoke(context: Context): Result<T> = function(context)
}