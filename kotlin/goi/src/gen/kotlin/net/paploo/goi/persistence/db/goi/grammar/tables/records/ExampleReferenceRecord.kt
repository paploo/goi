/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.grammar.tables.records


import java.util.UUID

import net.paploo.goi.persistence.db.goi.grammar.tables.ExampleReference

import org.jooq.impl.TableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("warnings")
open class ExampleReferenceRecord private constructor() : TableRecordImpl<ExampleReferenceRecord>(ExampleReference.EXAMPLE_REFERENCE) {

    open var exampleId: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var lessonCode: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    /**
     * Create a detached, initialised ExampleReferenceRecord
     */
    constructor(exampleId: UUID, lessonCode: String): this() {
        this.exampleId = exampleId
        this.lessonCode = lessonCode
        resetTouchedOnNotNull()
    }
}
