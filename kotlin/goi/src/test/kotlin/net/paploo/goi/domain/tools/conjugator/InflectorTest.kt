package net.paploo.goi.domain.tools.conjugator

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.scopes.DescribeSpecContainerScope
import io.kotest.core.spec.style.scopes.DescribeSpecRootScope
import io.kotest.core.test.TestScope
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import io.kotest.matchers.types.instanceOf
import io.kotest.matchers.types.shouldBeInstanceOf
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.GodanVerbInflector
import net.paploo.goi.domain.tools.conjugator.inflectors.verb.IchidanVerbInflector
import kotlin.reflect.KClass

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

    val expectationMap: Map<Conjugation.Inflection, Expectation> get() = DescribeInflectorScope().apply(expectationsScope).build()

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
            for ((inflection, expectation) in expectationMap) {
                val inflectionName = "${inflection.charge.name.lowercase()} ${inflection.politeness.name.lowercase()} ${inflection.form.name.lowercase()}"
                it("should inflect $inflectionName") {
                    val inflected = inflector(inflection)?.invoke(dictionaryValue)
                    expectation(this, inflected)
                }
            }
        }
    }

    sealed interface Expectation {

        suspend operator fun invoke(testScope: TestScope, inflectionResult: Result<String>?)

        data class ExpectedSuccess(val value: String) : Expectation {
            override suspend fun invoke(testScope: TestScope, inflectionResult: Result<String>?) {
                testScope.apply {
                    inflectionResult shouldBe Result.success(value)
                }
            }
        }
        data class ExpectedFailure(val failureClass: KClass<*>) : Expectation {
            override suspend fun invoke(testScope: TestScope, inflectionResult: Result<String>?) {
                testScope.apply {
                    inflectionResult.shouldNotBeNull().shouldBeFailure().let {th ->
                        th shouldBe instanceOf(failureClass)
                    }
                }
            }
        }
    }

    class DescribeInflectorScope {
        private var expectations: MutableMap<Conjugation.Inflection, Expectation> = mutableMapOf()

        fun add(inflection: Conjugation.Inflection, expectation: Expectation) {
            expectations[inflection] = expectation
        }

        infix fun Conjugation.Inflection.shouldInflectAs(expectedValue: String) {
            add(this, Expectation.ExpectedSuccess(expectedValue))
        }

        inline fun <reified T: Throwable> Conjugation.Inflection.shouldInflectAsFailure() {
            add(this, Expectation.ExpectedFailure(T::class))
        }

        internal fun build(): Map<Conjugation.Inflection, Expectation> = expectations.toMap()
    }

}