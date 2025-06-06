package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.result.shouldBeFailure
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.describeInflector
import java.lang.IllegalArgumentException

class GodanVerbInflectorTest : DescribeSpec({

    //u
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "会う") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "会う"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "会った"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "会って"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "会わない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "会わなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "会わなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "会います"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "会いました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "会いません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "会いませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "会える"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "会えない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "会えます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "会えません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "会おう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "会いましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "会ったら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "会わなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "会いましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "会えば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "会わなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "会われる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "会われない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "会われます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "会われません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "会わせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "会わせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "会わせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "会わせません"
    }

    //ku
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "聞く") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "聞く"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "聞いた"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "聞いて"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "聞かない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "聞かなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "聞かなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "聞きます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "聞きました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "聞きません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "聞きませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "聞ける"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "聞けない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "聞けます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "聞けません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "聞こう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "聞きましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "聞いたら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "聞かなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "聞きましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "聞けば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "聞かなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "聞かれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "聞かれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "聞かれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "聞かれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "聞かせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "聞かせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "聞かせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "聞かせません"
    }

    //gu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "急ぐ") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "急ぐ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "急いだ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "急いで"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "急がない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "急がなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "急がなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "急ぎます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "急ぎました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "急ぎません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "急ぎませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "急げる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "急げない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "急げます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "急げません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "急ごう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "急ぎましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "急いだら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "急がなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "急ぎましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "急げば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "急がなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "急がれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "急がれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "急がれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "急がれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "急がせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "急がせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "急がせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "急がせません"
    }

    //su
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "話す") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "話す"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "話した"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "話して"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "話さない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "話さなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "話さなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "話します"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "話しました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "話しません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "話しませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "話せる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "話せない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "話せます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "話せません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "話そう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "話しましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "話したら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "話さなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "話しましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "話せば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "話さなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "話される"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "話されない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "話されます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "話されません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "話させる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "話させない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "話させます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "話させません"
    }

    //tsu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "待つ") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "待つ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "待った"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "待って"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "待たない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "待たなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "待たなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "待ちます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "待ちました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "待ちません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "待ちませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "待てる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "待てない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "待てます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "待てません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "待とう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "待ちましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "待ったら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "待たなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "待ちましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "待てば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "待たなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "待たれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "待たれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "待たれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "待たれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "待たせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "待たせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "待たせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "待たせません"
    }

    //nu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "死ぬ") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "死ぬ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "死んだ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "死んで"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "死なない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "死ななかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "死ななくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "死にます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "死にました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "死にません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "死にませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "死ねる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "死ねない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "死ねます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "死ねません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "死のう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "死にましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "死んだら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "死ななかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "死にましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "死ねば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "死ななければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "死なれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "死なれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "死なれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "死なれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "死なせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "死なせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "死なせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "死なせません"
    }

    //bu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "飛ぶ") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "飛ぶ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "飛んだ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "飛んで"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "飛ばない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "飛ばなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "飛ばなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "飛びます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "飛びました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "飛びません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "飛びませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "飛べる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "飛べない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "飛べます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "飛べません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "飛ぼう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "飛びましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "飛んだら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "飛ばなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "飛びましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "飛べば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "飛ばなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "飛ばれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "飛ばれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "飛ばれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "飛ばれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "飛ばせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "飛ばせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "飛ばせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "飛ばせません"
    }

    //mu
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "薬を飲む") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "薬を飲む"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "薬を飲んだ"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "薬を飲んで"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "薬を飲まない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "薬を飲まなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "薬を飲まなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "薬を飲みます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "薬を飲みました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "薬を飲みません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "薬を飲みませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "薬を飲める"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "薬を飲めない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "薬を飲めます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "薬を飲めません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "薬を飲もう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "薬を飲みましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "薬を飲んだら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "薬を飲まなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "薬を飲みましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "薬を飲めば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "薬を飲まなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "薬を飲まれる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "薬を飲まれない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "薬を飲まれます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "薬を飲まれません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "薬を飲ませる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "薬を飲ませない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "薬を飲ませます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "薬を飲ませません"
    }

    //ru
    describeInflector("Godan Verb Inflector", GodanVerbInflector.default, "頑張る") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "頑張る"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "頑張った"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "頑張って"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "頑張らない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "頑張らなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "頑張らなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "頑張ります"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "頑張りました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "頑張りません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "頑張りませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "頑張れる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "頑張れない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "頑張れます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "頑張れません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Volitional) shouldInflectAs "頑張ろう"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Volitional) shouldInflectAs "頑張りましょう"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "頑張ったら"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalTara) shouldInflectAs "頑張らなかったら"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.ConditionalTara) shouldInflectAs "頑張りましたら"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "頑張れば"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.ConditionalEba) shouldInflectAs "頑張らなければ"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Passive) shouldInflectAs "頑張られる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Passive) shouldInflectAs "頑張られない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Passive) shouldInflectAs "頑張られます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Passive) shouldInflectAs "頑張られません"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Causative) shouldInflectAs "頑張らせる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Causative) shouldInflectAs "頑張らせない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Causative) shouldInflectAs "頑張らせます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Causative) shouldInflectAs "頑張らせません"
    }

    describe("Illegal Values") {

        for(inflection in VerbInflector.supportedInflections) {
            val inflectionName = "${inflection.charge.name.lowercase()} ${inflection.politeness.name.lowercase()} ${inflection.form.name.lowercase()}"

            it("should fail for $inflectionName with an error when the verb doesn't end in ふ") {
                val result = GodanVerbInflector.default(inflection)?.invoke("ぎふ")
                result?.shouldBeFailure<IllegalArgumentException>()
            }
        }

    }


})