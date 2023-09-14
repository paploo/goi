package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form

interface Inflector : (Conjugation.Inflection) -> Rewriter? {
    override fun invoke(inflection: Conjugation.Inflection): Rewriter?
}

/**
 * Internal method; this is useful when building inflectors from other inflectors, where there is
 * knowledge that a [Rewriter] exists but no good way to tell the compiler (without a lot of boilerplate).
 */
internal fun Inflector.getOrThrow(inflection: Conjugation.Inflection): Rewriter =
    invoke(inflection) ?: throw IllegalArgumentException("No Rewriter for inflection $inflection")


/**
 * Gives some syntactic sugar niceness for making objects that are Inflectors but that delegate to inline created inflector instances.
 */
@Deprecated("Don't use this modeling style")
open class DelegatedInflector(delegate: Inflector) : Inflector by delegate

/**
 * Type-safe interface for all (supported) adjective inflections.
 */
interface AdjectiveInflector : Inflector {
    val positivePlainPresent: Rewriter
    val positivePlainPast: Rewriter
    val positivePlainTe: Rewriter

    val negativePlainPresent: Rewriter
    val negativePlainPast: Rewriter
    val negativePlainTe: Rewriter
}

/**
 * Type-safe interface for all (supported) verb inflections.
 */
interface VerbInflector : Inflector {
    val positivePlainPresent: Rewriter
    val positivePlainPast: Rewriter
    val positivePlainTe: Rewriter

    val negativePlainPresent: Rewriter
    val negativePlainPast: Rewriter
    val negativePlainTe: Rewriter

    val positivePolitePresent: Rewriter
    val positivePolitePast: Rewriter

    val negativePolitePresent: Rewriter
    val negativePolitePast: Rewriter

    val positivePlainPotential: Rewriter
    val negativePlainPotential: Rewriter
    val positivePolitePotential: Rewriter
    val negativePolitePotential: Rewriter
}

abstract class StandardAdjectiveInflector : AdjectiveInflector {

    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) -> positivePlainTe

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) -> negativePlainPast
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) -> negativePlainTe

        else -> null
    }

}

abstract class StandardVerbInflector : VerbInflector {

    override val negativePlainPotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.getOrThrow(
            Conjugation.Inflection(
                Charge.Negative,
                Politeness.Plain,
                Form.Present
            )
        )
    }

    override val positivePolitePotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.getOrThrow(
            Conjugation.Inflection(
                Charge.Positive,
                Politeness.Polite,
                Form.Present
            )
        )
    }

    override val negativePolitePotential: Rewriter by lazy {
        positivePlainPotential + IchidanVerbInflector.default.getOrThrow(
            Conjugation.Inflection(
                Charge.Negative,
                Politeness.Polite,
                Form.Present
            )
        )
    }

    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) -> positivePlainTe

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) -> negativePlainPast
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) -> negativePlainTe

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) -> positivePolitePresent
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) -> positivePolitePast

        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) -> negativePolitePresent
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) -> negativePolitePast

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) -> positivePlainPotential
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) -> negativePlainPotential
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) -> positivePolitePotential
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) -> negativePolitePotential

        else -> null
    }

}

/**
 * Type-safe mapping of the [Rewriter] instances expected for an adjective conjugation.
 */
@Deprecated("Replace with an abstract class and implement inline; this gives better control over evaluation and better composition later")
private data class AdjectiveStandardInflector(
    override val positivePlainPresent: Rewriter,
    override val positivePlainPast: Rewriter,
    override val positivePlainTe: Rewriter,

    override val negativePlainPresent: Rewriter,
    override val negativePlainPast: Rewriter,
    override val negativePlainTe: Rewriter,
) : StandardAdjectiveInflector()

/**
 * Type-safe mapping of the [Rewriter] instances expected for most adjectives.
 *
 * In some cases, a default may exist if none is provided.
 *
 * Note: This isn't all possible ones, just the ones that I've needed thus far.
 */
