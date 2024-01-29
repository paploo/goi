package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.result.shouldBeFailure
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector
import java.lang.IllegalArgumentException

class IchidanVerbInflectorTest : DescribeSpec({

    describeInflector("Ichidan Verb Inflector", IchidanVerbInflector.default, "食べる") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "食べる"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "食べた"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "食べて"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "食べない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "食べなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "食べなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "食べます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "食べました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "食べません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "食べませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "食べられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "食べられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "食べられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "食べられません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "食べよう"
    }

    describe("Illegal Value") {

        for(inflection in VerbInflector.supportedInflections) {
            val inflectionName = "${inflection.charge.name.lowercase()} ${inflection.politeness.name.lowercase()} ${inflection.form.name.lowercase()}"

            it("should fail for $inflectionName with an error when the verb doesn't end in る") {
                val result = IchidanVerbInflector.default(inflection)?.invoke("書く")
                result?.shouldBeFailure<IllegalArgumentException>()
            }
        }

    }

})