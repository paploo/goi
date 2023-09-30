package net.paploo.goi.persistence.jsonfile.grammarrule

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.persistence.common.BulkDao
import java.nio.file.Path

class JsonFileGrammarRuleDao(
    val filePath: Path
) : BulkDao<GrammarRule> {

    override suspend fun getAll(): Result<List<GrammarRule>> =
        JsonFileGrammarRuleReader().invoke(filePath).flatMap { records ->
            records.map { GrammarRuleRecordToDomainTransform().invoke(it) }.sequenceToResult()
        }

    override suspend fun writeAll(values: List<GrammarRule>): Result<Unit> {
        TODO("Not yet implemented")
    }
}
