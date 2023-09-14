package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.conjugator.describeInflector

class IchidanVerbInflectorTest : DescribeSpec({

    describeInflector("Ichidan Verb Inflector", IchidanVerbInflector.default, "食べる") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "食べる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "食べた"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "食べて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "食べない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "食べなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "食べなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "食べます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "食べました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "食べません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "食べませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "食べられる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "食べられない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "食べられます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "食べられません"
    }

})