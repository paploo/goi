package net.paploo.goi.common.interfaces

interface Valued<out T: Any> {
    val value: T
}

interface OptionallyValued<out T: Any> {
    val value: T?
}