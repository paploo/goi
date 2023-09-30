package net.paploo.goi.persistence.jsonfile.grammarrule

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.inputStream

internal class JsonFileGrammarRuleReader : suspend (Path) -> Result<List<GrammarRuleDto>> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private val jsonReader = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun invoke(path: Path): Result<List<GrammarRuleDto>> =
        Result.runCatching {
            path.inputStream()
        }.mapCatching {
            it.use { usedStream ->
                jsonReader.decodeFromStream<List<GrammarRuleJsonElement>>(usedStream)
            }
        }.map {
            it.filterIsInstance<GrammarRuleDto>().mapIndexed { index, dto ->
                dto.copy(rowNum = index + 1)
            }
        }

}