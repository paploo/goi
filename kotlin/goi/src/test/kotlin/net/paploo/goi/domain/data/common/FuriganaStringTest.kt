package net.paploo.goi.domain.data.common

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import java.lang.IllegalArgumentException

class FuriganaStringTest : DescribeSpec({

    describe("text template") {

        describe("with value") {

            val template = FuriganaTemplate.Text("猫")

            it("should instantiate") {
                val furiganaString = FuriganaString(template)
                furiganaString.furiganaTemplate shouldBe template
            }

            it("should give the correct value") {
                val furiganaString = FuriganaString(template)
                furiganaString.value shouldBe template.preferred
            }

            it("should give the correct preferred") {
                val furiganaString = FuriganaString(template)
                furiganaString.preferredSpelling.value shouldBe template.preferred
            }

            it("should give the correct phonetic") {
                val furiganaString = FuriganaString(template)
                furiganaString.phoneticSpelling.shouldBeNull()
            }

        }

        describe("empty") {

            val template = FuriganaTemplate.Text("")

            it("should give the correct value") {
                val furiganaString = FuriganaString(template)
                furiganaString.value shouldBe ""
            }

            it("should give the correct preferred") {
                val furiganaString = FuriganaString(template)
                shouldThrow<IllegalArgumentException> { furiganaString.preferredSpelling }
            }

            it("should give the correct phonetic") {
                val furiganaString = FuriganaString(template)
                shouldThrow<IllegalArgumentException> { furiganaString.phoneticSpelling }
            }

        }

    }

    describe("ruby text template") {

        describe("with value") {

            val template = FuriganaTemplate.Ruby("猫", "ねこ")

            it("should instantiate") {
                val furiganaString = FuriganaString(template)
                furiganaString.furiganaTemplate shouldBe template
            }

            it("should give the correct value") {
                val furiganaString = FuriganaString(template)
                furiganaString.value shouldBe template.preferred
            }

            it("should give the correct preferred") {
                val furiganaString = FuriganaString(template)
                furiganaString.preferredSpelling.value shouldBe template.preferred
            }

            it("should give the correct phonetic") {
                val furiganaString = FuriganaString(template)
                furiganaString.phoneticSpelling?.value shouldBe template.phonetic
            }

        }

        describe("empty") {

            val template = FuriganaTemplate.Ruby("", "")

            it("should give the correct value") {
                val furiganaString = FuriganaString(template)
                furiganaString.value shouldBe ""
            }

            it("should give the correct preferred") {
                val furiganaString = FuriganaString(template)
                shouldThrow<IllegalArgumentException> { furiganaString.preferredSpelling }
            }

            it("should give the correct phonetic") {
                val furiganaString = FuriganaString(template)
                shouldThrow<IllegalArgumentException> { furiganaString.phoneticSpelling }
            }

        }

    }

    describe("curly braces template") {

        describe("with value") {

            val template = FuriganaTemplate.CurlyBraces("{猫|ねこ}")

            it("should instantiate") {
                val furiganaString = FuriganaString(template)
                furiganaString.furiganaTemplate shouldBe template
            }

            it("should give the correct value") {
                val furiganaString = FuriganaString(template)
                furiganaString.value shouldBe "猫"
            }

            it("should give the correct preferred") {
                val furiganaString = FuriganaString(template)
                furiganaString.preferredSpelling.value shouldBe "猫"
            }

            it("should give the correct phonetic") {
                val furiganaString = FuriganaString(template)
                furiganaString.phoneticSpelling?.value shouldBe "ねこ"
            }

        }

        describe("empty") {

            val template = FuriganaTemplate.CurlyBraces("")

            it("should give the correct value") {
                val furiganaString = FuriganaString(template)
                furiganaString.value shouldBe ""
            }

            it("should give the correct preferred") {
                val furiganaString = FuriganaString(template)
                shouldThrow<IllegalArgumentException> { furiganaString.preferredSpelling }
            }

            it("should give the correct phonetic") {
                val furiganaString = FuriganaString(template)
                shouldThrow<IllegalArgumentException> { furiganaString.phoneticSpelling }
            }

        }

    }

})