package net.paploo.goi.pipeline.core

import net.paploo.goi.common.extensions.flatMap

interface Transformer<T> : suspend (T, Context) -> Result<T> {

    companion object {
        operator fun <T> invoke(block: suspend (T, Context) -> Result<T>): Transformer<T> =
            SuspendFunctionTransformer(block)

        fun <T> lift(block: suspend (T) -> Result<T>): Transformer<T> =
            LiftedSuspendFunctionTransformer(block)

        fun <T> identity(): Transformer<T> =
            invoke { value, _ -> Result.success(value) }

        fun <T> join(first: Transformer<T>, second: Transformer<T>): Transformer<T> =
            JoinTransformer(first, second)

    }

}

operator fun <T> Transformer<T>.plus(other: Transformer<T>): Transformer<T> =
    Transformer.join(this, other)

@JvmInline
private value class SuspendFunctionTransformer<T>(
    private val function: suspend (T, Context) -> Result<T>
) : Transformer<T> {
    override suspend fun invoke(value: T, context: Context): Result<T> = function(value, context)
}

@JvmInline
private value class LiftedSuspendFunctionTransformer<T>(
    private val function: suspend (T) -> Result<T>
) : Transformer<T> {
    override suspend fun invoke(value: T, context: Context): Result<T> = function(value)
}

private class JoinTransformer<T>(
    private val first: Transformer<T>,
    private val second: Transformer<T>
) : Transformer<T> {
    override suspend fun invoke(initialValue: T, context: Context): Result<T> =
        first(initialValue, context).flatMap { second(it, context) }
}
