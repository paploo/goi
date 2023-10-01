package net.paploo.goi.persistence.common

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.domain.data.vocabulary.Vocabulary

interface BulkReadDao<out T: Any> {
    suspend fun getAll(): Result<List<T>>
}

interface BulkWriteDao<in T: Any> {
    suspend fun writeAll(values: List<T>): Result<Unit>
}

interface BulkDao<T : Any> : BulkReadDao<T>, BulkWriteDao<T>
