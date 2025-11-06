// Generated from FuriganaCurlyBraceTemplateParser.g4 by ANTLR 4.13.2

    package net.paploo.goi.gen.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FuriganaCurlyBraceTemplateParser}.
 */
public interface FuriganaCurlyBraceTemplateParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#template}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(FuriganaCurlyBraceTemplateParser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#template}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(FuriganaCurlyBraceTemplateParser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(FuriganaCurlyBraceTemplateParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(FuriganaCurlyBraceTemplateParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(FuriganaCurlyBraceTemplateParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(FuriganaCurlyBraceTemplateParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#rubyGroup}.
	 * @param ctx the parse tree
	 */
	void enterRubyGroup(FuriganaCurlyBraceTemplateParser.RubyGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#rubyGroup}.
	 * @param ctx the parse tree
	 */
	void exitRubyGroup(FuriganaCurlyBraceTemplateParser.RubyGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeGroup}.
	 * @param ctx the parse tree
	 */
	void enterNativeGroup(FuriganaCurlyBraceTemplateParser.NativeGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeGroup}.
	 * @param ctx the parse tree
	 */
	void exitNativeGroup(FuriganaCurlyBraceTemplateParser.NativeGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#cjkChar}.
	 * @param ctx the parse tree
	 */
	void enterCjkChar(FuriganaCurlyBraceTemplateParser.CjkCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#cjkChar}.
	 * @param ctx the parse tree
	 */
	void exitCjkChar(FuriganaCurlyBraceTemplateParser.CjkCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeRubySubGroups}.
	 * @param ctx the parse tree
	 */
	void enterNativeRubySubGroups(FuriganaCurlyBraceTemplateParser.NativeRubySubGroupsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeRubySubGroups}.
	 * @param ctx the parse tree
	 */
	void exitNativeRubySubGroups(FuriganaCurlyBraceTemplateParser.NativeRubySubGroupsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeRubyText}.
	 * @param ctx the parse tree
	 */
	void enterNativeRubyText(FuriganaCurlyBraceTemplateParser.NativeRubyTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeRubyText}.
	 * @param ctx the parse tree
	 */
	void exitNativeRubyText(FuriganaCurlyBraceTemplateParser.NativeRubyTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#pronuncationGroup}.
	 * @param ctx the parse tree
	 */
	void enterPronuncationGroup(FuriganaCurlyBraceTemplateParser.PronuncationGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#pronuncationGroup}.
	 * @param ctx the parse tree
	 */
	void exitPronuncationGroup(FuriganaCurlyBraceTemplateParser.PronuncationGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#jpChar}.
	 * @param ctx the parse tree
	 */
	void enterJpChar(FuriganaCurlyBraceTemplateParser.JpCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#jpChar}.
	 * @param ctx the parse tree
	 */
	void exitJpChar(FuriganaCurlyBraceTemplateParser.JpCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#pronuncationRubySubGroups}.
	 * @param ctx the parse tree
	 */
	void enterPronuncationRubySubGroups(FuriganaCurlyBraceTemplateParser.PronuncationRubySubGroupsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#pronuncationRubySubGroups}.
	 * @param ctx the parse tree
	 */
	void exitPronuncationRubySubGroups(FuriganaCurlyBraceTemplateParser.PronuncationRubySubGroupsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#pronunciationRubyText}.
	 * @param ctx the parse tree
	 */
	void enterPronunciationRubyText(FuriganaCurlyBraceTemplateParser.PronunciationRubyTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#pronunciationRubyText}.
	 * @param ctx the parse tree
	 */
	void exitPronunciationRubyText(FuriganaCurlyBraceTemplateParser.PronunciationRubyTextContext ctx);
}