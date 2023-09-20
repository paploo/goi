package net.paploo.goi.domain.tools.spellingclassifier

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.data.common.Spelling

class SpellingClassifierTest : DescribeSpec({

    describe("default classification") {

        val classifier = SpellingClassifier.default

        it("should classify single classification strings as their classification") {
            classifier.invoke("日本語") shouldBe (Spelling.Kind.Kanji)
            classifier.invoke("りんご") shouldBe (Spelling.Kind.Hiragana)
            classifier.invoke("ゑ") shouldBe (Spelling.Kind.Hiragana)
            classifier.invoke("バス") shouldBe (Spelling.Kind.Katakana)
            classifier.invoke("ー") shouldBe (Spelling.Kind.Katakana)
            classifier.invoke("・") shouldBe (Spelling.Kind.Katakana)
            classifier.invoke("々") shouldBe (Spelling.Kind.CjkPunctuation)
            classifier.invoke("Hello") shouldBe (Spelling.Kind.Latin)
            classifier.invoke("32") shouldBe (Spelling.Kind.Latin)
            classifier.invoke("３２") shouldBe (Spelling.Kind.HalfAndFullWidth)
        }

        it("should classify the empty string as Unknown") {
            classifier.invoke("") shouldBe (Spelling.Kind.Unknown)
        }

        it("should classify mixed kanji and kana as kanji") {
            val values = listOf("教える", "時々", "スーパーに行きます", "くノ一")
            values.forEach {
                withClue(it) {
                    classifier.invoke(it) shouldBe (Spelling.Kind.Kanji)
                }
            }
        }

        it("should classify mixed kana as katakana") {
            val katakanaValues = listOf("サボる", "サボります", "ソラさん", "あかいバス")
            katakanaValues.forEach {
                withClue(it) {
                    classifier.invoke(it) shouldBe (Spelling.Kind.Katakana)
                }
            }
        }

        it("should classify mixed 日本語 and Latin as if it were just 日本語 characters") {
            classifier.invoke("あまり + negative") shouldBe Spelling.Kind.Hiragana
            classifier.invoke("全然 + negative") shouldBe Spelling.Kind.Kanji
            classifier.invoke("Ｔシャツ") shouldBe Spelling.Kind.Katakana
        }

    }

})
