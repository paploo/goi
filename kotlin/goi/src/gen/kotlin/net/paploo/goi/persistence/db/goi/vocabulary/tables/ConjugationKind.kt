/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.vocabulary.tables


import java.util.function.Function

import kotlin.collections.List

import net.paploo.goi.persistence.db.goi.vocabulary.Vocabulary
import net.paploo.goi.persistence.db.goi.vocabulary.keys.CONJUGATION_KIND_PKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.CONJUGATION_KIND__CONJUGATION_KIND_WORD_CLASS_CODE_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.ConjugationKindRecord

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row3
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
open class ConjugationKind(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, ConjugationKindRecord>?,
    aliased: Table<ConjugationKindRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<ConjugationKindRecord>(
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
         * The reference instance of <code>vocabulary.conjugation_kind</code>
         */
        val CONJUGATION_KIND: ConjugationKind = ConjugationKind()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<ConjugationKindRecord> = ConjugationKindRecord::class.java

    /**
     * The column <code>vocabulary.conjugation_kind.code</code>.
     */
    val CODE: TableField<ConjugationKindRecord, String?> = createField(DSL.name("code"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>vocabulary.conjugation_kind.label</code>.
     */
    val LABEL: TableField<ConjugationKindRecord, String?> = createField(DSL.name("label"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column <code>vocabulary.conjugation_kind.word_class_code</code>.
     */
    val WORD_CLASS_CODE: TableField<ConjugationKindRecord, String?> = createField(DSL.name("word_class_code"), SQLDataType.VARCHAR.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<ConjugationKindRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<ConjugationKindRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vocabulary.conjugation_kind</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vocabulary.conjugation_kind</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vocabulary.conjugation_kind</code> table reference
     */
    constructor(): this(DSL.name("conjugation_kind"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, ConjugationKindRecord>): this(Internal.createPathAlias(child, key), child, key, CONJUGATION_KIND, null)
    override fun getSchema(): Schema? = if (aliased()) null else Vocabulary.VOCABULARY
    override fun getPrimaryKey(): UniqueKey<ConjugationKindRecord> = CONJUGATION_KIND_PKEY
    override fun getReferences(): List<ForeignKey<ConjugationKindRecord, *>> = listOf(CONJUGATION_KIND__CONJUGATION_KIND_WORD_CLASS_CODE_FKEY)

    private lateinit var _wordClass: WordClass

    /**
     * Get the implicit join path to the <code>vocabulary.word_class</code>
     * table.
     */
    fun wordClass(): WordClass {
        if (!this::_wordClass.isInitialized)
            _wordClass = WordClass(this, CONJUGATION_KIND__CONJUGATION_KIND_WORD_CLASS_CODE_FKEY)

        return _wordClass;
    }

    val wordClass: WordClass
        get(): WordClass = wordClass()
    override fun `as`(alias: String): ConjugationKind = ConjugationKind(DSL.name(alias), this)
    override fun `as`(alias: Name): ConjugationKind = ConjugationKind(alias, this)
    override fun `as`(alias: Table<*>): ConjugationKind = ConjugationKind(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): ConjugationKind = ConjugationKind(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): ConjugationKind = ConjugationKind(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): ConjugationKind = ConjugationKind(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<String?, String?, String?> = super.fieldsRow() as Row3<String?, String?, String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (String?, String?, String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (String?, String?, String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
