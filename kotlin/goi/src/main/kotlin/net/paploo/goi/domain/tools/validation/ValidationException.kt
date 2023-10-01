package net.paploo.goi.domain.tools.validation

import net.paploo.goi.common.extensions.flatMap

sealed class ValidationException(
    message: String
) : RuntimeException(message)

data class ValidationReportException(
    val validationReport: ValidationReport
) : ValidationException("Validation failed (${validationReport.formattedCounts()}):\n${validationReport.formatted()}")

inline fun <T> T.validatedResult(block: (T) -> ValidationReport): Result<T> =
    runCatching {
        block(this)
    }.flatMap { validationReport ->
        if (validationReport.maxLevel() < ValidationReport.Level.Error) Result.success(this)
        else Result.failure(ValidationReportException(validationReport))
    }

fun <T> T.validatedResult(validationReport: ValidationReport): Result<T> =
    validatedResult { validationReport }