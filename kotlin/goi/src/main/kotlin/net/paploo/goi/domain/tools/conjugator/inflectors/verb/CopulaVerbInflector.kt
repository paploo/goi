package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.Inflector
import net.paploo.goi.domain.tools.conjugator.Rewriter
import net.paploo.goi.domain.tools.conjugator.plus

class CopulaVerbInflector : Inflector {

    override fun invoke(inflection: Conjugation.Inflection): Rewriter? = when (inflection) {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) -> positivePlainPresent
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) -> positivePlainPast
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) -> positivePlainTe

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) -> negativePlainPresent
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) -> negativePlainPast

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) -> positivePolitePresent
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) -> positivePolitePast
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Te) -> positivePoliteTe

        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) -> negativePolitePresent
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) -> negativePolitePast

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) -> positivePlainTaraConditional
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) -> negativePlainTaraConditional
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) -> positivePoliteTaraConditional

        //TODO
//        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) -> positivePlainEbaConditional
//        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) -> negativePlainEbaConditional

        else -> null
    }

    private val validEndingsRegex = "(だ)$".toRegex()

    val positivePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "だ")
    val positivePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "だった")
    val positivePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "で")

    val negativePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "じゃない")
    val negativePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "じゃなかった")

    val positivePolitePresent: Rewriter = Rewriter.replace(validEndingsRegex, "です")
    val positivePolitePast: Rewriter = Rewriter.replace(validEndingsRegex, "でした")
    val positivePoliteTe: Rewriter = Rewriter.replace(validEndingsRegex, "でありまして")

    val negativePolitePresent: Rewriter = Rewriter.replace(validEndingsRegex, "じゃないです")
    val negativePolitePast: Rewriter = Rewriter.replace(validEndingsRegex, "じゃないかったです")

    // Fill in 〜たら rules from https://www.tofugu.com/japanese-grammar/conditional-form-tara/

    val positivePlainTaraConditional: Rewriter =
        positivePlainPast + Rewriter { Result.success(it + "ら") }

    val negativePlainTaraConditional: Rewriter =
        negativePlainPast + Rewriter { Result.success(it + "ら") }

    val positivePoliteTaraConditional: Rewriter =
        positivePolitePast + Rewriter { Result.success(it + "ら") }

    companion object {
        val default: CopulaVerbInflector = CopulaVerbInflector()


        val supportedInflections: Set<Conjugation.Inflection> = setOf(
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present),
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past),
            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te),

            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past),

            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Te),

            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present),
            Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past),

            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara),
            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara),
            Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara),

            //TODO
//            Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba),
//            Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba),
        )

    }
}
