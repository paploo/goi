/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.vocabulary.tables


import kotlin.collections.Collection

import net.paploo.goi.persistence.db.goi.vocabulary.Vocabulary
import net.paploo.goi.persistence.db.goi.vocabulary.keys.CONJUGATION_POLITENESS_PKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.CONJUGATION__CONJUGATION_POLITENESS_CODE_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.tables.Conjugation.ConjugationPath
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.ConjugationPolitenessRecord

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
open class ConjugationPoliteness(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, ConjugationPolitenessRecord>?,
    parentPath: InverseForeignKey<out Record, ConjugationPolitenessRecord>?,
    aliased: Table<ConjugationPolitenessRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<ConjugationPolitenessRecord>(
    alias,
    Vocabulary.VOCABULARY,
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
         * The reference instance of
         * <code>vocabulary.conjugation_politeness</code>
         */
        val CONJUGATION_POLITENESS: ConjugationPoliteness = ConjugationPoliteness()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<ConjugationPolitenessRecord> = ConjugationPolitenessRecord::class.java

    /**
     * The column <code>vocabulary.conjugation_politeness.code</code>.
     */
    val CODE: TableField<ConjugationPolitenessRecord, String?> = createField(DSL.name("code"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>vocabulary.conjugation_politeness.label</code>.
     */
    val LABEL: TableField<ConjugationPolitenessRecord, String?> = createField(DSL.name("label"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column <code>vocabulary.conjugation_politeness.sort_rank</code>.
     */
    val SORT_RANK: TableField<ConjugationPolitenessRecord, Int?> = createField(DSL.name("sort_rank"), SQLDataType.INTEGER.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<ConjugationPolitenessRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<ConjugationPolitenessRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<ConjugationPolitenessRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>vocabulary.conjugation_politeness</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vocabulary.conjugation_politeness</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vocabulary.conjugation_politeness</code> table reference
     */
    constructor(): this(DSL.name("conjugation_politeness"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, ConjugationPolitenessRecord>?, parentPath: InverseForeignKey<out Record, ConjugationPolitenessRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, CONJUGATION_POLITENESS, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class ConjugationPolitenessPath : ConjugationPoliteness, Path<ConjugationPolitenessRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, ConjugationPolitenessRecord>?, parentPath: InverseForeignKey<out Record, ConjugationPolitenessRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<ConjugationPolitenessRecord>): super(alias, aliased)
        override fun `as`(alias: String): ConjugationPolitenessPath = ConjugationPolitenessPath(DSL.name(alias), this)
        override fun `as`(alias: Name): ConjugationPolitenessPath = ConjugationPolitenessPath(alias, this)
        override fun `as`(alias: Table<*>): ConjugationPolitenessPath = ConjugationPolitenessPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Vocabulary.VOCABULARY
    override fun getPrimaryKey(): UniqueKey<ConjugationPolitenessRecord> = CONJUGATION_POLITENESS_PKEY

    private lateinit var _conjugation: ConjugationPath

    /**
     * Get the implicit to-many join path to the
     * <code>vocabulary.conjugation</code> table
     */
    fun conjugation(): ConjugationPath {
        if (!this::_conjugation.isInitialized)
            _conjugation = ConjugationPath(this, null, CONJUGATION__CONJUGATION_POLITENESS_CODE_FKEY.inverseKey)

        return _conjugation;
    }

    val conjugation: ConjugationPath
        get(): ConjugationPath = conjugation()
    override fun `as`(alias: String): ConjugationPoliteness = ConjugationPoliteness(DSL.name(alias), this)
    override fun `as`(alias: Name): ConjugationPoliteness = ConjugationPoliteness(alias, this)
    override fun `as`(alias: Table<*>): ConjugationPoliteness = ConjugationPoliteness(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): ConjugationPoliteness = ConjugationPoliteness(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): ConjugationPoliteness = ConjugationPoliteness(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): ConjugationPoliteness = ConjugationPoliteness(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): ConjugationPoliteness = ConjugationPoliteness(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): ConjugationPoliteness = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): ConjugationPoliteness = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): ConjugationPoliteness = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): ConjugationPoliteness = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): ConjugationPoliteness = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): ConjugationPoliteness = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): ConjugationPoliteness = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): ConjugationPoliteness = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): ConjugationPoliteness = where(DSL.notExists(select))
}
