package net.paploo.goi.persistence.common

import java.lang.IllegalArgumentException
import kotlin.enums.EnumEntries

/**
 * Common interface for the CSVRecord pattern in use.
 *
 * # Overview
 *
 * This pattern is a bit of a compromise between a number of factors, including:
 * - Type safety on hand-creation of records (ensuring a compile error if a new field is added but the mapper doesn't supply it)
 * - Easy interop with external libraries, which can be inconsistent on how they handle fetched rows versus returned ones.
 * - The desire to dynamically build/use the record using the defined fields.
 *
 * This last point has a couple implementation details around the use of Apache Common CSV:
 * - It parses CSV into CSVRecord objects, but you can NOT hand-create them, so one cannot simply wrap these records.
 *
 * # Implementation
 *
 * Implementors usually:
 * 1. Create a data class whose fields are the columns (in order) and values are `String?`.
 * 2. Define within it the `enum class Field` class, again in column order.
 * 3. Create a `constructor(csvRecord: CSVRecord)` to convert from Apache Commons.
 * 4. Use `abstract class BaseVocabularyCsvRecord` to imbue standard method implementations.
 *
 * The upside is that the boilerplate becomes pretty trivial if you use IntelliJ with the "String Manipulation" plugin installed:
 * 1. Copy the column headers from a sample file.
 * 2. Use find/replace to split it into multiple rows.
 * 3. Use block selection (opt-click-drag) to copy and paste the fields and then bulk edit the column boilerplate
 * 4. Use string manipulation (opt-shift-m) to change the raw column names to camelCase and PascalCase as needed.
 * With those tools, it doesn't take much more effort to setup 100 rows than to setup 1.
 */
interface CsvRecord<R, F>
        where F : Enum<F>, F : CsvRecord.Field<R, F>, R : CsvRecord<R, F> {

    /**
     * Interface for the defined list of fields.
     *
     * ⚠️ This enum is expected to be in the same order as the columns in the file (though their indices may not align).
     */
    interface Field<R, F> where F : Enum<F>, F : Field<R, F>, R : CsvRecord<R, F> {
        val headerName: String
        val getter: (R) -> String?
    }

    /**
     * Alternative to directly calling an accessor in a way that is more uniform with field based access.
     *
     * @see [getNotnull] for some idea of why this pattern exists for reading.
     */
    operator fun get(field: F): String?

    /**
     * Gets the value, returning a failed result if it is null.
     *
     * This method is preferred over custom `getOrElse` routines, primarily because the error indicates useful
     * information like which field was unexpectedly null.
     */
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