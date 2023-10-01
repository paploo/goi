package net.paploo.goi.domain.data.common

import net.paploo.goi.common.extensions.enumResultValueOf
import net.paploo.goi.common.interfaces.Valued

enum class JlptLevel(val levelNumber: Int) {
    N5(5),
    N4(4),
    N3(3),
    N2(2),
    N1(1),
    N0(0);

    companion object {
        fun forLevelNumber(value: Int): Result<JlptLevel> =
            forLevelNumber(value.toString())

        fun forLevelNumber(value: String): Result<JlptLevel> =
            enumResultValueOf<JlptLevel>("N${value}")

    }

}