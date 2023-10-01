package net.paploo.goi.common.extensions

//TODO: Write Tests
fun String.snakeCase(): String =
    replace("(([a-z])([A-Z]))|(([A-Z])([A-Z][a-z]))".toRegex(), "$2$5_$3$6").replace('-', '_').lowercase()

fun String.kebabCase(): String =
    snakeCase().replace('_', '-')

//TODO: Write Tests
fun String.constCase(): String =
    snakeCase().uppercase()

//TODO: Write Tests
fun String.camelCase(): String =
    snakeCase().replace("(\\w)(_)(\\w)".toRegex()) { it.groupValues[1].lowercase() + it.groupValues[3].uppercase() }

//TODO: Write Tests
fun String.pascalCase(): String =
    camelCase().replaceFirstChar { it.uppercase() }
