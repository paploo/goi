/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.grammar.indexes


import net.paploo.goi.persistence.db.goi.grammar.tables.Example
import net.paploo.goi.persistence.db.goi.grammar.tables.ExampleReference
import net.paploo.goi.persistence.db.goi.grammar.tables.RuleReference

import org.jooq.Index
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// INDEX definitions
// -------------------------------------------------------------------------

val EXAMPLE_REFERENCE_EXAMPLE_ID_LESSON_CODE_IDX: Index = Internal.createIndex(DSL.name("example_reference_example_id_lesson_code_idx"), ExampleReference.EXAMPLE_REFERENCE, arrayOf(ExampleReference.EXAMPLE_REFERENCE.EXAMPLE_ID, ExampleReference.EXAMPLE_REFERENCE.LESSON_CODE), true)
val EXAMPLE_REFERENCE_LESSON_CODE_IDX: Index = Internal.createIndex(DSL.name("example_reference_lesson_code_idx"), ExampleReference.EXAMPLE_REFERENCE, arrayOf(ExampleReference.EXAMPLE_REFERENCE.LESSON_CODE), false)
val EXAMPLE_RULE_ID_IDX: Index = Internal.createIndex(DSL.name("example_rule_id_idx"), Example.EXAMPLE, arrayOf(Example.EXAMPLE.RULE_ID), false)
val RULE_REFERENCE_LESSON_CODE_IDX: Index = Internal.createIndex(DSL.name("rule_reference_lesson_code_idx"), RuleReference.RULE_REFERENCE, arrayOf(RuleReference.RULE_REFERENCE.LESSON_CODE), false)
val RULE_REFERENCE_RULE_ID_LESSON_CODE_IDX: Index = Internal.createIndex(DSL.name("rule_reference_rule_id_lesson_code_idx"), RuleReference.RULE_REFERENCE, arrayOf(RuleReference.RULE_REFERENCE.RULE_ID, RuleReference.RULE_REFERENCE.LESSON_CODE), true)