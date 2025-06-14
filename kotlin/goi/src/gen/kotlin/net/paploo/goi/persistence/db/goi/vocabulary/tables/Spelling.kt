/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.vocabulary.tables


import java.util.UUID

import kotlin.collections.Collection
import kotlin.collections.List

import net.paploo.goi.persistence.db.goi.vocabulary.Vocabulary
import net.paploo.goi.persistence.db.goi.vocabulary.indexes.SPELLING_SPELLING_KIND_CODE_VALUE_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.indexes.SPELLING_VALUE_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.indexes.SPELLING_VOCABULARY_ID_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.indexes.SPELLING_VOCABULARY_ID_ID_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.keys.SPELLING_PKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.SPELLING__SPELLING_SPELLING_KIND_CODE_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.SPELLING__SPELLING_VOCABULARY_ID_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.tables.SpellingKind.SpellingKindPath
import net.paploo.goi.persistence.db.goi.vocabulary.tables.Vocabulary.VocabularyPath
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.SpellingRecord

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
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
open class Spelling(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, SpellingRecord>?,
    parentPath: InverseForeignKey<out Record, SpellingRecord>?,
    aliased: Table<SpellingRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<SpellingRecord>(
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
         * The reference instance of <code>vocabulary.spelling</code>
         */
        val SPELLING: Spelling = Spelling()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<SpellingRecord> = SpellingRecord::class.java

    /**
     * The column <code>vocabulary.spelling.id</code>.
     */
    val ID: TableField<SpellingRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>vocabulary.spelling.vocabulary_id</code>.
     */
    val VOCABULARY_ID: TableField<SpellingRecord, UUID?> = createField(DSL.name("vocabulary_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>vocabulary.spelling.spelling_kind_code</code>.
     */
    val SPELLING_KIND_CODE: TableField<SpellingRecord, String?> = createField(DSL.name("spelling_kind_code"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>vocabulary.spelling.value</code>.
     */
    val VALUE: TableField<SpellingRecord, String?> = createField(DSL.name("value"), SQLDataType.CLOB.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<SpellingRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<SpellingRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<SpellingRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>vocabulary.spelling</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vocabulary.spelling</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vocabulary.spelling</code> table reference
     */
    constructor(): this(DSL.name("spelling"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, SpellingRecord>?, parentPath: InverseForeignKey<out Record, SpellingRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, SPELLING, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class SpellingPath : Spelling, Path<SpellingRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, SpellingRecord>?, parentPath: InverseForeignKey<out Record, SpellingRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<SpellingRecord>): super(alias, aliased)
        override fun `as`(alias: String): SpellingPath = SpellingPath(DSL.name(alias), this)
        override fun `as`(alias: Name): SpellingPath = SpellingPath(alias, this)
        override fun `as`(alias: Table<*>): SpellingPath = SpellingPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Vocabulary.VOCABULARY
    override fun getIndexes(): List<Index> = listOf(SPELLING_SPELLING_KIND_CODE_VALUE_IDX, SPELLING_VALUE_IDX, SPELLING_VOCABULARY_ID_ID_IDX, SPELLING_VOCABULARY_ID_IDX)
    override fun getPrimaryKey(): UniqueKey<SpellingRecord> = SPELLING_PKEY
    override fun getReferences(): List<ForeignKey<SpellingRecord, *>> = listOf(SPELLING__SPELLING_SPELLING_KIND_CODE_FKEY, SPELLING__SPELLING_VOCABULARY_ID_FKEY)

    /**
     * Get the implicit join path to the <code>vocabulary.spelling_kind</code>
     * table.
     */
    fun spellingKind(): SpellingKindPath = spellingKind
    val spellingKind: SpellingKindPath by lazy { SpellingKindPath(this, SPELLING__SPELLING_SPELLING_KIND_CODE_FKEY, null) }

    /**
     * Get the implicit join path to the <code>vocabulary.vocabulary</code>
     * table.
     */
    fun vocabulary(): VocabularyPath = vocabulary
    val vocabulary: VocabularyPath by lazy { VocabularyPath(this, SPELLING__SPELLING_VOCABULARY_ID_FKEY, null) }
    override fun `as`(alias: String): Spelling = Spelling(DSL.name(alias), this)
    override fun `as`(alias: Name): Spelling = Spelling(alias, this)
    override fun `as`(alias: Table<*>): Spelling = Spelling(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Spelling = Spelling(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Spelling = Spelling(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Spelling = Spelling(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Spelling = Spelling(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Spelling = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Spelling = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Spelling = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Spelling = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Spelling = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Spelling = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Spelling = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Spelling = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Spelling = where(DSL.notExists(select))
}
