/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.vocabulary.tables


import java.time.LocalDate
import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import net.paploo.goi.persistence.db.goi.vocabulary.indexes.VOCABULARY_TAGS_IDX
import net.paploo.goi.persistence.db.goi.vocabulary.keys.VOCABULARY_PKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.VOCABULARY__VOCABULARY_CONJUGATION_KIND_CODE_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.keys.VOCABULARY__VOCABULARY_WORD_CLASS_CODE_FKEY
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.VocabularyRecord

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row7
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
open class Vocabulary(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, VocabularyRecord>?,
    aliased: Table<VocabularyRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<VocabularyRecord>(
    alias,
    net.paploo.goi.persistence.db.goi.vocabulary.Vocabulary.VOCABULARY,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>vocabulary.vocabulary</code>
         */
        val VOCABULARY_: Vocabulary = Vocabulary()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<VocabularyRecord> = VocabularyRecord::class.java

    /**
     * The column <code>vocabulary.vocabulary.id</code>.
     */
    val ID: TableField<VocabularyRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>vocabulary.vocabulary.word_class_code</code>.
     */
    val WORD_CLASS_CODE: TableField<VocabularyRecord, String?> = createField(DSL.name("word_class_code"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>vocabulary.vocabulary.conjugation_kind_code</code>.
     */
    val CONJUGATION_KIND_CODE: TableField<VocabularyRecord, String?> = createField(DSL.name("conjugation_kind_code"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>vocabulary.vocabulary.jlpt_level</code>.
     */
    val JLPT_LEVEL: TableField<VocabularyRecord, Int?> = createField(DSL.name("jlpt_level"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>vocabulary.vocabulary.row_num</code>.
     */
    val ROW_NUM: TableField<VocabularyRecord, Int?> = createField(DSL.name("row_num"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>vocabulary.vocabulary.date_added</code>.
     */
    val DATE_ADDED: TableField<VocabularyRecord, LocalDate?> = createField(DSL.name("date_added"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column <code>vocabulary.vocabulary.tags</code>.
     */
    val TAGS: TableField<VocabularyRecord, Array<String?>?> = createField(DSL.name("tags"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field(DSL.raw("'{}'::character varying[]"), SQLDataType.VARCHAR)).array(), this, "")

    private constructor(alias: Name, aliased: Table<VocabularyRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<VocabularyRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vocabulary.vocabulary</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vocabulary.vocabulary</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vocabulary.vocabulary</code> table reference
     */
    constructor(): this(DSL.name("vocabulary"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, VocabularyRecord>): this(Internal.createPathAlias(child, key), child, key, VOCABULARY_, null)
    override fun getSchema(): Schema? = if (aliased()) null else net.paploo.goi.persistence.db.goi.vocabulary.Vocabulary.VOCABULARY
    override fun getIndexes(): List<Index> = listOf(VOCABULARY_TAGS_IDX)
    override fun getPrimaryKey(): UniqueKey<VocabularyRecord> = VOCABULARY_PKEY
    override fun getReferences(): List<ForeignKey<VocabularyRecord, *>> = listOf(VOCABULARY__VOCABULARY_WORD_CLASS_CODE_FKEY, VOCABULARY__VOCABULARY_CONJUGATION_KIND_CODE_FKEY)

    private lateinit var _wordClass: WordClass
    private lateinit var _conjugationKind: ConjugationKind

    /**
     * Get the implicit join path to the <code>vocabulary.word_class</code>
     * table.
     */
    fun wordClass(): WordClass {
        if (!this::_wordClass.isInitialized)
            _wordClass = WordClass(this, VOCABULARY__VOCABULARY_WORD_CLASS_CODE_FKEY)

        return _wordClass;
    }

    val wordClass: WordClass
        get(): WordClass = wordClass()

    /**
     * Get the implicit join path to the
     * <code>vocabulary.conjugation_kind</code> table.
     */
    fun conjugationKind(): ConjugationKind {
        if (!this::_conjugationKind.isInitialized)
            _conjugationKind = ConjugationKind(this, VOCABULARY__VOCABULARY_CONJUGATION_KIND_CODE_FKEY)

        return _conjugationKind;
    }

    val conjugationKind: ConjugationKind
        get(): ConjugationKind = conjugationKind()
    override fun `as`(alias: String): Vocabulary = Vocabulary(DSL.name(alias), this)
    override fun `as`(alias: Name): Vocabulary = Vocabulary(alias, this)
    override fun `as`(alias: Table<*>): Vocabulary = Vocabulary(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Vocabulary = Vocabulary(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Vocabulary = Vocabulary(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Vocabulary = Vocabulary(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row7<UUID?, String?, String?, Int?, Int?, LocalDate?, Array<String?>?> = super.fieldsRow() as Row7<UUID?, String?, String?, Int?, Int?, LocalDate?, Array<String?>?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, String?, String?, Int?, Int?, LocalDate?, Array<String?>?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, String?, String?, Int?, Int?, LocalDate?, Array<String?>?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}