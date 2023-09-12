package net.paploo.goi.domain.data.vocabulary

import net.paploo.goi.common.Valued

data class Conjugation(
    val inflection: Inflection,
    override val value: String
) : Valued<String> {

    //TODO: Inflector should be able to differentiate between an unmatched rule and no rule defined.

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

    }

}