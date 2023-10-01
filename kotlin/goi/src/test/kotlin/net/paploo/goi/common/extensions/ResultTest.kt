package net.paploo.goi.common.extensions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import java.util.NoSuchElementException

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

    describe("mapFailure") {

        it("should not execute the block if success") {
            var didExecute: Boolean = false

            val result = Result.success("猫").mapFailure {
                didExecute = true
                it
            }

            result.shouldBeSuccess().shouldBe("猫")
            didExecute.shouldBeFalse()
        }

        it("should execute the block with the exception and return the result if failure") {
            val excpA = RuntimeException("test exception")
            val excpBMessage = "test excp 狸"

            val result = Result.failure<String>(excpA).mapFailure {
                RuntimeException(excpBMessage, it)
            }

            result.shouldBeFailure {
                //Validate both that we got the result back and the exception was available for use.
                it.message shouldBe excpBMessage
                it.cause shouldBe excpA
            }
        }

    }

    describe("finally") {

        it("should run on a success and return the original success") {
            var didRun = false

            val res = Result.success("猫").finally { didRun = true }

            didRun.shouldBeTrue()
            res shouldBe Result.success("猫")
        }

        it("should run on a failure and return the original failure") {
            val error = RuntimeException("やばい！")
            var didRun = false

            val res = Result.failure<String>(error).finally { didRun = true }

            didRun.shouldBeTrue()
            res shouldBe Result.failure(error)
        }

        it("should return the failure if the finally throws") {
            val error = RuntimeException("やばい！")
            var didRun = false

            val res = Result.success("猫").finally { didRun = true; throw error }

            didRun.shouldBeTrue()
            res shouldBe Result.failure(error)
        }

    }

    describe("flatFinally") {

        it("should run on a success and return the original success") {
            var didRun = false

            val res = Result.success("猫").flatFinally { didRun = true; Result.success(Unit) }

            didRun.shouldBeTrue()
            res shouldBe Result.success("猫")
        }

        it("should run on a failure and return the original failure") {
            val error = RuntimeException("やばい！")
            var didRun = false

            val res = Result.failure<String>(error).flatFinally { didRun = true;  Result.success(Unit) }

            didRun.shouldBeTrue()
            res shouldBe Result.failure(error)
        }

        it("should return the failure if the finally throws") {
            val error = RuntimeException("やばい！")
            var didRun = false

            val res = Result.success("猫").flatFinally { didRun = true; Result.failure<Unit>(error) }

            didRun.shouldBeTrue()
            res shouldBe Result.failure(error)
        }

    }

    describe("sequenceToResult") {

        it("should turn a list of successes into a successful list of the values") {
            val input = listOf(
                Result.success("一"),
                Result.success("二"),
                Result.success("三"),
            )

            input.sequenceToResult() shouldBe Result.success(listOf("一", "二", "三"))
        }

        it("should return the first failure found in the list") {
            val error = RuntimeException("Test Exception")
            val input = listOf(
                Result.success("一"),
                Result.failure(error),
                Result.success("二"),
                Result.success("三"),
                Result.failure(RuntimeException("This is not the exception you are looking for")),
            )

            input.sequenceToResult() shouldBe Result.failure(error)
        }

        it("should convert an empty list to a success of an empty list") {
            emptyList<Result<String>>().sequenceToResult() shouldBe Result.success(emptyList<String>())
        }

    }

    describe("sequenceToNullable") {

        //Note, the type signatures on the assignments are as important as the values themselves!

        it("should sequence a success of a value to the value") {
            val s: String? = "狸"
            val r: Result<String?> = Result.success(s)

            val sequenced: Result<String>? = r.sequenceToNullable()

            sequenced shouldBe Result.success("狸")
        }

        it("should sequence a success of a null to a null") {
            val s: String? = null
            val r: Result<String?> = Result.success(s)

            val sequenced: Result<String>? = r.sequenceToNullable()

            sequenced.shouldBeNull()
        }

        it("should sequence a failure to the failure") {
            val excp = RuntimeException("test exception")
            val r: Result<String?> = Result.failure(excp)

            val sequenced: Result<String>? = r.sequenceToNullable()

            sequenced shouldBe Result.failure(excp)
        }

    }

    describe("nullable sequenceToResult") {

        //Note, the type signatures on the assignments are as important as the values themselves!

        it("should sequence a null to a success of a null") {
            val r: Result<String>? = null

            val sequenced: Result<String?> = r.sequenceToResult()

            sequenced shouldBe Result.success(null)
        }

        it("should sequence a non-null success to a success of a non-null") {
            val s: String = "狸"
            val r: Result<String>? = Result.success(s)

            val sequenced: Result<String?> = r.sequenceToResult()

            sequenced shouldBe Result.success(s)
        }

        it("should sequence a failure to a failure") {
            val excp = RuntimeException("やばい！")
            val r: Result<String>? = Result.failure(excp)

            val sequenced: Result<String?> = r.sequenceToResult()

            sequenced shouldBe Result.failure(excp)
        }

    }

})