package net.paploo.goi.persistence.common

import org.apache.commons.csv.CSVFormat

object CsvFormats {

    val default: CSVFormat = CSVFormat.Builder
        .create(CSVFormat.RFC4180)
        .setHeader() // Gets the header; only works on parse.
        .setNullString("") // Empty strings between delimeters are null records.
        .setCommentMarker('#') //Enables comments in a way that works with Anki output.
        .setIgnoreEmptyLines(true) //Ignore empty lines
        .setTrim(true) //Trim leading/trailing whitespace. This is normally what we want.
        .setTrailingDelimiter(false) //
        .setRecordSeparator('\n') //This sets the line endings to UNIX ones, breaking RFC4180 but matching the Ruby reference impl.
        .build()

    val googleSheet: CSVFormat = CSVFormat.Builder
        .create(default)
        .setAllowMissingColumnNames(true)
        .build()

    val anki: CSVFormat = CSVFormat.Builder
        .create(default)
        .setSkipHeaderRecord(true)
        .build()

}