package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.interfaces.Valued

data class Definition(
    override val value: String,
    val sort_rank: Int?,
) : Valued<String>