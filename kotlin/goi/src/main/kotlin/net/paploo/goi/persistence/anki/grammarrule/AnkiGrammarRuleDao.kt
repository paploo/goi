package net.paploo.goi.persistence.anki.grammarrule

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.persistence.common.BulkWriteDao
import java.nio.file.Path

class AnkiGrammarRuleDao(
    val filePath: Path
) : BulkWriteDao<GrammarRule> {

    override suspend fun writeAll(values: List<GrammarRule>): Result<Unit> =
        values.map { GrammarRuleDomainToRecordTransform().invoke(it) }.sequenceToResult().flatMap { records ->
            GrammarRuleWriter().invoke(filePath, records)
        }
}
