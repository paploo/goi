package net.paploo.goi.domain.tools.spellingclassifier

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.data.common.Spelling

class SpellingClassifierTest : DescribeSpec({

    describe("default classification") {

        val classifier = SpellingClassifier.default

        it("should classify single classification strings as their classification") {
            classifier.classify("日本語") shouldBe (Spelling.Kind.Kanji)
            classifier.classify("りんご") shouldBe (Spelling.Kind.Hiragana)
            classifier.classify("バス") shouldBe (Spelling.Kind.Katakana)
            classifier.classify("ー") shouldBe (Spelling.Kind.Katakana)
            classifier.classify("々") shouldBe (Spelling.Kind.CjkPunctuation)
            classifier.classify("Hello") shouldBe (Spelling.Kind.Latin)
            classifier.classify("32") shouldBe (Spelling.Kind.Latin)
            classifier.classify("３２").shouldBeNull()
        }

        it("should classify mixed kanji and kana as kanji") {
            val values = listOf("教える", "時々", "スーパーに行きます", "くノ一")
            values.forEach {
                withClue(it) {
                    classifier.classify(it) shouldBe (Spelling.Kind.Kanji)
                }
            }
        }

        it("should classify mixed kana as katakana") {
            val katakanaValues = listOf("サボる", "サボります", "ソラさん", "あかいバス")
            katakanaValues.forEach {
                withClue(it) {
                    classifier.classify(it) shouldBe (Spelling.Kind.Katakana)
                }
            }
        }

        it("should classify mixed 日本語 and Latin as if it were just 日本語 characters") {
            classifier.classify("あまり + negative") shouldBe Spelling.Kind.Hiragana
            classifier.classify("全然 + negative") shouldBe Spelling.Kind.Kanji
            classifier.classify("Ｔシャツ") shouldBe Spelling.Kind.Katakana
        }

    }

})