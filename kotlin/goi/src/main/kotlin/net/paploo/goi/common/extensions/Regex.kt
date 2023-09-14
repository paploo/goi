package net.paploo.goi.common.extensions

/**
 * Allows convenience use of `in` keyword in things like `when` pattern matching.
 */
operator fun Regex.contains(string: String): Boolean =
    string.matches(regex = this)