package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.conjugator.describeInflector

class GodanVerbInflectorTest : DescribeSpec({

    //u
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

    //ku
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "聞く") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "聞く"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "聞いた"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "聞いて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "聞かない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "聞かなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "聞かなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "聞きます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "聞きました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "聞きません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "聞きませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "聞ける"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "聞けない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "聞けます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "聞けません"
    }

    //gu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "急ぐ") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "急ぐ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "急いだ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "急いで"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "急がない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "急がなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "急がなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "急ぎます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "急ぎました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "急ぎません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "急ぎませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "急げる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "急げない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "急げます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "急げません"
    }

    //su
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

    //tsu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "待つ") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "待つ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "待った"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "待って"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "待たない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "待たなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "待たなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "待ちます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "待ちました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "待ちません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "待ちませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "待てる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "待てない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "待てます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "待てません"
    }

    //nu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "死ぬ") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "死ぬ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "死んだ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "死んで"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "死なない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "死ななかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "死ななくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "死にます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "死にました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "死にません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "死にませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "死ねる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "死ねない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "死ねます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "死ねません"
    }

    //bu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "飛ぶ") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "飛ぶ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "飛んだ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "飛んで"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "飛ばない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "飛ばなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "飛ばなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "飛びます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "飛びました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "飛びません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "飛びませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "飛べる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "飛べない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "飛べます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "飛べません"
    }

    //mu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "薬を飲む") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "薬を飲む"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "薬を飲んだ"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "薬を飲んで"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "薬を飲まない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "薬を飲まなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "薬を飲まなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "薬を飲みます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "薬を飲みました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "薬を飲みません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "薬を飲みませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "薬を飲める"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "薬を飲めない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "薬を飲めます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "薬を飲めません"
    }

    //ru
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "頑張る") {
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "頑張る"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "頑張った"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "頑張って"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Present) shouldInflectAs "頑張らない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Past) shouldInflectAs "頑張らなかった"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Te) shouldInflectAs "頑張らなくて"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "頑張ります"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "頑張りました"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Present) shouldInflectAs "頑張りません"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Past) shouldInflectAs "頑張りませんでした"

        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "頑張れる"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Plain, Conjugation.Inflection.Form.Potential) shouldInflectAs "頑張れない"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Positive, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "頑張れます"
        Conjugation.Inflection(Conjugation.Inflection.Charge.Negative, Conjugation.Inflection.Politeness.Polite, Conjugation.Inflection.Form.Potential) shouldInflectAs "頑張れません"
    }


})