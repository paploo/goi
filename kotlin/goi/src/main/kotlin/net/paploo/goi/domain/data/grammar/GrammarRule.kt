package net.paploo.goi.domain.data.grammar

import net.paploo.goi.common.Identifiable
import net.paploo.goi.common.Identifier
import net.paploo.goi.domain.data.common.JlptLevel
import net.paploo.goi.domain.data.common.Tag
import net.paploo.goi.domain.data.common.Tagable
import net.paploo.goi.domain.data.source.Lesson
import net.paploo.goi.domain.data.source.Referencable
import java.util.*

data class GrammarRule(
    override val id: Id,
    val title: String,
    val meaning: String,
    val howToUse: List<String>,
    val jlptLevel: JlptLevel?,
    val rowNumber: Int,
    val dateAdded: Int,
    override val references: Set<Lesson>,
    override val tags: Set<Tag>,
    val examples: List<Example>,
) : Identifiable<GrammarRule.Id>, Referencable, Tagable {

    @JvmInline
    value class Id(override val value: UUID) : Identifier<UUID>

}