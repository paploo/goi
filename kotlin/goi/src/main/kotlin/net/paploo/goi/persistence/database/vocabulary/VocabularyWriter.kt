package net.paploo.goi.persistence.database.vocabulary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.withContext
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.parallelMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.common.util.TimerLog
import net.paploo.goi.persistence.common.ServiceDataSource
import net.paploo.goi.persistence.common.executeAsyncResult
import net.paploo.goi.persistence.db.goi.vocabulary.tables.records.*
import net.paploo.goi.persistence.db.goi.vocabulary.tables.references.*
import org.jooq.DSLContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

internal class VocabularyWriter : suspend (ServiceDataSource, List<VocabularyRecordGroup>) -> Result<Unit> {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun invoke(dataSource: ServiceDataSource, recordGroups: List<VocabularyRecordGroup>): Result<Unit> =
        //Right now the database is only used as an output source, so we do a full clean reload. No need to spend the work to transactionally update.
        clearVocabularyDatabases(dataSource).flatMap {
            logger.info("Starting write of ${recordGroups.size} vocabulary entries")
            writeRecords(dataSource, recordGroups).also {

            }
        }.also { result ->
            result.onSuccess {
                logger.info("Write of ${recordGroups.size} vocabulary entries resulted in writing $it rows")
            }.onFailure {
                logger.error("Write of ${recordGroups.size} vocabulary entries resulted in error $it", it)
            }
        }.map { Unit }

    suspend fun clearVocabularyDatabases(dataSource: ServiceDataSource): Result<Int> =
        //Thanks to cascading, this deletes everything.
        dataSource.dsl().deleteFrom(VOCABULARY_).where("true").executeAsyncResult().flatMap { deleteCount ->
            logger.warn("Purged $deleteCount Vocabulary Records")
            Result.runCatching {
                dataSource.dsl().execute("VACUUM")
            }.map { deleteCount }
        }

    private suspend fun writeRecords(dataSource: ServiceDataSource, recordGroups: List<VocabularyRecordGroup>): Result<Int> =
        Result.success(
            max(ceil(recordGroups.size / 10.0).roundToInt(), 1)
        ).mapCatching { batchSize ->
            logger.debug("Splitting ${recordGroups.size} record groups into batch sizes of $batchSize")
            recordGroups.chunked(batchSize)
        }.flatMap { batches ->
            logger.debug("Batches: ${batches.size}")
            batches.parallelMap(Dispatchers.IO) { batch ->
                batch.map { writeRecord(dataSource, it) }.sequenceToResult().map { it.sum() }
            }.sequenceToResult().map { it.sum() }
        }
//        recordGroups.map { writeRecord(dataSource, it) }.sequenceToResult().map { it.size }

    private suspend fun writeRecord(dataSource: ServiceDataSource, recordGroup: VocabularyRecordGroup): Result<Int> =
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
        dsl.dsl().insertInto(VOCABULARY_).set(record).executeAsyncResult()

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