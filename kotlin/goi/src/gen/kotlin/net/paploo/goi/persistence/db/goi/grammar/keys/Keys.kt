/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.grammar.keys


import net.paploo.goi.persistence.db.goi.grammar.tables.Example
import net.paploo.goi.persistence.db.goi.grammar.tables.ExampleReference
import net.paploo.goi.persistence.db.goi.grammar.tables.Rule
import net.paploo.goi.persistence.db.goi.grammar.tables.RuleReference
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleReferenceRecord
import net.paploo.goi.persistence.db.goi.source.keys.LESSON_PKEY
import net.paploo.goi.persistence.db.goi.source.tables.Lesson
import net.paploo.goi.persistence.db.goi.source.tables.records.LessonRecord

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val EXAMPLE_PKEY: UniqueKey<ExampleRecord> = Internal.createUniqueKey(Example.EXAMPLE, DSL.name("example_pkey"), arrayOf(Example.EXAMPLE.ID), true)
val RULE_PKEY: UniqueKey<RuleRecord> = Internal.createUniqueKey(Rule.RULE, DSL.name("rule_pkey"), arrayOf(Rule.RULE.ID), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val EXAMPLE__EXAMPLE_RULE_ID_FKEY: ForeignKey<ExampleRecord, RuleRecord> = Internal.createForeignKey(Example.EXAMPLE, DSL.name("example_rule_id_fkey"), arrayOf(Example.EXAMPLE.RULE_ID), net.paploo.goi.persistence.db.goi.grammar.keys.RULE_PKEY, arrayOf(Rule.RULE.ID), true)
val EXAMPLE_REFERENCE__EXAMPLE_REFERENCE_EXAMPLE_ID_FKEY: ForeignKey<ExampleReferenceRecord, ExampleRecord> = Internal.createForeignKey(ExampleReference.EXAMPLE_REFERENCE, DSL.name("example_reference_example_id_fkey"), arrayOf(ExampleReference.EXAMPLE_REFERENCE.EXAMPLE_ID), net.paploo.goi.persistence.db.goi.grammar.keys.EXAMPLE_PKEY, arrayOf(Example.EXAMPLE.ID), true)
val EXAMPLE_REFERENCE__EXAMPLE_REFERENCE_LESSON_CODE_FKEY: ForeignKey<ExampleReferenceRecord, LessonRecord> = Internal.createForeignKey(ExampleReference.EXAMPLE_REFERENCE, DSL.name("example_reference_lesson_code_fkey"), arrayOf(ExampleReference.EXAMPLE_REFERENCE.LESSON_CODE), LESSON_PKEY, arrayOf(Lesson.LESSON.CODE), true)
val RULE_REFERENCE__RULE_REFERENCE_LESSON_CODE_FKEY: ForeignKey<RuleReferenceRecord, LessonRecord> = Internal.createForeignKey(RuleReference.RULE_REFERENCE, DSL.name("rule_reference_lesson_code_fkey"), arrayOf(RuleReference.RULE_REFERENCE.LESSON_CODE), LESSON_PKEY, arrayOf(Lesson.LESSON.CODE), true)
val RULE_REFERENCE__RULE_REFERENCE_RULE_ID_FKEY: ForeignKey<RuleReferenceRecord, RuleRecord> = Internal.createForeignKey(RuleReference.RULE_REFERENCE, DSL.name("rule_reference_rule_id_fkey"), arrayOf(RuleReference.RULE_REFERENCE.RULE_ID), net.paploo.goi.persistence.db.goi.grammar.keys.RULE_PKEY, arrayOf(Rule.RULE.ID), true)