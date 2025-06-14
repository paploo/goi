@file:Suppress("warnings")
/*
 * This file is generated by jOOQ.
 */
package net.paploo.goi.persistence.db.goi.kanji.indexes


import net.paploo.goi.persistence.db.goi.kanji.tables.KanjiCharacter

import org.jooq.Index
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// INDEX definitions
// -------------------------------------------------------------------------

val KANJI_CHARACTER_CHARACTER_IDX: Index = Internal.createIndex(DSL.name("kanji_character_character_idx"), KanjiCharacter.KANJI_CHARACTER, arrayOf(KanjiCharacter.KANJI_CHARACTER.CHARACTER), false)
val KANJI_CHARACTER_KUN_READINGS_IDX: Index = Internal.createIndex(DSL.name("kanji_character_kun_readings_idx"), KanjiCharacter.KANJI_CHARACTER, arrayOf(KanjiCharacter.KANJI_CHARACTER.KUN_READINGS), false)
val KANJI_CHARACTER_MEANINGS_IDX: Index = Internal.createIndex(DSL.name("kanji_character_meanings_idx"), KanjiCharacter.KANJI_CHARACTER, arrayOf(KanjiCharacter.KANJI_CHARACTER.MEANINGS), false)
val KANJI_CHARACTER_ON_READINGS_IDX: Index = Internal.createIndex(DSL.name("kanji_character_on_readings_idx"), KanjiCharacter.KANJI_CHARACTER, arrayOf(KanjiCharacter.KANJI_CHARACTER.ON_READINGS), false)
