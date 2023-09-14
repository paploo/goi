package net.paploo.goi.domain.tools.conjugator

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerScope
import io.kotest.core.spec.style.scopes.DescribeSpecRootScope
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form

class InflectorTest : DescribeSpec({

    describeInflector("Ichidan Verb Inflector", IchidanVerbInflector.default, "食べる") {
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

    describe("Godan Verb Inflector") {

        describe("foo") {

        }

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
        }

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
        }


    }

})

fun DescribeSpecRootScope.describeInflector(
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

suspend fun DescribeSpecContainerScope.describeInflector(
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

    val expectationMap: Map<Conjugation.Inflection, String> get() = DescribeInflectorScope().apply(expectationsScope).build()

    operator fun invoke(scope: DescribeSpecRootScope) {
        scope.apply {
            describe("$inflectorName on dictionary value $dictionaryValue") {
                makeTests(this)
            }
        }
    }

    suspend operator fun invoke(scope: DescribeSpecContainerScope) {
        scope.apply {
            describe("$inflectorName on dictionary value $dictionaryValue") {
                makeTests(this)
            }
        }
    }

    private suspend fun makeTests(scope: DescribeSpecContainerScope) {
        scope.apply {
            for ((inflection, expectedValue) in expectationMap) {
                it("should inflect ${inflection.charge.name.lowercase()} ${inflection.politeness.name.lowercase()} ${inflection.form.name.lowercase()}") {
                    val inflected = inflector(inflection)?.invoke(dictionaryValue)
                    inflected shouldBe Result.success(expectedValue)
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