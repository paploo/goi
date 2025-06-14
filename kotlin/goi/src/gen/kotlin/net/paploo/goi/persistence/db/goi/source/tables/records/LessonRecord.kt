/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.source.tables.records


import net.paploo.goi.persistence.db.goi.source.tables.Lesson

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("warnings")
open class LessonRecord private constructor() : UpdatableRecordImpl<LessonRecord>(Lesson.LESSON) {

    open var code: String
        set(value): Unit = set(0, value)
        get(): String = get(0) as String

    open var lessonCode: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var sourceCode: String
        set(value): Unit = set(2, value)
        get(): String = get(2) as String

    open var label: String
        set(value): Unit = set(3, value)
        get(): String = get(3) as String

    open var sectionNumber: Int?
        set(value): Unit = set(4, value)
        get(): Int? = get(4) as Int?

    open var subsectionNumber: Int?
        set(value): Unit = set(5, value)
        get(): Int? = get(5) as Int?

    open var url: String?
        set(value): Unit = set(6, value)
        get(): String? = get(6) as String?

    open var description: String?
        set(value): Unit = set(7, value)
        get(): String? = get(7) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<String?> = super.key() as Record1<String?>

    /**
     * Create a detached, initialised LessonRecord
     */
    constructor(code: String, lessonCode: String, sourceCode: String, label: String, sectionNumber: Int?, subsectionNumber: Int?, url: String?, description: String?): this() {
        this.code = code
        this.lessonCode = lessonCode
        this.sourceCode = sourceCode
        this.label = label
        this.sectionNumber = sectionNumber
        this.subsectionNumber = subsectionNumber
        this.url = url
        this.description = description
        resetTouchedOnNotNull()
    }
}
