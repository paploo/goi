package net.paploo.goi.domain.tools.validation

import net.paploo.goi.common.extensions.flatMap
import org.slf4j.Logger

/**
 * Interface for managing a report of grouped messages with attached severity levels.
 */
sealed interface ValidationReport {

    fun count(level: Level): Int

    fun maxLevel(): Level

    enum class Level(val icon: String) {
        Debug("🐛"),
        Info("ℹ️"),
        Warn("⚠️"),
        Error("🛑"),
    }

    data class Group(
        val name: String,
        val messages: List<ValidationReport>,
    ) : ValidationReport {

        override fun count(level: Level): Int =
            messages.sumOf { it.count(level) }

        override fun maxLevel(): Level =
            messages.maxOf { it.maxLevel() }

    }

    data class Message(
        val level: Level,
        val message: String,
    ) : ValidationReport {

        override fun count(level: Level): Int =
            if (level == this.level) 1 else 0

        override fun maxLevel(): Level = level

        companion object {
            fun debug(message: String): Message = Message(Level.Debug, message)
            fun info(message: String): Message = Message(Level.Info, message)
            fun warn(message: String): Message = Message(Level.Warn, message)
            fun error(message: String): Message = Message(Level.Error, message)
        }

    }

}

fun ValidationReport.counts(): Map<ValidationReport.Level, Int> =
    ValidationReport.Level.entries.associateBy(
        keySelector = { it },
        valueTransform = { count(it) }
    )

fun ValidationReport.formattedCounts(): String =
    ValidationReport.Level.entries.sortedDescending().joinToString("  ") { level ->
        "${count(level)} ${level.icon}"
    }

fun ValidationReport.formatted(indent: Int = 0): String = when (this) {
    is ValidationReport.Message ->
        "    ".repeat(indent) + "${level.icon} $message"

    is ValidationReport.Group ->
        listOf(
            listOf("    ".repeat(indent) + "${maxLevel().icon} $name"),
            messages.map { it.formatted(indent + 1) },
            if (indent == 0) listOf(formattedCounts()) else emptyList()
        ).flatten().joinToString("\n")
}

fun Logger.log(validationReport: ValidationReport) {
    //Start on new line because first line is the time stamps and stuff.
    val formatted = "Validation Report\n" + validationReport.formatted()
    when(validationReport.maxLevel()) {
        ValidationReport.Level.Error -> error(formatted)
        ValidationReport.Level.Warn -> warn(formatted)
        ValidationReport.Level.Info -> info(formatted)
        ValidationReport.Level.Debug -> debug(formatted)
    }
}
