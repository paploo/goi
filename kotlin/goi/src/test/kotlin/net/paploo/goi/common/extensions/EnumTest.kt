package net.paploo.goi.common.extensions

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldInclude
import io.kotest.matchers.types.instanceOf

class EnumTest : DescribeSpec({

    describe("enumResultValueOf") {

        it("should be a success if the value is in the enum") {
            enumResultValueOf<Counting>("One") shouldBe Result.success(Counting.One)
        }

        it("should be a failure of NoSuchElementException if it is invalid") {
            enumResultValueOf<Counting>("Cat").shouldBeFailure { th ->
                th shouldBe instanceOf(NoSuchElementException::class)
            }
        }

        it("should have human readable diagnosis info in the message") {
            enumResultValueOf<Counting>("Cat").shouldBeFailure { th ->
                th.message shouldInclude "Cat"
                Counting.entries.forEach {counting ->
                    th.message shouldInclude counting.name
                }
            }
        }

    }

}) {

    enum class Counting(val jp: String) {
        One("一"),
        Two("二"),
        Three("三"),
    }

}