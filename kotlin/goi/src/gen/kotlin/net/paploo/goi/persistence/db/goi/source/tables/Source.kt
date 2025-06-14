/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.source.tables


import kotlin.collections.Collection

import net.paploo.goi.persistence.db.goi.source.keys.LESSON__LESSON_SOURCE_CODE_FKEY
import net.paploo.goi.persistence.db.goi.source.keys.SOURCE_PKEY
import net.paploo.goi.persistence.db.goi.source.tables.Lesson.LessonPath
import net.paploo.goi.persistence.db.goi.source.tables.records.SourceRecord

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
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
@Suppress("warnings")
open class Source(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, SourceRecord>?,
    parentPath: InverseForeignKey<out Record, SourceRecord>?,
    aliased: Table<SourceRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<SourceRecord>(
    alias,
    net.paploo.goi.persistence.db.goi.source.Source.SOURCE,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table(),
    where,
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

    private constructor(alias: Name, aliased: Table<SourceRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<SourceRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<SourceRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

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

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, SourceRecord>?, parentPath: InverseForeignKey<out Record, SourceRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, SOURCE_, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class SourcePath : Source, Path<SourceRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, SourceRecord>?, parentPath: InverseForeignKey<out Record, SourceRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<SourceRecord>): super(alias, aliased)
        override fun `as`(alias: String): SourcePath = SourcePath(DSL.name(alias), this)
        override fun `as`(alias: Name): SourcePath = SourcePath(alias, this)
        override fun `as`(alias: Table<*>): SourcePath = SourcePath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else net.paploo.goi.persistence.db.goi.source.Source.SOURCE
    override fun getPrimaryKey(): UniqueKey<SourceRecord> = SOURCE_PKEY

    private lateinit var _lesson: LessonPath

    /**
     * Get the implicit to-many join path to the <code>source.lesson</code>
     * table
     */
    fun lesson(): LessonPath {
        if (!this::_lesson.isInitialized)
            _lesson = LessonPath(this, null, LESSON__LESSON_SOURCE_CODE_FKEY.inverseKey)

        return _lesson;
    }

    val lesson: LessonPath
        get(): LessonPath = lesson()
    override fun `as`(alias: String): Source = Source(DSL.name(alias), this)
    override fun `as`(alias: Name): Source = Source(alias, this)
    override fun `as`(alias: Table<*>): Source = Source(alias.qualifiedName, this)

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
    override fun rename(name: Table<*>): Source = Source(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Source = Source(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Source = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Source = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Source = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Source = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Source = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Source = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Source = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Source = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Source = where(DSL.notExists(select))
}
