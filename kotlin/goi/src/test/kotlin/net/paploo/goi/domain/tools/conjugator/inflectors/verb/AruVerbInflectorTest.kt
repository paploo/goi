package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class AruVerbInflectorTest : DescribeSpec({

    describeInflector("Aru Verb Inflector", AruVerbInflector.default, "咳がある") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "咳がある"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "咳があった"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "咳があって"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "咳がない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "咳がなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "咳がなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "咳があります"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "咳がありました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "咳がありません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "咳がありませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "咳がありえる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "咳がありえない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "咳がありえます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "咳がありえません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "咳があろう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "咳がありましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "咳があったら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "咳がなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "咳がありましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "咳があれば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "咳がなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "咳があられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "咳があられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "咳があられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "咳があられません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "咳があらせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "咳があらせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "咳があらせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "咳があらせません"
    }

})
