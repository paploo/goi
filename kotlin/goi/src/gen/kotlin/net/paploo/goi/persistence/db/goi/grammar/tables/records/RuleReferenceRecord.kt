/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.grammar.tables.records


import java.util.UUID

import net.paploo.goi.persistence.db.goi.grammar.tables.RuleReference

import org.jooq.Field
import org.jooq.Record2
import org.jooq.Row2
import org.jooq.impl.TableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class RuleReferenceRecord() : TableRecordImpl<RuleReferenceRecord>(RuleReference.RULE_REFERENCE), Record2<UUID?, String?> {

    open var ruleId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var lessonCode: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row2<UUID?, String?> = super.fieldsRow() as Row2<UUID?, String?>
    override fun valuesRow(): Row2<UUID?, String?> = super.valuesRow() as Row2<UUID?, String?>
    override fun field1(): Field<UUID?> = RuleReference.RULE_REFERENCE.RULE_ID
    override fun field2(): Field<String?> = RuleReference.RULE_REFERENCE.LESSON_CODE
    override fun component1(): UUID? = ruleId
    override fun component2(): String? = lessonCode
    override fun value1(): UUID? = ruleId
    override fun value2(): String? = lessonCode

    override fun value1(value: UUID?): RuleReferenceRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): RuleReferenceRecord {
        set(1, value)
        return this
    }

    override fun values(value1: UUID?, value2: String?): RuleReferenceRecord {
        this.value1(value1)
        this.value2(value2)
        return this
    }

    /**
     * Create a detached, initialised RuleReferenceRecord
     */
    constructor(ruleId: UUID? = null, lessonCode: String? = null): this() {
        this.ruleId = ruleId
        this.lessonCode = lessonCode
        resetChangedOnNotNull()
    }
}
