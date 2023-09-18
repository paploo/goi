// Generated from FuriganaCurlyBraceTemplateParser.g4 by ANTLR 4.13.1

    package net.paploo.goi.gen.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FuriganaCurlyBraceTemplateParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		STR=1, GROUP_START=2, INVALID_CHAR=3, NATIVE_GROUP_END=4, GROUP_PART_SEPARATOR=5, 
		CJK_CHAR=6, FULL_WIDTH_ALAPHANUMERIC_CHAR=7, HIRAGANA_CHAR=8, KATAKANA_CHAR=9, 
		GROUP_INVALID_CHAR=10, RUBY_GROUP_END=11, RUBY_SEPARATOR=12, HIRAGANA=13, 
		KATAKANA=14, ROMAJI=15, RUBY_TEXT_INVALID_CHAR=16;
	public static final int
		RULE_template = 0, RULE_string = 1, RULE_group = 2, RULE_rubyGroup = 3, 
		RULE_nativeGroup = 4, RULE_cjkChar = 5, RULE_nativeRubySubGroups = 6, 
		RULE_nativeRubyText = 7, RULE_pronuncationGroup = 8, RULE_jpChar = 9, 
		RULE_pronuncationRubySubGroups = 10, RULE_pronunciationRubyText = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"template", "string", "group", "rubyGroup", "nativeGroup", "cjkChar", 
			"nativeRubySubGroups", "nativeRubyText", "pronuncationGroup", "jpChar", 
			"pronuncationRubySubGroups", "pronunciationRubyText"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'{'", null, null, "'|'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "STR", "GROUP_START", "INVALID_CHAR", "NATIVE_GROUP_END", "GROUP_PART_SEPARATOR", 
			"CJK_CHAR", "FULL_WIDTH_ALAPHANUMERIC_CHAR", "HIRAGANA_CHAR", "KATAKANA_CHAR", 
			"GROUP_INVALID_CHAR", "RUBY_GROUP_END", "RUBY_SEPARATOR", "HIRAGANA", 
			"KATAKANA", "ROMAJI", "RUBY_TEXT_INVALID_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FuriganaCurlyBraceTemplateParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FuriganaCurlyBraceTemplateParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TemplateContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(FuriganaCurlyBraceTemplateParser.EOF, 0); }
		public List<StringContext> string() {
			return getRuleContexts(StringContext.class);
		}
		public StringContext string(int i) {
			return getRuleContext(StringContext.class,i);
		}
		public List<GroupContext> group() {
			return getRuleContexts(GroupContext.class);
		}
		public GroupContext group(int i) {
			return getRuleContext(GroupContext.class,i);
		}
		public TemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitTemplate(this);
		}
	}

	public final TemplateContext template() throws RecognitionException {
		TemplateContext _localctx = new TemplateContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_template);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STR || _la==GROUP_START) {
				{
				setState(26);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STR:
					{
					setState(24);
					string();
					}
					break;
				case GROUP_START:
					{
					setState(25);
					group();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(FuriganaCurlyBraceTemplateParser.STR, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupContext extends ParserRuleContext {
		public TerminalNode GROUP_START() { return getToken(FuriganaCurlyBraceTemplateParser.GROUP_START, 0); }
		public RubyGroupContext rubyGroup() {
			return getRuleContext(RubyGroupContext.class,0);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitGroup(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_group);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			match(GROUP_START);
			setState(36);
			rubyGroup();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RubyGroupContext extends ParserRuleContext {
		public NativeGroupContext nativeGroup() {
			return getRuleContext(NativeGroupContext.class,0);
		}
		public PronuncationGroupContext pronuncationGroup() {
			return getRuleContext(PronuncationGroupContext.class,0);
		}
		public RubyGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rubyGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterRubyGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitRubyGroup(this);
		}
	}

	public final RubyGroupContext rubyGroup() throws RecognitionException {
		RubyGroupContext _localctx = new RubyGroupContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_rubyGroup);
		try {
			setState(40);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				nativeGroup();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
				pronuncationGroup();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NativeGroupContext extends ParserRuleContext {
		public TerminalNode GROUP_PART_SEPARATOR() { return getToken(FuriganaCurlyBraceTemplateParser.GROUP_PART_SEPARATOR, 0); }
		public NativeRubySubGroupsContext nativeRubySubGroups() {
			return getRuleContext(NativeRubySubGroupsContext.class,0);
		}
		public TerminalNode RUBY_GROUP_END() { return getToken(FuriganaCurlyBraceTemplateParser.RUBY_GROUP_END, 0); }
		public List<CjkCharContext> cjkChar() {
			return getRuleContexts(CjkCharContext.class);
		}
		public CjkCharContext cjkChar(int i) {
			return getRuleContext(CjkCharContext.class,i);
		}
		public NativeGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nativeGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterNativeGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitNativeGroup(this);
		}
	}

	public final NativeGroupContext nativeGroup() throws RecognitionException {
		NativeGroupContext _localctx = new NativeGroupContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_nativeGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				cjkChar();
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CJK_CHAR || _la==FULL_WIDTH_ALAPHANUMERIC_CHAR );
			setState(47);
			match(GROUP_PART_SEPARATOR);
			setState(48);
			nativeRubySubGroups();
			setState(49);
			match(RUBY_GROUP_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CjkCharContext extends ParserRuleContext {
		public TerminalNode CJK_CHAR() { return getToken(FuriganaCurlyBraceTemplateParser.CJK_CHAR, 0); }
		public TerminalNode FULL_WIDTH_ALAPHANUMERIC_CHAR() { return getToken(FuriganaCurlyBraceTemplateParser.FULL_WIDTH_ALAPHANUMERIC_CHAR, 0); }
		public CjkCharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cjkChar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterCjkChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitCjkChar(this);
		}
	}

	public final CjkCharContext cjkChar() throws RecognitionException {
		CjkCharContext _localctx = new CjkCharContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cjkChar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_la = _input.LA(1);
			if ( !(_la==CJK_CHAR || _la==FULL_WIDTH_ALAPHANUMERIC_CHAR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NativeRubySubGroupsContext extends ParserRuleContext {
		public List<NativeRubyTextContext> nativeRubyText() {
			return getRuleContexts(NativeRubyTextContext.class);
		}
		public NativeRubyTextContext nativeRubyText(int i) {
			return getRuleContext(NativeRubyTextContext.class,i);
		}
		public List<TerminalNode> RUBY_SEPARATOR() { return getTokens(FuriganaCurlyBraceTemplateParser.RUBY_SEPARATOR); }
		public TerminalNode RUBY_SEPARATOR(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.RUBY_SEPARATOR, i);
		}
		public NativeRubySubGroupsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nativeRubySubGroups; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterNativeRubySubGroups(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitNativeRubySubGroups(this);
		}
	}

	public final NativeRubySubGroupsContext nativeRubySubGroups() throws RecognitionException {
		NativeRubySubGroupsContext _localctx = new NativeRubySubGroupsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_nativeRubySubGroups);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(53);
					nativeRubyText();
					setState(54);
					match(RUBY_SEPARATOR);
					}
					} 
				}
				setState(60);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(61);
			nativeRubyText();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NativeRubyTextContext extends ParserRuleContext {
		public List<TerminalNode> HIRAGANA() { return getTokens(FuriganaCurlyBraceTemplateParser.HIRAGANA); }
		public TerminalNode HIRAGANA(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.HIRAGANA, i);
		}
		public List<TerminalNode> KATAKANA() { return getTokens(FuriganaCurlyBraceTemplateParser.KATAKANA); }
		public TerminalNode KATAKANA(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.KATAKANA, i);
		}
		public NativeRubyTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nativeRubyText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterNativeRubyText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitNativeRubyText(this);
		}
	}

	public final NativeRubyTextContext nativeRubyText() throws RecognitionException {
		NativeRubyTextContext _localctx = new NativeRubyTextContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_nativeRubyText);
		int _la;
		try {
			setState(73);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HIRAGANA:
				enterOuterAlt(_localctx, 1);
				{
				setState(64); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(63);
					match(HIRAGANA);
					}
					}
					setState(66); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==HIRAGANA );
				}
				break;
			case KATAKANA:
				enterOuterAlt(_localctx, 2);
				{
				setState(69); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(68);
					match(KATAKANA);
					}
					}
					setState(71); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==KATAKANA );
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PronuncationGroupContext extends ParserRuleContext {
		public TerminalNode GROUP_PART_SEPARATOR() { return getToken(FuriganaCurlyBraceTemplateParser.GROUP_PART_SEPARATOR, 0); }
		public PronuncationRubySubGroupsContext pronuncationRubySubGroups() {
			return getRuleContext(PronuncationRubySubGroupsContext.class,0);
		}
		public TerminalNode RUBY_GROUP_END() { return getToken(FuriganaCurlyBraceTemplateParser.RUBY_GROUP_END, 0); }
		public List<JpCharContext> jpChar() {
			return getRuleContexts(JpCharContext.class);
		}
		public JpCharContext jpChar(int i) {
			return getRuleContext(JpCharContext.class,i);
		}
		public PronuncationGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronuncationGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterPronuncationGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitPronuncationGroup(this);
		}
	}

	public final PronuncationGroupContext pronuncationGroup() throws RecognitionException {
		PronuncationGroupContext _localctx = new PronuncationGroupContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_pronuncationGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(75);
				jpChar();
				}
				}
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 960L) != 0) );
			setState(80);
			match(GROUP_PART_SEPARATOR);
			setState(81);
			pronuncationRubySubGroups();
			setState(82);
			match(RUBY_GROUP_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JpCharContext extends ParserRuleContext {
		public TerminalNode CJK_CHAR() { return getToken(FuriganaCurlyBraceTemplateParser.CJK_CHAR, 0); }
		public TerminalNode FULL_WIDTH_ALAPHANUMERIC_CHAR() { return getToken(FuriganaCurlyBraceTemplateParser.FULL_WIDTH_ALAPHANUMERIC_CHAR, 0); }
		public TerminalNode HIRAGANA_CHAR() { return getToken(FuriganaCurlyBraceTemplateParser.HIRAGANA_CHAR, 0); }
		public TerminalNode KATAKANA_CHAR() { return getToken(FuriganaCurlyBraceTemplateParser.KATAKANA_CHAR, 0); }
		public JpCharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jpChar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterJpChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitJpChar(this);
		}
	}

	public final JpCharContext jpChar() throws RecognitionException {
		JpCharContext _localctx = new JpCharContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_jpChar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 960L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PronuncationRubySubGroupsContext extends ParserRuleContext {
		public List<PronunciationRubyTextContext> pronunciationRubyText() {
			return getRuleContexts(PronunciationRubyTextContext.class);
		}
		public PronunciationRubyTextContext pronunciationRubyText(int i) {
			return getRuleContext(PronunciationRubyTextContext.class,i);
		}
		public List<TerminalNode> RUBY_SEPARATOR() { return getTokens(FuriganaCurlyBraceTemplateParser.RUBY_SEPARATOR); }
		public TerminalNode RUBY_SEPARATOR(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.RUBY_SEPARATOR, i);
		}
		public PronuncationRubySubGroupsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronuncationRubySubGroups; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterPronuncationRubySubGroups(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitPronuncationRubySubGroups(this);
		}
	}

	public final PronuncationRubySubGroupsContext pronuncationRubySubGroups() throws RecognitionException {
		PronuncationRubySubGroupsContext _localctx = new PronuncationRubySubGroupsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pronuncationRubySubGroups);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(86);
					pronunciationRubyText();
					setState(87);
					match(RUBY_SEPARATOR);
					}
					} 
				}
				setState(93);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(94);
			pronunciationRubyText();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PronunciationRubyTextContext extends ParserRuleContext {
		public List<TerminalNode> ROMAJI() { return getTokens(FuriganaCurlyBraceTemplateParser.ROMAJI); }
		public TerminalNode ROMAJI(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.ROMAJI, i);
		}
		public PronunciationRubyTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronunciationRubyText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterPronunciationRubyText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitPronunciationRubyText(this);
		}
	}

	public final PronunciationRubyTextContext pronunciationRubyText() throws RecognitionException {
		PronunciationRubyTextContext _localctx = new PronunciationRubyTextContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pronunciationRubyText);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(96);
				match(ROMAJI);
				}
				}
				setState(99); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ROMAJI );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0010f\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u001b\b\u0000\n\u0000\f\u0000\u001e\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0003\u0003)\b\u0003\u0001\u0004"+
		"\u0004\u0004,\b\u0004\u000b\u0004\f\u0004-\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u00069\b\u0006\n\u0006\f\u0006<\t\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0004\u0007A\b\u0007\u000b\u0007\f\u0007B\u0001\u0007"+
		"\u0004\u0007F\b\u0007\u000b\u0007\f\u0007G\u0003\u0007J\b\u0007\u0001"+
		"\b\u0004\bM\b\b\u000b\b\f\bN\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0005\nZ\b\n\n\n\f\n]\t\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0004\u000bb\b\u000b\u000b\u000b\f\u000bc\u0001\u000b\u0000\u0000"+
		"\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0002"+
		"\u0001\u0000\u0006\u0007\u0001\u0000\u0006\td\u0000\u001c\u0001\u0000"+
		"\u0000\u0000\u0002!\u0001\u0000\u0000\u0000\u0004#\u0001\u0000\u0000\u0000"+
		"\u0006(\u0001\u0000\u0000\u0000\b+\u0001\u0000\u0000\u0000\n3\u0001\u0000"+
		"\u0000\u0000\f:\u0001\u0000\u0000\u0000\u000eI\u0001\u0000\u0000\u0000"+
		"\u0010L\u0001\u0000\u0000\u0000\u0012T\u0001\u0000\u0000\u0000\u0014["+
		"\u0001\u0000\u0000\u0000\u0016a\u0001\u0000\u0000\u0000\u0018\u001b\u0003"+
		"\u0002\u0001\u0000\u0019\u001b\u0003\u0004\u0002\u0000\u001a\u0018\u0001"+
		"\u0000\u0000\u0000\u001a\u0019\u0001\u0000\u0000\u0000\u001b\u001e\u0001"+
		"\u0000\u0000\u0000\u001c\u001a\u0001\u0000\u0000\u0000\u001c\u001d\u0001"+
		"\u0000\u0000\u0000\u001d\u001f\u0001\u0000\u0000\u0000\u001e\u001c\u0001"+
		"\u0000\u0000\u0000\u001f \u0005\u0000\u0000\u0001 \u0001\u0001\u0000\u0000"+
		"\u0000!\"\u0005\u0001\u0000\u0000\"\u0003\u0001\u0000\u0000\u0000#$\u0005"+
		"\u0002\u0000\u0000$%\u0003\u0006\u0003\u0000%\u0005\u0001\u0000\u0000"+
		"\u0000&)\u0003\b\u0004\u0000\')\u0003\u0010\b\u0000(&\u0001\u0000\u0000"+
		"\u0000(\'\u0001\u0000\u0000\u0000)\u0007\u0001\u0000\u0000\u0000*,\u0003"+
		"\n\u0005\u0000+*\u0001\u0000\u0000\u0000,-\u0001\u0000\u0000\u0000-+\u0001"+
		"\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000"+
		"/0\u0005\u0005\u0000\u000001\u0003\f\u0006\u000012\u0005\u000b\u0000\u0000"+
		"2\t\u0001\u0000\u0000\u000034\u0007\u0000\u0000\u00004\u000b\u0001\u0000"+
		"\u0000\u000056\u0003\u000e\u0007\u000067\u0005\f\u0000\u000079\u0001\u0000"+
		"\u0000\u000085\u0001\u0000\u0000\u00009<\u0001\u0000\u0000\u0000:8\u0001"+
		"\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;=\u0001\u0000\u0000\u0000"+
		"<:\u0001\u0000\u0000\u0000=>\u0003\u000e\u0007\u0000>\r\u0001\u0000\u0000"+
		"\u0000?A\u0005\r\u0000\u0000@?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000"+
		"\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CJ\u0001\u0000"+
		"\u0000\u0000DF\u0005\u000e\u0000\u0000ED\u0001\u0000\u0000\u0000FG\u0001"+
		"\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"HJ\u0001\u0000\u0000\u0000I@\u0001\u0000\u0000\u0000IE\u0001\u0000\u0000"+
		"\u0000J\u000f\u0001\u0000\u0000\u0000KM\u0003\u0012\t\u0000LK\u0001\u0000"+
		"\u0000\u0000MN\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001"+
		"\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PQ\u0005\u0005\u0000\u0000"+
		"QR\u0003\u0014\n\u0000RS\u0005\u000b\u0000\u0000S\u0011\u0001\u0000\u0000"+
		"\u0000TU\u0007\u0001\u0000\u0000U\u0013\u0001\u0000\u0000\u0000VW\u0003"+
		"\u0016\u000b\u0000WX\u0005\f\u0000\u0000XZ\u0001\u0000\u0000\u0000YV\u0001"+
		"\u0000\u0000\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000"+
		"[\\\u0001\u0000\u0000\u0000\\^\u0001\u0000\u0000\u0000][\u0001\u0000\u0000"+
		"\u0000^_\u0003\u0016\u000b\u0000_\u0015\u0001\u0000\u0000\u0000`b\u0005"+
		"\u000f\u0000\u0000a`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000"+
		"ca\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000d\u0017\u0001\u0000"+
		"\u0000\u0000\u000b\u001a\u001c(-:BGIN[c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}