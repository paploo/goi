package net.paploo.goi.domain.data.common

import net.paploo.goi.common.interfaces.Valued

@JvmInline
value class Tag(override val value: String) : Valued<String>

interface Tagable {
    val tags: Set<Tag>
}