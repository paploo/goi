package net.paploo.goi.pipeline.grammarrule.exporter

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.persistence.database.grammarrule.SqlFileGrammarRuleDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter
import java.nio.file.Path

class SqlFileGrammarRuleExporter(
    val config: Config
) : Exporter<List<GrammarRule>> {

    data class Config(
        val filePath: Path
    )

    override suspend fun invoke(rules: List<GrammarRule>, context: Context): Result<Unit> =
        SqlFileGrammarRuleDao(config.filePath).writeAll(rules)

}