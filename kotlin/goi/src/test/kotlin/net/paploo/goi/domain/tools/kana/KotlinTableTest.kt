package net.paploo.goi.domain.tools.kana

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class KotlinTableTest : DescribeSpec({

    describe("kana shift") {

        it("should shift vowel") {
            KanaTable["く"]?.shiftTo(KanaTable.Column.I) shouldBe KanaTable["き"]
        }

        it("should be able to shift to itsself") {
            KanaTable["く"]?.let { kana ->
                kana.shiftTo(kana.coordinate.column) shouldBe kana
            }
        }

        it("should return null on an unmapped shift") {
            KanaTable["n"]?.shiftTo(KanaTable.Column.I).shouldBeNull()
        }

    }

    describe("change dakuten") {

        it("should change dakuten") {
            KanaTable["び"]?.changeDakuten(KanaTable.Row.Dakuten.Handakuten) shouldBe KanaTable["ぴ"]
        }

        it("should be able to change to itsself") {
            KanaTable["く"]?.let { kana ->
                kana.changeDakuten(kana.coordinate.row.dakuten) shouldBe kana
            }
        }

        it("should return null on an unmapped change") {
            KanaTable["n"]?.changeDakuten(KanaTable.Row.Dakuten.Handakuten).shouldBeNull()
        }

    }

})