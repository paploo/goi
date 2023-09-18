package net.paploo.goi.domain.tools.furiganatemplate.transformers

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaParseTree
import net.paploo.goi.domain.tools.furiganatemplate.FuriganaTemplate

class AnkiTemplateTransformerTest : DescribeSpec({

    val transformer = AnkiTemplateTransformer.default

    it("should parse an ordinary template") {
        val tree = FuriganaParseTree(
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

        val expectedTemplate = FuriganaTemplate.Anki(
            "たけしさんは 英[えい] 語[ご]の 本[ほん]を 読[よ]んでいます。"
        )

        transformer(tree) shouldBe Result.success(expectedTemplate)
    }

    it("should not put a leading space if it starts with a furigana group") {
        val tree = FuriganaParseTree(
            elements = listOf(
                FuriganaParseTree.Element.RubyText("図", "と"),
                FuriganaParseTree.Element.RubyText("書", "しょ"),
                FuriganaParseTree.Element.RubyText("館", "かん"),
            )
        )

        val expectedTemplate = FuriganaTemplate.Anki(
            "図[と] 書[しょ] 館[かん]"
        )

        transformer(tree) shouldBe Result.success(expectedTemplate)
    }

    it("should preserve the leading space if it starts with a space") {
        val tree = FuriganaParseTree(
            elements = listOf(
                FuriganaParseTree.Element.Text(" ここに"),
                FuriganaParseTree.Element.RubyText("図", "と"),
                FuriganaParseTree.Element.RubyText("書", "しょ"),
                FuriganaParseTree.Element.RubyText("館", "かん"),
            )
        )

        val expectedTemplate = FuriganaTemplate.Anki(
            " ここに 図[と] 書[しょ] 館[かん]"
        )

        transformer(tree) shouldBe Result.success(expectedTemplate)
    }

})