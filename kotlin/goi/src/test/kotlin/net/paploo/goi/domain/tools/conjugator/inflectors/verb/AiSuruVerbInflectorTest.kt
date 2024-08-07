package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class AiSuruVerbInflectorTest : DescribeSpec({

    describeInflector("愛する Verb Inflector", AiSuruVerbInflector.default, "愛する") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "愛する"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "愛した"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "愛して"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "愛さない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "愛しなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "愛しなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "愛します"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "愛しました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "愛しません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "愛しませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "愛できる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "愛できない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "愛できます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "愛できません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "愛しよう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "愛したら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "愛しなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "愛しましたら"
    }

})










