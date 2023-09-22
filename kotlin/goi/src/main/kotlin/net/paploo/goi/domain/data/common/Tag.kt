package net.paploo.goi.domain.data.common

import net.paploo.goi.common.interfaces.Valued
import java.lang.IllegalArgumentException

@JvmInline
value class Tag(override val value: String) : Valued<String>, Comparable<Tag> {
    init {
        if (!value.matches("^(([a-z0-9])([-a-z0-9])*)$".toRegex())) throw IllegalArgumentException("Tags must be kebab-case, but got '$value'")
    }

    override fun compareTo(other: Tag): Int = this.value compareTo other.value
}

interface Tagable {
    val tags: Set<Tag>
}