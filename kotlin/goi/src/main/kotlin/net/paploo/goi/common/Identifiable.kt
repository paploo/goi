package net.paploo.goi.common

interface Identifiable<out I: Identifier<*>> {
    val id: I
}

interface Identifier<out T: Any> : Valued<T> {
    override val value: T
}

interface Codable<out C: Coded<*>> {
    val code: C
}

interface Coded<out T: Any> : Valued<T> {
    override val value: T
}