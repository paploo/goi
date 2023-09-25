package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector

class CopulaVerbInflectorTest : DescribeSpec({

    describeInflector("Copula Verb Inflector", CopulaVerbInflector.default, "だ") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "だ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "だった"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "で"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "じゃない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "じゃなかった"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "です"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "でした"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Te) shouldInflectAs "でありまして"

        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "じゃないです"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "じゃないかったです"
    }

})

//to live; to reside; to inhabit,住む,すむ,,住む,VERB,GODAN_VERB,5,"{DUO_02_05,DUO_U08,GENKI3_L07}",,dd191fac-e29b-5262-b2a6-f9f2875a4a3f,40,2022-04-24,,住む,住んだ,住んで,住まない,住まなかった,住まなくて,住みます,住みました,,住みません,住みませんでした,,住める,住めない,住めます,住めません