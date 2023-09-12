package net.paploo.goi.domain.data.common

import net.paploo.goi.common.Valued

enum class JlptLevel(override val value: Int) : Valued<Int> {
    N5(5),
    N4(4),
    N3(3),
    N2(2),
    N1(1),
    N0(0);
}