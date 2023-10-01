package net.paploo.goi.persistence.common

import kotlinx.coroutines.Dispatchers
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.parallelMap
import net.paploo.goi.common.extensions.sequenceToResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Writes the given records into a SQL store.
 *
 * This takes the strategy of iterating through the records using batches run in parallel.
 *
 * Optionally, this can remove all records from target tables before it starts, replacing with new records.
 */
abstract class SqlRecordGroupsWriter<T : Any>(
    val batchParallelization: Int = 10
) : suspend (ServiceDataSource, List<T>) -> Result<Unit> {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun invoke(dataSource: ServiceDataSource, recordGroups: List<T>): Result<Unit> =
        //Right now the database is only used as an output source, so we do a full clean reload. No need to spend the work to transactionally update.
        clearDatabases(dataSource).flatMap {
            logger.info("Starting write of ${recordGroups.size} entries")
            writeRecords(dataSource, recordGroups)
        }.also { result ->
            result.onSuccess {
                logger.info("Write of ${recordGroups.size} entries resulted in writing $it rows")
            }.onFailure {
                logger.error("Write of ${recordGroups.size} entries resulted in error $it", it)
            }
        }.map { Unit }

    protected suspend fun writeRecords(dataSource: ServiceDataSource, recordGroups: List<T>): Result<Int> =
        Result.success(
            max(ceil(recordGroups.size.toDouble() / batchParallelization.toDouble()).roundToInt(), 1)
        ).mapCatching { batchSize ->
            logger.debug("Splitting ${recordGroups.size} record groups into batch sizes of $batchSize")
            recordGroups.chunked(batchSize)
        }.flatMap { batches ->
            logger.debug("Batches: ${batches.size}")
            batches.parallelMap(Dispatchers.IO) { batch ->
                batch.map { writeRecord(dataSource, it) }.sequenceToResult().map { it.sum() }
            }.sequenceToResult().map { it.sum() }
        }

    /**
     * Clean-out any records (e.g. truncate tables).
     *
     * @return Usually the number of root records deleted, or zero if there is a no-op.
     */
    protected abstract suspend fun clearDatabases(dataSource: ServiceDataSource) : Result<Int>

    /**
     * Writes a record group to the data store.
     *
     * @return the number of rows created for the record (across all tables)
     */
    protected abstract suspend fun writeRecord(dataSource: ServiceDataSource, recordGroup: T): Result<Int>

}