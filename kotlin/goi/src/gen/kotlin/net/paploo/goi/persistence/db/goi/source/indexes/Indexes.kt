@file:Suppress("warnings")
/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.source.indexes


import net.paploo.goi.persistence.db.goi.source.tables.Lesson

import org.jooq.Index
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// INDEX definitions
// -------------------------------------------------------------------------

val LESSON_SECTION_NUMBER_SUBSECTION_NUMBER_IDX: Index = Internal.createIndex(DSL.name("lesson_section_number_subsection_number_idx"), Lesson.LESSON, arrayOf(Lesson.LESSON.SECTION_NUMBER, Lesson.LESSON.SUBSECTION_NUMBER), false)
val LESSON_SOURCE_CODE_IDX: Index = Internal.createIndex(DSL.name("lesson_source_code_idx"), Lesson.LESSON, arrayOf(Lesson.LESSON.SOURCE_CODE), false)
val LESSON_SOURCE_CODE_LESSON_CODE_IDX: Index = Internal.createIndex(DSL.name("lesson_source_code_lesson_code_idx"), Lesson.LESSON, arrayOf(Lesson.LESSON.SOURCE_CODE, Lesson.LESSON.LESSON_CODE), true)
