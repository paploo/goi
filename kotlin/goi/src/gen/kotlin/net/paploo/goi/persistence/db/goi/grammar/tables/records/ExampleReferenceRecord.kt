/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.grammar.tables.records


import java.util.UUID

import net.paploo.goi.persistence.db.goi.grammar.tables.ExampleReference

import org.jooq.Field
import org.jooq.Record2
import org.jooq.Row2
import org.jooq.impl.TableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class ExampleReferenceRecord() : TableRecordImpl<ExampleReferenceRecord>(ExampleReference.EXAMPLE_REFERENCE), Record2<UUID?, String?> {

    open var exampleId: UUID?
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
    override fun field1(): Field<UUID?> = ExampleReference.EXAMPLE_REFERENCE.EXAMPLE_ID
    override fun field2(): Field<String?> = ExampleReference.EXAMPLE_REFERENCE.LESSON_CODE
    override fun component1(): UUID? = exampleId
    override fun component2(): String? = lessonCode
    override fun value1(): UUID? = exampleId
    override fun value2(): String? = lessonCode

    override fun value1(value: UUID?): ExampleReferenceRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): ExampleReferenceRecord {
        set(1, value)
        return this
    }

    override fun values(value1: UUID?, value2: String?): ExampleReferenceRecord {
        this.value1(value1)
        this.value2(value2)
        return this
    }

    /**
     * Create a detached, initialised ExampleReferenceRecord
     */
    constructor(exampleId: UUID? = null, lessonCode: String? = null): this() {
        this.exampleId = exampleId
        this.lessonCode = lessonCode
        resetChangedOnNotNull()
    }
}
