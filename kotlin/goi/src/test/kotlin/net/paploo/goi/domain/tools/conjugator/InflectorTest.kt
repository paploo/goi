package net.paploo.goi.domain.tools.conjugator

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form

class InflectorTest : DescribeSpec({

    describeInflector("Ichidan Verb Inflector", IchidanVerbInflector, "食べる") {
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present) shouldInflectAs "食べる"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past) shouldInflectAs "食べた"
        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te) shouldInflectAs "食べて"

        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present) shouldInflectAs "食べない"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past) shouldInflectAs "食べなかった"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te) shouldInflectAs "食べなくて"

        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present) shouldInflectAs "食べます"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past) shouldInflectAs "食べました"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present) shouldInflectAs "食べません"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past) shouldInflectAs "食べませんでした"

        Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential) shouldInflectAs "食べられる"
        Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential) shouldInflectAs "食べられない"
        Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential) shouldInflectAs "食べられます"
        Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential) shouldInflectAs "食べられません"
    }

})

fun DescribeSpec.describeInflector(
    inflectorName: String,
    inflector: Inflector,
    dictionaryValue: String,
    expectationsScope: DescribeInflector.DescribeInflectorScope.() -> Unit
) = DescribeInflector(
    inflectorName = inflectorName,
    inflector = inflector,
    dictionaryValue = dictionaryValue,
    expectationsScope = expectationsScope
).invoke(this)

class DescribeInflector(
    private val inflectorName: String,
    private val inflector: Inflector,
    private val dictionaryValue: String,
    private val expectationsScope: DescribeInflectorScope.() -> Unit
) {

    operator fun invoke(describeSpec: DescribeSpec) {
        val expectationMap = DescribeInflectorScope().apply(expectationsScope).build()

        describeSpec.apply {
            describe("$inflectorName on dictionary value $dictionaryValue") {

                for ((inflection, expectedValue) in expectationMap) {
                    it("should inflect ${inflection.charge.name.lowercase()} ${inflection.politeness.name.lowercase()}　${inflection.form.name.lowercase()}") {
                        val inflected = inflector(inflection)?.invoke(dictionaryValue)
                        inflected shouldBe Result.success(expectedValue)
                    }
                }
            }
        }
    }

    class DescribeInflectorScope {
        private var expectations: MutableMap<Conjugation.Inflection, String> = mutableMapOf()

        infix fun Conjugation.Inflection.shouldInflectAs(expectedValue: String) {
            expectations[this] = expectedValue
        }

        internal fun build(): Map<Conjugation.Inflection, String> = expectations.toMap()
    }

}