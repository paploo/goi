package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class KuruVerbInflectorTest : DescribeSpec({

    describeInflector("Kuru Verb Inflector", KuruVerbInflector.default, "持ってくる") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "持ってくる"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "持ってきた"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "持ってきて"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "持ってこない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "持ってこなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "持ってこなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "持ってきます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "持ってきました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "持ってきません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "持ってきませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "持ってこられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "持ってこられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "持ってこられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "持ってこられません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "持ってこよう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "todo"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "todo"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "todo"
    }

    describeInflector("Kuru Verb Inflector", KuruVerbInflector.default, "持って来る") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "持ってくる"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "持ってきた"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "持ってきて"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "持ってこない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "持ってこなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "持ってこなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "持ってきます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "持ってきました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "持ってきません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "持ってきませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "持ってこられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "持ってこられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "持ってこられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "持ってこられません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "持ってこよう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "todo"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "todo"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "todo"
    }

})














