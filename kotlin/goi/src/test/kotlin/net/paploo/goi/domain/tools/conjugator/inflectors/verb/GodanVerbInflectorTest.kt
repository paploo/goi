package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.conjugator.describeInflector

class GodanVerbInflectorTest : DescribeSpec({

    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "会う") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "会う"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "会った"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "会って"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "会わない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "会わなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "会わなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "会います"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "会いました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "会いません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "会いませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "会える"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "会えない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "会えます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "会えません"
    }

    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "話す") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "話す"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "話した"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "話して"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "話さない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "話さなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "話さなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "話します"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "話しました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "話しません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "話しませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "話せる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "話せない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "話せます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "話せません"
    }

})