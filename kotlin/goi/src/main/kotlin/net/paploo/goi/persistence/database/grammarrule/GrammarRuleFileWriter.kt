package net.paploo.goi.persistence.database.grammarrule

import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.references.EXAMPLE
import net.paploo.goi.persistence.db.goi.grammar.tables.references.EXAMPLE_REFERENCE
import net.paploo.goi.persistence.db.goi.grammar.tables.references.RULE
import net.paploo.goi.persistence.db.goi.grammar.tables.references.RULE_REFERENCE
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.conf.StatementType
import org.jooq.impl.DSL
import java.io.FileWriter
import java.nio.file.Path

internal class GrammarRuleFileWriter : suspend (Path, List<GrammarRuleRecordGroup>) -> Result<Unit> {

    override suspend fun invoke(filePath: Path, recordGroups: List<GrammarRuleRecordGroup>): Result<Unit> =
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

    private fun recordGroupSql(recordGroup: GrammarRuleRecordGroup): Result<List<String>> = Result.runCatching {
        listOf<List<String>>(
            listOf(headerComment(recordGroup)),
            listOf(grammarRuleSql(recordGroup.ruleRecord)),
            recordGroup.exampleRecords.map { exampleSql(it) },
            recordGroup.ruleReferences.map { ruleReferencesSql(it) },
            recordGroup.exampleReferences.map { exampleReferencesSql(it) },
        ).flatten()
    }

    private fun headerComment(recordGroup: GrammarRuleRecordGroup): String =
        "-- ${recordGroup.ruleRecord.titlePreferredSpelling} | ${recordGroup.ruleRecord.meaning} --"

    private fun grammarRuleSql(record: RuleRecord): String =
        dsl.insertInto(RULE).set(record).sql.finish()

    private fun exampleSql(record: ExampleRecord): String =
        dsl.insertInto(EXAMPLE).set(record).sql.finish()

    private fun ruleReferencesSql(record: RuleReferenceRecord): String =
        dsl.insertInto(RULE_REFERENCE).set(record).sql.finish()

    private fun exampleReferencesSql(record: ExampleReferenceRecord): String =
        dsl.insertInto(EXAMPLE_REFERENCE).set(record).sql.finish()

    private fun String.finish(): String = this + ";"

}