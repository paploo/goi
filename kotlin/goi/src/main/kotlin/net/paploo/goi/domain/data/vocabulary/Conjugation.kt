package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.interfaces.Valued
import kotlin.enums.EnumEntries

data class Conjugation(
    val inflection: Inflection,
    override val value: String
) : Valued<String> {

    data class Inflection(
        val charge: Charge,
        val politeness: Politeness,
        val form: Form,
    ) {

        enum class Charge {
            Positive,
            Negative,
        }

        enum class Politeness {
            Plain,
            Polite,
        }

        enum class Form {
            Present,
            Past,
            Te,
            ConditionalEba,
            ConditionalTara,
            Potential,
            Passive,
            Causative,
            Imperative,
        }

        companion object {

            val entries: List<Inflection> =
                Charge.entries.flatMap { charge ->
                    Politeness.entries.flatMap { politeness ->
                        Form.entries.map { form ->
                            Inflection(charge = charge, politeness = politeness, form = form)
                        }
                    }
                }

        }

    }

}