package net.paploo.goi.persistence.database.grammarrule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.persistence.common.SqlRecordGroupsWriter
import net.paploo.goi.persistence.common.executeAsyncResult
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.ExampleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.records.RuleReferenceRecord
import net.paploo.goi.persistence.db.goi.grammar.tables.references.EXAMPLE
import net.paploo.goi.persistence.db.goi.grammar.tables.references.EXAMPLE_REFERENCE
import net.paploo.goi.persistence.db.goi.grammar.tables.references.RULE
import net.paploo.goi.persistence.db.goi.grammar.tables.references.RULE_REFERENCE
import org.jooq.DSLContext

internal class GrammarRuleWriter : SqlRecordGroupsWriter<GrammarRuleRecordGroup>() {

    override suspend fun clearDatabases(dataSource: ServiceDataSource): Result<Int> =
        //Thanks to cascading, this deletes everything.
        dataSource.dsl().deleteFrom(RULE).where("true").executeAsyncResult().flatMap { deleteCount ->
            logger.warn("Purged $deleteCount Vocabulary Records")
            Result.runCatching {
                dataSource.dsl().execute("VACUUM")
            }.map { deleteCount }
        }

    override suspend fun writeRecord(dataSource: ServiceDataSource, recordGroup: GrammarRuleRecordGroup): Result<Int> =
        withContext(Dispatchers.IO) {
            dataSource.dsl().let { dsl ->
                listOf<List<Result<Int>>>(
                    listOf(grammarRuleSql(dsl, recordGroup.ruleRecord)),
                    recordGroup.exampleRecords.map { exampleSql(dsl, it) },
                    recordGroup.ruleReferences.map { ruleReferencesSql(dsl, it) },
                    recordGroup.exampleReferences.map { exampleReferencesSql(dsl, it) },
                ).flatten().sequenceToResult().map { it.sum() }
            }
        }

    private suspend fun grammarRuleSql(dsl: DSLContext, record: RuleRecord): Result<Int> =
        dsl.insertInto(RULE).set(record).executeAsyncResult()

    private suspend fun exampleSql(dsl: DSLContext, record: ExampleRecord): Result<Int> =
        dsl.insertInto(EXAMPLE).set(record).executeAsyncResult()

    private suspend fun ruleReferencesSql(dsl: DSLContext, record: RuleReferenceRecord): Result<Int> =
        dsl.insertInto(RULE_REFERENCE).set(record).executeAsyncResult()

    private suspend fun exampleReferencesSql(dsl: DSLContext, record: ExampleReferenceRecord): Result<Int> =
        dsl.insertInto(EXAMPLE_REFERENCE).set(record).executeAsyncResult()

}
