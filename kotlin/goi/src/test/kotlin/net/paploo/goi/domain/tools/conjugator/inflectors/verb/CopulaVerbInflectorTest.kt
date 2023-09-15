package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class CopulaVerbInflectorTest : DescribeSpec({

    describeInflector("Copula Verb Inflector", CopulaVerbInflector.default, "です") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "だ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "だった"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "で"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "じゃない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "じゃなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te).shouldInflectAsFailure<IllegalArgumentException>()

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "です"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "でした"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "じゃないです"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "じゃないかったです"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential).shouldInflectAsFailure<IllegalArgumentException>()
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential).shouldInflectAsFailure<IllegalArgumentException>()
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential).shouldInflectAsFailure<IllegalArgumentException>()
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential).shouldInflectAsFailure<IllegalArgumentException>()
    }

})