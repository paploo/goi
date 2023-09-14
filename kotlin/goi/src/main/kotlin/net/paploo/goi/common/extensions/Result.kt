package net.paploo.goi.common.extensions

inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )
