package net.paploo.goi.persistence.jsonfile.grammarrule

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import net.paploo.goi.persistence.common.LocalDateSerializer
import net.paploo.goi.persistence.common.UUIDSerializer
import java.time.LocalDate
import java.util.*

@Serializable(GrammarRuleJsonElementSerializer::class)
sealed class GrammarRuleJsonElement

@Serializable
data class CommentDto(
    @SerialName("COMMENT")
    val comment: String
) : GrammarRuleJsonElement()

@Serializable
data class TodoDto(
    @SerialName("TODO")
    val todo: String
) : GrammarRuleJsonElement()

@Serializable
data class GrammarRuleDto(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val meaning: String,
    @SerialName("how_to_use")
    val howToUse: List<String>,
    val examples: List<ExampleDto>,
    @SerialName("jlpt_level")
    val jlptLevel: Int?,
    @Serializable(LocalDateSerializer::class)
    @SerialName("date_added")
    val dateAdded: LocalDate,
    @SerialName("lesson_codes")
    val lessonCodes: List<String>,
    val tags: List<String>,
) : GrammarRuleJsonElement()

@Serializable
data class ExampleDto(
    @Serializable(UUIDSerializer::class)
    val id: UUID,
    val meaning: String,
    val text: TextDto,
    @SerialName("lesson_codes")
    val lessonCodes: List<String>,
    val tags: List<String>,
)

@Serializable
data class TextDto(
    val spelling: String,
)

object GrammarRuleJsonElementSerializer : JsonContentPolymorphicSerializer<GrammarRuleJsonElement>(GrammarRuleJsonElement::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<GrammarRuleJsonElement> = when {
        "COMMENT" in element.jsonObject -> CommentDto.serializer()
        "TODO" in element.jsonObject -> TodoDto.serializer()
        else -> GrammarRuleDto.serializer()
    }
}

