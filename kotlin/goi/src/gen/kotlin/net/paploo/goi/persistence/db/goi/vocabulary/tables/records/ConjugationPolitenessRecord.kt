/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.vocabulary.tables.records


import net.paploo.goi.persistence.db.goi.vocabulary.tables.ConjugationPoliteness

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("warnings")
open class ConjugationPolitenessRecord private constructor() : UpdatableRecordImpl<ConjugationPolitenessRecord>(ConjugationPoliteness.CONJUGATION_POLITENESS) {

    open var code: String
        set(value): Unit = set(0, value)
        get(): String = get(0) as String

    open var label: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var sortRank: Int
        set(value): Unit = set(2, value)
        get(): Int = get(2) as Int

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<String?> = super.key() as Record1<String?>

    /**
     * Create a detached, initialised ConjugationPolitenessRecord
     */
    constructor(code: String, label: String, sortRank: Int): this() {
        this.code = code
        this.label = label
        this.sortRank = sortRank
        resetTouchedOnNotNull()
    }
}
