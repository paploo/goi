// Generated from FuriganaCurlyBraceTemplateParser.g4 by ANTLR 4.13.1

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
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#emptyGroup}.
	 * @param ctx the parse tree
	 */
	void enterEmptyGroup(FuriganaCurlyBraceTemplateParser.EmptyGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#emptyGroup}.
	 * @param ctx the parse tree
	 */
	void exitEmptyGroup(FuriganaCurlyBraceTemplateParser.EmptyGroupContext ctx);
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
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#rubySubGroups}.
	 * @param ctx the parse tree
	 */
	void enterRubySubGroups(FuriganaCurlyBraceTemplateParser.RubySubGroupsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#rubySubGroups}.
	 * @param ctx the parse tree
	 */
	void exitRubySubGroups(FuriganaCurlyBraceTemplateParser.RubySubGroupsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#rubyText}.
	 * @param ctx the parse tree
	 */
	void enterRubyText(FuriganaCurlyBraceTemplateParser.RubyTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#rubyText}.
	 * @param ctx the parse tree
	 */
	void exitRubyText(FuriganaCurlyBraceTemplateParser.RubyTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeChars}.
	 * @param ctx the parse tree
	 */
	void enterNativeChars(FuriganaCurlyBraceTemplateParser.NativeCharsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FuriganaCurlyBraceTemplateParser#nativeChars}.
	 * @param ctx the parse tree
	 */
	void exitNativeChars(FuriganaCurlyBraceTemplateParser.NativeCharsContext ctx);
}