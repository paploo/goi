package net.paploo.goi.domain.data.source

import net.paploo.goi.common.interfaces.Coded
import java.lang.IllegalArgumentException

//In the future we might want/need a full Lesson data class, but for now we just need the code.
object Lesson{
    @JvmInline
    value class Code(override val value: String) : Coded<String>, Comparable<Code> {
        init {
            if (!value.matches("^(([A-Z])([_A-Z0-9])*)$".toRegex())) throw IllegalArgumentException("Lesson codes must be CONST_CASE, but got '$value'")
        }

        override fun compareTo(other: Code): Int = this.value compareTo other.value
    }
}

interface Referencable {
    val references: Set<Lesson.Code>
}