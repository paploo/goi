package net.paploo.goi.persistence.common

import java.lang.IllegalArgumentException
import kotlin.enums.EnumEntries

interface CsvRecord<R, F>
        where F : Enum<F>, F : CsvRecord.Field<R, F>, R : CsvRecord<R, F> {

    interface Field<R, F> where F : Enum<F>, F : Field<R, F>, R : CsvRecord<R, F> {
        val headerName: String
        val getter: (R) -> String?
    }

    operator fun get(field: F): String?

    fun getNotNull(field: F): Result<String>

    fun toRow(): List<String?>

    fun toMap(): Map<F, String?>

}

abstract class BaseVocabularyCsvRecord<R, F>(
) : CsvRecord<R, F>
        where F : Enum<F>, F : CsvRecord.Field<R, F>, R : CsvRecord<R, F> {

    protected abstract val record: R

    protected abstract val fields: EnumEntries<F>

    override fun get(field: F): String? =
        field.getter(record)

    override fun getNotNull(field: F): Result<String> =
        get(field)?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("$field contained a null value but should've been non-null"))

    override fun toRow(): List<String?> =
        fields.map { get(it) }

    override fun toMap(): Map<F, String?> =
        fields.associateWith { get(it) }

}