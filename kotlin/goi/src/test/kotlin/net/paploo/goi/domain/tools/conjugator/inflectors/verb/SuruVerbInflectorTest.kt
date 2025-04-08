package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class SuruVerbInflectorTest : DescribeSpec({

    describeInflector("Suru Verb Inflector", SuruVerbInflector.default, "する") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "する"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "した"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "して"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "しない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "しなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "しなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "します"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "しました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "しません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "しませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "できる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "できない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "できます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "できません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "しよう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "しましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "したら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "しなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "しましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "すれば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "しなければ"
    }

    describeInflector("Suru Verb Inflector", SuruVerbInflector.default, "洗濯する") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "洗濯する"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "洗濯した"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "洗濯して"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "洗濯しない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "洗濯しなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "洗濯しなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "洗濯します"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "洗濯しました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "洗濯しません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "洗濯しませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "洗濯できる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "洗濯できない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "洗濯できます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "洗濯できません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "洗濯しよう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "洗濯しましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "洗濯したら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "洗濯しなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "洗濯しましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "洗濯すれば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "洗濯しなければ"
    }

})