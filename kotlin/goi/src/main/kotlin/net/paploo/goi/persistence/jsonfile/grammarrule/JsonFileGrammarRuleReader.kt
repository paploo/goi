package net.paploo.goi.persistence.jsonfile.grammarrule

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.inputStream
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries

internal class JsonFileGrammarRuleReader : suspend (Path) -> Result<List<GrammarRuleDto>> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private val jsonReader = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun invoke(path: Path): Result<List<GrammarRuleDto>> =
        Result.runCatching { parse(path) }.mapCatching {
            processRows(it)
        }

    @OptIn(ExperimentalSerializationApi::class)
    private fun parse(path: Path): List<GrammarRuleJsonElement> =
        if (path.isDirectory()) {
            logger.info("Parsing directory: {}", path)
            path
                .listDirectoryEntries()
                .sortedBy { it.fileName.toString() }
                .flatMap { parse(it) }
        } else if (path.isRegularFile() && path.toString().endsWith(".json", ignoreCase = true)) {
            logger.info("Parsing file: {}", path)
            path.inputStream().use { inputStream ->
                jsonReader.decodeFromStream<List<GrammarRuleJsonElement>>(inputStream)
            }
        } else {
            logger.warn("Skipping non-json file: {}", path)
            emptyList()
        }

    private fun processRows(allRows: List<GrammarRuleJsonElement>): List<GrammarRuleDto> =
            allRows.filterIsInstance<GrammarRuleDto>().mapIndexed { index, dto ->
                dto.copy(rowNum = index + 1)
            }
}