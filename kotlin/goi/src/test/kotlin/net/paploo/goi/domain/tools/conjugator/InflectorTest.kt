package net.paploo.goi.domain.tools.conjugator

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerScope
import io.kotest.core.spec.style.scopes.DescribeSpecRootScope
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.GodanVerbInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.IchidanVerbInflector

class InflectorTest : DescribeSpec({
    //TODO: Make tests
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

    val expectationMap: Map<Conjugation.Inflection, Result<String>> get() = DescribeInflectorScope().apply(expectationsScope).build()

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
                val inflectionName = "${inflection.charge.name.lowercase()} ${inflection.politeness.name.lowercase()} ${inflection.form.name.lowercase()}"
                it("should inflect $inflectionName}") {
                    val inflected = inflector(inflection)?.invoke(dictionaryValue)
                    inflected shouldBe expectedValue
                }
            }
        }
    }

    class DescribeInflectorScope {
        private var expectations: MutableMap<Conjugation.Inflection, Result<String>> = mutableMapOf()

        infix fun Conjugation.Inflection.shouldInflectAs(expectedValue: String) {
            expectations[this] = Result.success(expectedValue)
        }

        internal fun build(): Map<Conjugation.Inflection, Result<String>> = expectations.toMap()
    }

}