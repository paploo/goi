package net.paploo.goi.pipeline.grammarrule.exporter

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.persistence.anki.grammarrule.AnkiGrammarRuleDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Exporter
import java.nio.file.Path

class AnkiGrammarRuleExporter(
    val config: Config
) : Exporter<List<GrammarRule>> {

    data class Config(
        val filePath: Path
    )

    override suspend fun invoke(rules: List<GrammarRule>, context: Context): Result<Unit> =
        AnkiGrammarRuleDao(config.filePath).writeAll(rules)

}