package net.paploo.goi.persistence.common.extensions

import org.apache.commons.csv.CSVRecord

fun CSVRecord.getOrNull(name: String): String? =
    if (isMapped(name)) get(name) else null

fun CSVRecord.getOrDefault(name: String, default: String): String? =
    if (isMapped(name)) get(name) else default

fun CSVRecord.getOrElse(name: String, onElse: () -> String) =
    if (isMapped(name)) get(name) else onElse()