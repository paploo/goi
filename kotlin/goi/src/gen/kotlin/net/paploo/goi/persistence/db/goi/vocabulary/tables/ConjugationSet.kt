/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.vocabulary.tables


import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import net.paploo.goi.persistence.db.goi.vocabulary.Vocabulary
import net.paploo.goi.persistence.db.goi.vocabulary.indexes.CONJUGATION_SET_VOCABULARY_ID_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.indexes.CONJUGATION_SET_VOCABULARY_ID_ID_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.keys.CONJUGATION_SET_PKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.CONJUGATION_SET__CONJUGATION_SET_VOCABULARY_ID_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.ConjugationSetRecord

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row2
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class ConjugationSet(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, ConjugationSetRecord>?,
    aliased: Table<ConjugationSetRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<ConjugationSetRecord>(
    alias,
    Vocabulary.VOCABULARY,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>vocabulary.conjugation_set</code>
         */
        val CONJUGATION_SET: ConjugationSet = ConjugationSet()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<ConjugationSetRecord> = ConjugationSetRecord::class.java

    /**
     * The column <code>vocabulary.conjugation_set.id</code>.
     */
    val ID: TableField<ConjugationSetRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>vocabulary.conjugation_set.vocabulary_id</code>.
     */
    val VOCABULARY_ID: TableField<ConjugationSetRecord, UUID?> = createField(DSL.name("vocabulary_id"), SQLDataType.UUID.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<ConjugationSetRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<ConjugationSetRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vocabulary.conjugation_set</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vocabulary.conjugation_set</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vocabulary.conjugation_set</code> table reference
     */
    constructor(): this(DSL.name("conjugation_set"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, ConjugationSetRecord>): this(Internal.createPathAlias(child, key), child, key, CONJUGATION_SET, null)
    override fun getSchema(): Schema? = if (aliased()) null else Vocabulary.VOCABULARY
    override fun getIndexes(): List<Index> = listOf(CONJUGATION_SET_VOCABULARY_ID_ID_IDX, CONJUGATION_SET_VOCABULARY_ID_IDX)
    override fun getPrimaryKey(): UniqueKey<ConjugationSetRecord> = CONJUGATION_SET_PKEY
    override fun getReferences(): List<ForeignKey<ConjugationSetRecord, *>> = listOf(CONJUGATION_SET__CONJUGATION_SET_VOCABULARY_ID_FKEY)

    private lateinit var _vocabulary: net.paploo.goi.persistence.db.goi.vocabulary.tables.Vocabulary

    /**
     * Get the implicit join path to the <code>vocabulary.vocabulary</code>
     * table.
     */
    fun vocabulary(): net.paploo.goi.persistence.db.goi.vocabulary.tables.Vocabulary {
        if (!this::_vocabulary.isInitialized)
            _vocabulary = net.paploo.goi.persistence.db.goi.vocabulary.tables.Vocabulary(this, CONJUGATION_SET__CONJUGATION_SET_VOCABULARY_ID_FKEY)

        return _vocabulary;
    }

    val vocabulary: net.paploo.goi.persistence.db.goi.vocabulary.tables.Vocabulary
        get(): net.paploo.goi.persistence.db.goi.vocabulary.tables.Vocabulary = vocabulary()
    override fun `as`(alias: String): ConjugationSet = ConjugationSet(DSL.name(alias), this)
    override fun `as`(alias: Name): ConjugationSet = ConjugationSet(alias, this)
    override fun `as`(alias: Table<*>): ConjugationSet = ConjugationSet(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): ConjugationSet = ConjugationSet(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): ConjugationSet = ConjugationSet(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): ConjugationSet = ConjugationSet(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row2<UUID?, UUID?> = super.fieldsRow() as Row2<UUID?, UUID?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, UUID?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, UUID?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}