package net.paploo.goi.common.extensions

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

suspend fun <E, R> Iterable<E>.parallelMap(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    transform: suspend (E) -> R
): List<R> = coroutineScope {
    map {
        async(coroutineContext) { transform(it) }
    }.awaitAll()
}