package net.paploo.goi.domain.data.grammar

import net.paploo.goi.common.interfaces.Identifiable
import net.paploo.goi.common.interfaces.Identifier
import net.paploo.goi.domain.data.common.FuriganaString
import net.paploo.goi.domain.data.common.Tag
import net.paploo.goi.domain.data.common.Tagable
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.data.source.Referencable
import java.util.*

data class Example(
    override val id: Id,
    val meaning: String,
    val text: FuriganaString,
    override val references: Set<Lesson.Code>,
    override val tags: Set<Tag>,
) : Identifiable<Example.Id>, Referencable, Tagable {

    @JvmInline
    value class Id(override val value: UUID) : Identifier<UUID>

}