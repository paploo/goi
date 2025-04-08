package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.*
import net.paploo.goi.domain.tools.conjugator.describeInflector

class AruSpecialClassVerbInflectorTest : DescribeSpec({

    //https://www.tanoshiijapanese.com/dictionary/conjugation_details.cfm?entry_id=84
    describeInflector("Aru Special Class Verb Inflector", AruSpecialClassVerbInflector.default, "いらっしゃる") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "いらっしゃる"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "いらっしゃった"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "いらっしゃって"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "いらっしゃらない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "いらっしゃらなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "いらっしゃらなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "いらっしゃいます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "いらっしゃいました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "いらっしゃいません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "いらっしゃいませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "いらっしゃれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "いらっしゃれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "いらっしゃれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "いらっしゃれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "いらっしゃろう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "いらっしゃいましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "いらっしゃったら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "いらっしゃらなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "いらっしゃいましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "いらっしゃれば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "いらっしゃらなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "いらっしゃられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "いらっしゃられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "いらっしゃられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "いらっしゃられません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "いらっしゃらせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "いらっしゃらせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "いらっしゃらせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "いらっしゃらせません"
    }

    //https://www.tanoshiijapanese.com/dictionary/conjugation_details.cfm?entry_id=25762
    describeInflector("Aru Special Class Verb Inflector", AruSpecialClassVerbInflector.default, "ござる") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "ござる"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "ござった"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "ござって"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "ござらない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "ござらなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "ござらなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "ございます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "ございました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "ございません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "ございませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "ござれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "ござれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "ござれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "ござれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "ござろう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "ございましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "ござったら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "ござらなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "ございましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "ござれば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "ござらなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "ござられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "ござられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "ござられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "ござられません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "ござらせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "ござらせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "ござらせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "ござらせません"
    }

})