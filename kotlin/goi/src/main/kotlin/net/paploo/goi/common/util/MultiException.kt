package net.paploo.goi.common.util

import org.slf4j.Logger
import java.lang.IllegalArgumentException

class MultiException(
    val reason: String,
    val failures: Map<String, Throwable>
) : IllegalArgumentException (
    "Validation failed for target with ${failures.size} failures:"
), Map<String, Throwable> by failures

private fun makeMessage(reason: String, failures: Map<String, Throwable>): String =
    "Validation failed for with ${failures.size} failures with reason = $reason\n" +
            failures.entries.joinToString() { (label, error) ->
                "\t${label}: $error\n"
            }

private fun makeShortMessage(reason: String, failures: Map<String, Throwable>): String =
    "Validation failed for with ${failures.size} failures with reason = $reason"