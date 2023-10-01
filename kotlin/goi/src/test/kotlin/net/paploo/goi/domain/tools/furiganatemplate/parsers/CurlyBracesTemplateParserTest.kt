package net.paploo.goi.domain.tools.furiganatemplate.parsers

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplateParseException

class CurlyBracesTemplateParserTest : DescribeSpec({

    val parser = CurlyBracesTemplateParser.default

    it("should parse a plain string of mixed characters") {
        val textString = "Ｑ；カメラを食べたい。<br/>Ａ；何？"
        val template = FuriganaTemplate.CurlyBraces(textString)

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(FuriganaParseTree.Element.Text(textString))
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should parse an empty string") {
        val template = FuriganaTemplate.CurlyBraces("")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = emptyList()
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should parse a string which is only a ruby text group") {
        val template = FuriganaTemplate.CurlyBraces("{私|わたし}")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(FuriganaParseTree.Element.RubyText("私", "わたし"))
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should parse a a multi-part group") {
        val template = FuriganaTemplate.CurlyBraces("{図書館|と・しょ・かん}")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(
                FuriganaParseTree.Element.RubyText("図", "と"),
                FuriganaParseTree.Element.RubyText("書", "しょ"),
                FuriganaParseTree.Element.RubyText("館", "かん")
            )
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should error on on an unmatched close bracket.") {
        val template = FuriganaTemplate.CurlyBraces("図書館|と・しょ・かん}")

        val result = parser(template)

        result.shouldBeFailure<FuriganaTemplateParseException>()
    }

    it("should parse a string with a single ruby text spanning across all input characters") {
        val template = FuriganaTemplate.CurlyBraces("{昨日|きのう}")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(FuriganaParseTree.Element.RubyText("昨日", "きのう"))
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should not parse a string with a mismatch in the number of characters to ruby sub-groups") {
        val template = FuriganaTemplate.CurlyBraces(" {京都|き・ょう・と}")
        val result = parser(template)
        result.shouldBeFailure<FuriganaTemplateParseException>()
    }

    it("should parse a string with a mix of groups and text") {
        val template = FuriganaTemplate.CurlyBraces("たけしさんは{英語|えい・ご}の{本|ほん}を{読|よ}んでいます。")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(
                FuriganaParseTree.Element.Text("たけしさんは"),
                FuriganaParseTree.Element.RubyText("英", "えい"),
                FuriganaParseTree.Element.RubyText("語", "ご"),
                FuriganaParseTree.Element.Text("の"),
                FuriganaParseTree.Element.RubyText("本", "ほん"),
                FuriganaParseTree.Element.Text("を"),
                FuriganaParseTree.Element.RubyText("読", "よ"),
                FuriganaParseTree.Element.Text("んでいます。"),
            )
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should allow a noma in the japanese text") {
        val template = FuriganaTemplate.CurlyBraces("たけしさんは{時々|とき・どき}あのカフェでコーヒーを{飲|の}みます。")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(
                FuriganaParseTree.Element.Text("たけしさんは"),
                FuriganaParseTree.Element.RubyText("時", "とき"),
                FuriganaParseTree.Element.RubyText("々", "どき"),
                FuriganaParseTree.Element.Text("あのカフェでコーヒーを"),
                FuriganaParseTree.Element.RubyText("飲", "の"),
                FuriganaParseTree.Element.Text("みます。"),
            )
        )
    }

    it("should allow romaji in ruby text") {
        val template = FuriganaTemplate.CurlyBraces("〜て{は|wa}いけません")

        val result = parser(template)

        val expectedTree = FuriganaParseTree(
            elements = listOf(
                FuriganaParseTree.Element.Text("〜て"),
                FuriganaParseTree.Element.RubyText("は", "wa"),
                FuriganaParseTree.Element.Text("いけません"),
            )
        )

        result shouldBe Result.success(expectedTree)
    }

    it("should not allow mixing kana/romaji types in the same chunk of text") {
        val template = FuriganaTemplate.CurlyBraces("{私|わたシ}")
        val result = parser(template)
        result.shouldBeFailure<FuriganaTemplateParseException>()
    }

    it("should not allow katakana in a ruby phonetic spelling") {
        val template = FuriganaTemplate.CurlyBraces("{カメラ|かめら}")
        val result = parser(template)
        result.shouldBeFailure<FuriganaTemplateParseException>()
    }

})