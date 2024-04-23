package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class NaAdjectiveInflectorTest : DescribeSpec({

    // A normal な adjective
    describeInflector("な Adjective Inflector", NaAdjectiveInflector.default, "好き") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "好きだ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "好きだった"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "好きで"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "好きじゃない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "好きじゃなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "好きではなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "todo"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "todo"
    }

})
