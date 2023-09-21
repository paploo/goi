package net.paploo.goi.persistence.common

import org.apache.commons.csv.CSVFormat

object CsvFormats {

    val default: CSVFormat = CSVFormat.Builder
        .create(CSVFormat.RFC4180)
        .setHeader()
        .setNullString("")
        .setCommentMarker('#')
        .setIgnoreEmptyLines(true)
        .setTrim(true)
        .build()

}