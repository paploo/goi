package net.paploo.goi.common.extensions

inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )

inline fun <T> Result<T>.mapFailure(transform: (Throwable) -> Throwable): Result<T> =
    fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(transform(it)) }
    )

inline fun <T> Result<T>.finally(block: () -> Unit): Result<T> =
    flatFinally { Result.runCatching { block() } }

inline fun <T> Result<T>.flatFinally(block: () -> Result<Any>): Result<T> =
    fold(
        onSuccess = { t -> block().map { t } },
        onFailure = { e -> block().flatMap { Result.failure(e) } }
    )

fun <T> Iterable<Result<T>>.sequenceToResult(): Result<List<T>> {
    val buffer: MutableList<T> = mutableListOf()
    for (elem in this) {
        elem.onSuccess {
            buffer += it
        }.onFailure {
            return Result.failure(it)
        }
    }
    return Result.success(buffer.toList())
}

fun <T : Any> Result<T?>.sequenceToNullable(): Result<T>? =
    fold(
        onSuccess = { nullable -> nullable?.let { Result.success(it) } },
        onFailure = { Result.failure(it) }
    )

fun <T : Any> Result<T>?.sequenceToResult(): Result<T?> =
    this ?: Result.success(null)