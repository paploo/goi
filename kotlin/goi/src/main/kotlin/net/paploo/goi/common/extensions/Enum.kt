package net.paploo.goi.common.extensions

import java.lang.IllegalArgumentException
import java.util.NoSuchElementException

inline fun <reified E: Enum<E>> enumResultValueOf(name: String): Result<E> = Result.runCatching {
    try {
        enumValueOf(name)
    } catch (e: IllegalArgumentException) {
        throw NoSuchElementException("No enum value ${E::class.simpleName}.$name, must be one of ${enumValues<E>().toList()}")
    }
}