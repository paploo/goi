package net.paploo.goi.common.extensions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe

class ResultTest : DescribeSpec({

    describe("flatMap") {

        it("should map a success using the function") {
            val result: Result<Int> = Result.success("some value").flatMap { Result.success(it.length) }
            result shouldBe Result.success(10)
        }

        it("should allow transmuting a success to a failure") {
            val testException = RuntimeException("test exception")
            val result: Result<Int> = Result.success("some value").flatMap { Result.failure(testException) }
            result shouldBe Result.failure(testException)
        }

        it("should pass through the failure, but as the new type") {
            val testException = RuntimeException("test exception")
            var blockRan = false

            val result: Result<Int> = Result.failure<String>(testException).flatMap {
                blockRan = true
                Result.success(it.length)
            }

            result shouldBe Result.failure(testException)
            blockRan.shouldBeFalse()
        }

        it("should not catch any thrown exceptions") {
            val testException = RuntimeException("test exception")

            shouldThrow<RuntimeException> {
                Result.success("some value").flatMap<Int, String> {
                    throw testException
                }
            }.let { error ->
                error shouldBe testException
            }
        }

    }

})