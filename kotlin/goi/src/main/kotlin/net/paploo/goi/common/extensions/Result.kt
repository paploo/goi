package net.paploo.goi.common.extensions

inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )


fun <T> Iterable<Result<T>>.sequenceToResult(): Result<Iterable<T>> {
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