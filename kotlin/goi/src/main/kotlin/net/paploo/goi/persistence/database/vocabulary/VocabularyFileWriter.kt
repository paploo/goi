package net.paploo.goi.persistence.database.vocabulary

import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.*
import net.paploo.goi.persistence.db.goi.vocabulary.tables.references.*
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.conf.StatementType
import org.jooq.impl.DSL
import java.io.FileWriter
import java.nio.file.Path

internal class VocabularyFileWriter : suspend (Path, List<VocabularyRecordGroup>) -> Result<Unit> {

    override suspend fun invoke(filePath: Path, recordGroups: List<VocabularyRecordGroup>): Result<Unit> =
        Result.runCatching {
            FileWriter(filePath.toFile())
        }.map {
            it.use { writer ->

                recordGroups.forEach { recordGroup ->
                    recordGroupSql(recordGroup).map { lines ->
                        lines.forEach { line ->
                            writer.write(line)
                            writer.write("\n")
                        }
                    }
                }

            }
        }


    private val dsl: DSLContext = DSL.using(
        SQLDialect.POSTGRES, Settings().apply {
            statementType = StatementType.STATIC_STATEMENT //Makes output static sql and not prepared statements.
        }
    )

    private fun recordGroupSql(recordGroup: VocabularyRecordGroup): Result<List<String>> = Result.runCatching {
        listOf<List<String>>(
            listOf(headerComment(recordGroup)),
            listOf(vocabularySql(recordGroup.vocabularyRecord)),
            recordGroup.definitions.map { definitionSql(it) },
            recordGroup.spellings.map { spellingSql(it) },
            recordGroup.conjugationSet?.let { listOf(conjugationSetSql(it)) } ?: emptyList(),
            listOf(linkagesSql(recordGroup.linkagesRecord)),
            recordGroup.references.map { referenceSql(it) },
            recordGroup.conjugations?.map { conjugationSql(it) } ?: emptyList(),
        ).flatten()
    }

    private fun headerComment(recordGroup: VocabularyRecordGroup): String =
        "-- ${recordGroup.spellings.firstOrNull()?.value} | ${recordGroup.definitions.firstOrNull()?.value} --;"

    private fun vocabularySql(record: VocabularyRecord): String =
        dsl.insertInto(VOCABULARY_).set(record).sql.finish()

    private fun definitionSql(record: DefinitionRecord): String =
        dsl.insertInto(DEFINITION).set(record).sql.finish()

    private fun spellingSql(record: SpellingRecord): String =
        dsl.insertInto(SPELLING).set(record).sql.finish()

    private fun conjugationSetSql(record: ConjugationSetRecord): String =
        dsl.insertInto(CONJUGATION_SET).set(record).sql.finish()

    private fun linkagesSql(record: LinkagesRecord): String =
        dsl.insertInto(LINKAGES).set(record).sql.finish()

    private fun referenceSql(record: ReferenceRecord): String =
        dsl.insertInto(REFERENCE).set(record).sql.finish()

    private fun conjugationSql(record: ConjugationRecord): String =
        dsl.insertInto(CONJUGATION).set(record).sql.finish()

    private fun String.finish(): String = this + ";"

}