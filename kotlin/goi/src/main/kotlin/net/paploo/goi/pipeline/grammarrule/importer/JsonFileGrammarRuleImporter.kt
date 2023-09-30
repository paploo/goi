package net.paploo.goi.pipeline.grammarrule.importer

import net.paploo.goi.domain.data.grammar.GrammarRule
import net.paploo.goi.persistence.jsonfile.grammarrule.JsonFileGrammarRuleDao
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Importer
import java.nio.file.Path

class JsonFileGrammarRuleImporter(
    val config: Config
) : Importer<List<GrammarRule>>  {

    data class Config(
        val filePath: Path
    )

    override suspend fun invoke(context: Context): Result<List<GrammarRule>> =
        context.timerLog.markAround("Import from JSON file") {
            JsonFileGrammarRuleDao(config.filePath).getAll()
        }

}