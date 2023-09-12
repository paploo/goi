package net.paploo.goi.domain.data.source

import net.paploo.goi.common.interfaces.Coded

//In the future we might want/need a full Lesson data class, but for now we just need the code.
object Lesson{
    @JvmInline
    value class Code(override val value: String) : Coded<String>
}

interface Referencable {
    val references: Set<Lesson>
}