@Deprecated("Replace with an abstract class and implement inline; this gives better control over evaluation and better composition later")
private data class VerbStandardInflector(
    override val positivePlainPresent: Rewriter,
    override val positivePlainPast: Rewriter,
    override val positivePlainTe: Rewriter,

    override val negativePlainPresent: Rewriter,
    override val negativePlainPast: Rewriter,
    override val negativePlainTe: Rewriter,

    override val positivePolitePresent: Rewriter,
    override val positivePolitePast: Rewriter,

    override val negativePolitePresent: Rewriter,
    override val negativePolitePast: Rewriter,

    override val positivePlainPotential: Rewriter,
    val overrideNegativePlainPotential: Rewriter? = null,
    val overridePositivePolitePotential: Rewriter? = null,
    val overrideNegativePolitePotential: Rewriter? = null,
) : StandardVerbInflector() {

    //Provide some defaults for some easily derivable values, but allow them to be overriden. Note that we have to be lazy
    //because our dependency has to fully initialize first!

    override val negativePlainPotential: Rewriter by lazy {
        overrideNegativePlainPotential ?: super.negativePlainPotential
    }

    override val positivePolitePotential: Rewriter by lazy {
        overridePositivePolitePotential ?: super.positivePolitePotential
    }

    override val negativePolitePotential: Rewriter by lazy {
        overrideNegativePolitePotential ?: super.negativePolitePotential
    }

}

class IchidanVerbInflector : StandardVerbInflector() {
    private val validEndingsRegex = "(る)$".toRegex()

    override val positivePlainPresent =
        Rewriter.replacement(validEndingsRegex, "る") //By using a matcher, we validate it matches the pattern
    override val positivePlainPast = Rewriter.replacement(validEndingsRegex, "た")
    override val positivePlainTe = Rewriter.replacement(validEndingsRegex, "て")

    override val negativePlainPresent = Rewriter.replacement(validEndingsRegex, "ない")
    override val negativePlainPast = Rewriter.replacement(validEndingsRegex, "なかった")
    override val negativePlainTe = Rewriter.replacement(validEndingsRegex, "なくて")

    override val positivePolitePresent = Rewriter.replacement(validEndingsRegex, "ます")
    override val positivePolitePast = Rewriter.replacement(validEndingsRegex, "ました")

    override val negativePolitePresent = Rewriter.replacement(validEndingsRegex, "ません")
    override val negativePolitePast = Rewriter.replacement(validEndingsRegex, "ませんでした")

    override val positivePlainPotential = Rewriter.replacement(validEndingsRegex, "られる")

    companion object {
        val default: IchidanVerbInflector = IchidanVerbInflector()
    }
}

class GodanVerbInflector : StandardVerbInflector() {
    private val validEndingsRegex: Regex = """([${validDictionaryEndings.joinToString("")}])$""".toRegex()

    override val positivePlainPresent = Rewriter.replacement(validEndingsRegex, "$1")

    override val positivePlainPast = GodanRewriteRule(
        uRepl = "った",
        kuRepl = "いた",
        guRepl = "いだ",
        suRepl = "した",
        tsuRepl = "った",
        nuRepl = "んだ",
        buRepl = "んだ",
        muRepl = "んだ",
        ruRepl = "った",
    )

    override val positivePlainTe = Rewriter.identity

    override val negativePlainPresent = Rewriter.identity

    override val negativePlainPast = Rewriter.identity

    override val negativePlainTe = Rewriter.identity

    override val positivePolitePresent = Rewriter.identity

    override val positivePolitePast = Rewriter.identity

    override val negativePolitePresent = Rewriter.identity

    override val negativePolitePast = Rewriter.identity

    override val positivePlainPotential = Rewriter.identity

    companion object {
        val default: GodanVerbInflector by lazy { GodanVerbInflector() }

        val validDictionaryEndings: List<String> = listOf("う", "く", "ぐ", "す", "つ", "ぬ", "ぶ", "む", "る")
    }

}