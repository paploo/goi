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
		RULE_template = 0, RULE_string = 1, RULE_group = 2, RULE_emptyGroup = 3, 
		RULE_rubyGroup = 4, RULE_rubySubGroups = 5, RULE_rubyText = 6, RULE_nativeChars = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"template", "string", "group", "emptyGroup", "rubyGroup", "rubySubGroups", 
			"rubyText", "nativeChars"
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
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STR || _la==GROUP_START) {
				{
				setState(18);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STR:
					{
					setState(16);
					string();
					}
					break;
				case GROUP_START:
					{
					setState(17);
					group();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(23);
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
			setState(25);
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
			setState(27);
			match(GROUP_START);
			setState(28);
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
	public static class EmptyGroupContext extends ParserRuleContext {
		public NativeCharsContext nativeChars() {
			return getRuleContext(NativeCharsContext.class,0);
		}
		public TerminalNode NATIVE_GROUP_END() { return getToken(FuriganaCurlyBraceTemplateParser.NATIVE_GROUP_END, 0); }
		public EmptyGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterEmptyGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitEmptyGroup(this);
		}
	}

	public final EmptyGroupContext emptyGroup() throws RecognitionException {
		EmptyGroupContext _localctx = new EmptyGroupContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_emptyGroup);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			nativeChars();
			setState(31);
			match(NATIVE_GROUP_END);
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
		public NativeCharsContext nativeChars() {
			return getRuleContext(NativeCharsContext.class,0);
		}
		public TerminalNode GROUP_PART_SEPARATOR() { return getToken(FuriganaCurlyBraceTemplateParser.GROUP_PART_SEPARATOR, 0); }
		public RubySubGroupsContext rubySubGroups() {
			return getRuleContext(RubySubGroupsContext.class,0);
		}
		public TerminalNode RUBY_GROUP_END() { return getToken(FuriganaCurlyBraceTemplateParser.RUBY_GROUP_END, 0); }
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
		enterRule(_localctx, 8, RULE_rubyGroup);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			nativeChars();
			setState(34);
			match(GROUP_PART_SEPARATOR);
			setState(35);
			rubySubGroups();
			setState(36);
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
	public static class RubySubGroupsContext extends ParserRuleContext {
		public List<RubyTextContext> rubyText() {
			return getRuleContexts(RubyTextContext.class);
		}
		public RubyTextContext rubyText(int i) {
			return getRuleContext(RubyTextContext.class,i);
		}
		public List<TerminalNode> RUBY_SEPARATOR() { return getTokens(FuriganaCurlyBraceTemplateParser.RUBY_SEPARATOR); }
		public TerminalNode RUBY_SEPARATOR(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.RUBY_SEPARATOR, i);
		}
		public RubySubGroupsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rubySubGroups; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterRubySubGroups(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitRubySubGroups(this);
		}
	}

	public final RubySubGroupsContext rubySubGroups() throws RecognitionException {
		RubySubGroupsContext _localctx = new RubySubGroupsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_rubySubGroups);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(38);
					rubyText();
					setState(39);
					match(RUBY_SEPARATOR);
					}
					} 
				}
				setState(45);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(46);
			rubyText();
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
	public static class RubyTextContext extends ParserRuleContext {
		public List<TerminalNode> HIRAGANA() { return getTokens(FuriganaCurlyBraceTemplateParser.HIRAGANA); }
		public TerminalNode HIRAGANA(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.HIRAGANA, i);
		}
		public List<TerminalNode> KATAKANA() { return getTokens(FuriganaCurlyBraceTemplateParser.KATAKANA); }
		public TerminalNode KATAKANA(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.KATAKANA, i);
		}
		public List<TerminalNode> ROMAJI() { return getTokens(FuriganaCurlyBraceTemplateParser.ROMAJI); }
		public TerminalNode ROMAJI(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.ROMAJI, i);
		}
		public RubyTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rubyText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterRubyText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitRubyText(this);
		}
	}

	public final RubyTextContext rubyText() throws RecognitionException {
		RubyTextContext _localctx = new RubyTextContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rubyText);
		int _la;
		try {
			setState(63);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HIRAGANA:
				enterOuterAlt(_localctx, 1);
				{
				setState(49); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(48);
					match(HIRAGANA);
					}
					}
					setState(51); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==HIRAGANA );
				}
				break;
			case KATAKANA:
				enterOuterAlt(_localctx, 2);
				{
				setState(54); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(53);
					match(KATAKANA);
					}
					}
					setState(56); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==KATAKANA );
				}
				break;
			case ROMAJI:
				enterOuterAlt(_localctx, 3);
				{
				setState(59); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(58);
					match(ROMAJI);
					}
					}
					setState(61); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ROMAJI );
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
	public static class NativeCharsContext extends ParserRuleContext {
		public List<TerminalNode> CJK_CHAR() { return getTokens(FuriganaCurlyBraceTemplateParser.CJK_CHAR); }
		public TerminalNode CJK_CHAR(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.CJK_CHAR, i);
		}
		public List<TerminalNode> FULL_WIDTH_ALAPHANUMERIC_CHAR() { return getTokens(FuriganaCurlyBraceTemplateParser.FULL_WIDTH_ALAPHANUMERIC_CHAR); }
		public TerminalNode FULL_WIDTH_ALAPHANUMERIC_CHAR(int i) {
			return getToken(FuriganaCurlyBraceTemplateParser.FULL_WIDTH_ALAPHANUMERIC_CHAR, i);
		}
		public NativeCharsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nativeChars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).enterNativeChars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FuriganaCurlyBraceTemplateParserListener ) ((FuriganaCurlyBraceTemplateParserListener)listener).exitNativeChars(this);
		}
	}

	public final NativeCharsContext nativeChars() throws RecognitionException {
		NativeCharsContext _localctx = new NativeCharsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_nativeChars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(65);
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
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CJK_CHAR || _la==FULL_WIDTH_ALAPHANUMERIC_CHAR );
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
		"\u0004\u0001\u0010G\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u0013\b\u0000\n\u0000\f\u0000\u0016\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005*\b\u0005\n\u0005\f\u0005-\t\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0004\u00062\b\u0006\u000b\u0006\f\u00063\u0001\u0006\u0004"+
		"\u00067\b\u0006\u000b\u0006\f\u00068\u0001\u0006\u0004\u0006<\b\u0006"+
		"\u000b\u0006\f\u0006=\u0003\u0006@\b\u0006\u0001\u0007\u0004\u0007C\b"+
		"\u0007\u000b\u0007\f\u0007D\u0001\u0007\u0000\u0000\b\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0000\u0001\u0001\u0000\u0006\u0007G\u0000\u0014\u0001"+
		"\u0000\u0000\u0000\u0002\u0019\u0001\u0000\u0000\u0000\u0004\u001b\u0001"+
		"\u0000\u0000\u0000\u0006\u001e\u0001\u0000\u0000\u0000\b!\u0001\u0000"+
		"\u0000\u0000\n+\u0001\u0000\u0000\u0000\f?\u0001\u0000\u0000\u0000\u000e"+
		"B\u0001\u0000\u0000\u0000\u0010\u0013\u0003\u0002\u0001\u0000\u0011\u0013"+
		"\u0003\u0004\u0002\u0000\u0012\u0010\u0001\u0000\u0000\u0000\u0012\u0011"+
		"\u0001\u0000\u0000\u0000\u0013\u0016\u0001\u0000\u0000\u0000\u0014\u0012"+
		"\u0001\u0000\u0000\u0000\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0017"+
		"\u0001\u0000\u0000\u0000\u0016\u0014\u0001\u0000\u0000\u0000\u0017\u0018"+
		"\u0005\u0000\u0000\u0001\u0018\u0001\u0001\u0000\u0000\u0000\u0019\u001a"+
		"\u0005\u0001\u0000\u0000\u001a\u0003\u0001\u0000\u0000\u0000\u001b\u001c"+
		"\u0005\u0002\u0000\u0000\u001c\u001d\u0003\b\u0004\u0000\u001d\u0005\u0001"+
		"\u0000\u0000\u0000\u001e\u001f\u0003\u000e\u0007\u0000\u001f \u0005\u0004"+
		"\u0000\u0000 \u0007\u0001\u0000\u0000\u0000!\"\u0003\u000e\u0007\u0000"+
		"\"#\u0005\u0005\u0000\u0000#$\u0003\n\u0005\u0000$%\u0005\u000b\u0000"+
		"\u0000%\t\u0001\u0000\u0000\u0000&\'\u0003\f\u0006\u0000\'(\u0005\f\u0000"+
		"\u0000(*\u0001\u0000\u0000\u0000)&\u0001\u0000\u0000\u0000*-\u0001\u0000"+
		"\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,.\u0001"+
		"\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000./\u0003\f\u0006\u0000/\u000b"+
		"\u0001\u0000\u0000\u000002\u0005\r\u0000\u000010\u0001\u0000\u0000\u0000"+
		"23\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000"+
		"\u00004@\u0001\u0000\u0000\u000057\u0005\u000e\u0000\u000065\u0001\u0000"+
		"\u0000\u000078\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u000089\u0001"+
		"\u0000\u0000\u00009@\u0001\u0000\u0000\u0000:<\u0005\u000f\u0000\u0000"+
		";:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000"+
		"\u0000=>\u0001\u0000\u0000\u0000>@\u0001\u0000\u0000\u0000?1\u0001\u0000"+
		"\u0000\u0000?6\u0001\u0000\u0000\u0000?;\u0001\u0000\u0000\u0000@\r\u0001"+
		"\u0000\u0000\u0000AC\u0007\u0000\u0000\u0000BA\u0001\u0000\u0000\u0000"+
		"CD\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000"+
		"\u0000E\u000f\u0001\u0000\u0000\u0000\b\u0012\u0014+38=?D";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}