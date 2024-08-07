/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.source.tables


import java.util.function.Function

import net.paploo.goi.persistence.db.goi.source.keys.SOURCE_PKEY
import net.paploo.goi.persistence.db.goi.source.tables.records.SourceRecord

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row4
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
open class Source(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, SourceRecord>?,
    aliased: Table<SourceRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<SourceRecord>(
    alias,
    net.paploo.goi.persistence.db.goi.source.Source.SOURCE,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>source.source</code>
         */
        val SOURCE_: Source = Source()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<SourceRecord> = SourceRecord::class.java

    /**
     * The column <code>source.source.code</code>.
     */
    val CODE: TableField<SourceRecord, String?> = createField(DSL.name("code"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>source.source.label</code>.
     */
    val LABEL: TableField<SourceRecord, String?> = createField(DSL.name("label"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column <code>source.source.url</code>.
     */
    val URL: TableField<SourceRecord, String?> = createField(DSL.name("url"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>source.source.description</code>.
     */
    val DESCRIPTION: TableField<SourceRecord, String?> = createField(DSL.name("description"), SQLDataType.CLOB, this, "")

    private constructor(alias: Name, aliased: Table<SourceRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<SourceRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>source.source</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>source.source</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>source.source</code> table reference
     */
    constructor(): this(DSL.name("source"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, SourceRecord>): this(Internal.createPathAlias(child, key), child, key, SOURCE_, null)
    override fun getSchema(): Schema? = if (aliased()) null else net.paploo.goi.persistence.db.goi.source.Source.SOURCE
    override fun getPrimaryKey(): UniqueKey<SourceRecord> = SOURCE_PKEY
    override fun `as`(alias: String): Source = Source(DSL.name(alias), this)
    override fun `as`(alias: Name): Source = Source(alias, this)
    override fun `as`(alias: Table<*>): Source = Source(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Source = Source(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Source = Source(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Source = Source(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row4<String?, String?, String?, String?> = super.fieldsRow() as Row4<String?, String?, String?, String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (String?, String?, String?, String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (String?, String?, String?, String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
