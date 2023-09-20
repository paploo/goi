package net.paploo.goi.common.extensions

//TODO: Write Tests
fun String.toSnakeCase(): String =
    replace("(([a-z])([A-Z]))|(([A-Z])([A-Z][a-z]))".toRegex(), "$2$5_$3$6").replace('-', '_').lowercase()

//TODO: Write Tests
fun String.toConstCase(): String =
    toSnakeCase().uppercase()

//TODO: Write Tests
fun String.toCamelCase(): String =
    toSnakeCase().replace("(\\w)(_)(\\w)".toRegex()) { it.groupValues[1].lowercase() + it.groupValues[3].uppercase() }

//TODO: Write Tests
fun String.toPascalCase(): String =
    toCamelCase().replaceFirstChar { it.uppercase() }