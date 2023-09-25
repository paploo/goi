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

//TODO: Write tests
fun <T : Any> Result<T>?.sequenceToResult(): Result<T?> =
    this ?: Result.success(null)