package net.paploo.goi.persistence.common

import kotlinx.coroutines.future.asDeferred
import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import org.jooq.ResultQuery

suspend fun <T, Rec: org.jooq.Record> ResultQuery<Rec>.fetchAsyncAndFlatMap(
    mapper: (Rec) -> Result<T>
): Result<List<T>> =
    Result.runCatching { fetchAsync().asDeferred().await() }.flatMap { jooqResult ->
        jooqResult.map(mapper).sequenceToResult()
    }