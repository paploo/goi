package net.paploo.goi.common.interfaces

/**
 * Interface for objects that tend to wrap a value.
 *
 * Ofteen these wrappers also wrap auxiliary data to the primary value.
 */
interface Valued<out T: Any> : OptionallyValued<T> {
    override val value: T
}

interface OptionallyValued<out T: Any> {
    val value: T?
}

/**
 * Concrete implementation of Value.
 *
 * This is useful for wrapping a value in a [Valued] type so that it can be used along with other Valued objects.
 */
data class Value<out T : Any>(override val value: T) : Valued<T>

fun <T: Any> T.valued(): Valued<T> = Value(this)

/**
 * Concrete implementation of Value.
 *
 * This is useful for wrapping a value in an [OptionallyValued] type so that it can be used along with other [OptionallyValued] objects.
 */
data class OptionalValue<out T : Any>(override val value: T?) : OptionallyValued<T>

fun <T: Any> T?.optValued(): OptionallyValued<T> = OptionalValue(this)

fun <R: Any, T : R> OptionallyValued<T>.getOrDefault(default: R): Value<R> =
    Value(value ?: default)

fun <R: Any, T : R> OptionallyValued<T>.getOrElse(block: () -> R): Value<R> =
    Value(value ?: block())