package net.paploo.goi.persistence.database.vocabulary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.persistence.common.SqlRecordGroupsWriter
import net.paploo.goi.persistence.common.executeAsyncResult
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.*
import net.paploo.goi.persistence.db.goi.vocabulary.tables.references.*
import org.jooq.DSLContext

/**
 * Vocabulary Writer that takes the tactic of issuing many separate insert statements (one per record), using multiple
 * threads for parallelization where it can do so.
 *
 * TODO: See if a batch writer would work faster. It takes more effort for parallelization since we have to be careful
 *       to write all dependencies first. This may or may not make it worth the effort.
 */
internal class VocabularyWriter : SqlRecordGroupsWriter<VocabularyRecordGroup>() {

    override suspend fun clearDatabases(dataSource: ServiceDataSource): Result<Int> =
        //Thanks to cascading, this deletes everything.
        dataSource.dsl().deleteFrom(VOCABULARY_).where("true").executeAsyncResult().flatMap { deleteCount ->
            logger.warn("Purged $deleteCount Vocabulary Records")
            Result.runCatching {
                dataSource.dsl().execute("VACUUM")
            }.map { deleteCount }
        }

    override suspend fun writeRecord(dataSource: ServiceDataSource, recordGroup: VocabularyRecordGroup): Result<Int> =
        withContext(Dispatchers.IO) {
            dataSource.dsl().let { dsl ->
                listOf<List<Result<Int>>>(
                    listOf(vocabularySql(dsl, recordGroup.vocabularyRecord)),
                    recordGroup.definitions.map { definitionSql(dsl, it) },
                    recordGroup.spellings.map { spellingSql(dsl, it) },
                    recordGroup.conjugationSet?.let { listOf(conjugationSetSql(dsl, it)) } ?: emptyList(),
                    listOf(linkagesSql(dsl, recordGroup.linkagesRecord)),
                    recordGroup.references.map { referenceSql(dsl, it) },
                    recordGroup.conjugations?.map { conjugationSql(dsl, it) } ?: emptyList(),
                ).flatten().sequenceToResult().map { it.sum() }
            }
        }

    private suspend fun vocabularySql(dsl: DSLContext, record: VocabularyRecord): Result<Int> =
        dsl.insertInto(VOCABULARY_).set(record).executeAsyncResult()

    private suspend fun definitionSql(dsl: DSLContext, record: DefinitionRecord): Result<Int> =
        dsl.insertInto(DEFINITION).set(record).executeAsyncResult()

    private suspend fun spellingSql(dsl: DSLContext, record: SpellingRecord): Result<Int> =
        dsl.insertInto(SPELLING).set(record).executeAsyncResult()

    private suspend fun conjugationSetSql(dsl: DSLContext, record: ConjugationSetRecord): Result<Int> =
        dsl.insertInto(CONJUGATION_SET).set(record).executeAsyncResult()

    private suspend fun linkagesSql(dsl: DSLContext, record: LinkagesRecord): Result<Int> =
        dsl.insertInto(LINKAGES).set(record).executeAsyncResult()

    private suspend fun referenceSql(dsl: DSLContext, record: ReferenceRecord): Result<Int> =
        dsl.insertInto(REFERENCE).set(record).executeAsyncResult()

    private suspend fun conjugationSql(dsl: DSLContext, record: ConjugationRecord): Result<Int> =
        dsl.insertInto(CONJUGATION).set(record).executeAsyncResult()

}