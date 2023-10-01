package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.interfaces.Valued
import java.lang.IllegalArgumentException

data class Definition(
    override val value: String,
    val sortRank: Int?,
) : Valued<String> {

    init {
        if (value.isEmpty()) throw IllegalArgumentException("${this::class.simpleName} must contain a value, but got an empty string.")
    }

}