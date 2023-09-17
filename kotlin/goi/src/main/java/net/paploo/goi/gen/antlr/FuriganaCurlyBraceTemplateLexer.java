// Generated from FuriganaCurlyBraceTemplateLexer.g4 by ANTLR 4.13.1

    package net.paploo.goi.gen.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FuriganaCurlyBraceTemplateLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		STR=1, GROUP_START=2, NATIVE_GROUP_END=3, CJK_CHAR=4, FULL_WIDTH_ALAPHANUMERIC_CHAR=5, 
		HIRAGANA_CHAR=6, KATAKANA_CHAR=7, OTHER_CHAR=8, GROUP_PART_SEPARATOR=9, 
		RUBY_GROUP_END=10, RUBY_SEPARATOR=11, HIRAGANA=12, KATAKANA=13, ROMAJI=14;
	public static final int
		GROUP_MODE=1, RUBY_TEXT_MODE=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "GROUP_MODE", "RUBY_TEXT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"STR", "GROUP_START", "NATIVE_GROUP_END", "CJK_CHAR", "FULL_WIDTH_ALAPHANUMERIC_CHAR", 
			"HIRAGANA_CHAR", "KATAKANA_CHAR", "OTHER_CHAR", "GROUP_PART_SEPARATOR", 
			"RUBY_GROUP_END", "RUBY_SEPARATOR", "HIRAGANA", "KATAKANA", "ROMAJI"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'{'", null, null, null, null, null, null, "'|'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "STR", "GROUP_START", "NATIVE_GROUP_END", "CJK_CHAR", "FULL_WIDTH_ALAPHANUMERIC_CHAR", 
			"HIRAGANA_CHAR", "KATAKANA_CHAR", "OTHER_CHAR", "GROUP_PART_SEPARATOR", 
			"RUBY_GROUP_END", "RUBY_SEPARATOR", "HIRAGANA", "KATAKANA", "ROMAJI"
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


	public FuriganaCurlyBraceTemplateLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FuriganaCurlyBraceTemplateLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000eP\u0006\uffff\uffff\u0006\uffff\uffff\u0006\uffff\uffff"+
		"\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002"+
		"\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005"+
		"\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002"+
		"\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002\f\u0007\f\u0002"+
		"\r\u0007\r\u0001\u0000\u0004\u0000!\b\u0000\u000b\u0000\f\u0000\"\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0004\u000bC\b\u000b\u000b\u000b\f\u000bD\u0001"+
		"\f\u0004\fH\b\f\u000b\f\f\fI\u0001\r\u0004\rM\b\r\u000b\r\f\rN\u0000\u0000"+
		"\u000e\u0003\u0001\u0005\u0002\u0007\u0003\t\u0004\u000b\u0005\r\u0006"+
		"\u000f\u0007\u0011\b\u0013\t\u0015\n\u0017\u000b\u0019\f\u001b\r\u001d"+
		"\u000e\u0003\u0000\u0001\u0002\u0006\u0001\u0000{{\u0003\u0000|}\u4e00"+
		"\u8000\u9fff\u8000\uff10\u8000\uff3a\u0002\u0000..\u30fb\u30fb\u0001\u0000"+
		"\u3041\u3096\u0002\u0000\u30a1\u30fa\u30fc\u30fc\u0003\u000009AZazQ\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0001"+
		"\u0007\u0001\u0000\u0000\u0000\u0001\t\u0001\u0000\u0000\u0000\u0001\u000b"+
		"\u0001\u0000\u0000\u0000\u0001\r\u0001\u0000\u0000\u0000\u0001\u000f\u0001"+
		"\u0000\u0000\u0000\u0001\u0011\u0001\u0000\u0000\u0000\u0001\u0013\u0001"+
		"\u0000\u0000\u0000\u0002\u0015\u0001\u0000\u0000\u0000\u0002\u0017\u0001"+
		"\u0000\u0000\u0000\u0002\u0019\u0001\u0000\u0000\u0000\u0002\u001b\u0001"+
		"\u0000\u0000\u0000\u0002\u001d\u0001\u0000\u0000\u0000\u0003 \u0001\u0000"+
		"\u0000\u0000\u0005$\u0001\u0000\u0000\u0000\u0007(\u0001\u0000\u0000\u0000"+
		"\t,\u0001\u0000\u0000\u0000\u000b.\u0001\u0000\u0000\u0000\r0\u0001\u0000"+
		"\u0000\u0000\u000f2\u0001\u0000\u0000\u0000\u00114\u0001\u0000\u0000\u0000"+
		"\u00136\u0001\u0000\u0000\u0000\u0015:\u0001\u0000\u0000\u0000\u0017?"+
		"\u0001\u0000\u0000\u0000\u0019B\u0001\u0000\u0000\u0000\u001bG\u0001\u0000"+
		"\u0000\u0000\u001dL\u0001\u0000\u0000\u0000\u001f!\b\u0000\u0000\u0000"+
		" \u001f\u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\" \u0001\u0000"+
		"\u0000\u0000\"#\u0001\u0000\u0000\u0000#\u0004\u0001\u0000\u0000\u0000"+
		"$%\u0005{\u0000\u0000%&\u0001\u0000\u0000\u0000&\'\u0006\u0001\u0000\u0000"+
		"\'\u0006\u0001\u0000\u0000\u0000()\u0005}\u0000\u0000)*\u0001\u0000\u0000"+
		"\u0000*+\u0006\u0002\u0001\u0000+\b\u0001\u0000\u0000\u0000,-\u0002\u4e00"+
		"\u8000\u9fff\u0000-\n\u0001\u0000\u0000\u0000./\u0002\u8000\uff10\u8000"+
		"\uff3a\u0000/\f\u0001\u0000\u0000\u000001\u0002\u3040\u309f\u00001\u000e"+
		"\u0001\u0000\u0000\u000023\u0002\u30a0\u30ff\u00003\u0010\u0001\u0000"+
		"\u0000\u000045\b\u0001\u0000\u00005\u0012\u0001\u0000\u0000\u000067\u0005"+
		"|\u0000\u000078\u0001\u0000\u0000\u000089\u0006\b\u0002\u00009\u0014\u0001"+
		"\u0000\u0000\u0000:;\u0005}\u0000\u0000;<\u0001\u0000\u0000\u0000<=\u0006"+
		"\t\u0001\u0000=>\u0006\t\u0001\u0000>\u0016\u0001\u0000\u0000\u0000?@"+
		"\u0007\u0002\u0000\u0000@\u0018\u0001\u0000\u0000\u0000AC\u0007\u0003"+
		"\u0000\u0000BA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000DB\u0001"+
		"\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000E\u001a\u0001\u0000\u0000"+
		"\u0000FH\u0007\u0004\u0000\u0000GF\u0001\u0000\u0000\u0000HI\u0001\u0000"+
		"\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J\u001c"+
		"\u0001\u0000\u0000\u0000KM\u0007\u0005\u0000\u0000LK\u0001\u0000\u0000"+
		"\u0000MN\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000"+
		"\u0000\u0000O\u001e\u0001\u0000\u0000\u0000\u0007\u0000\u0001\u0002\""+
		"DIN\u0003\u0005\u0001\u0000\u0004\u0000\u0000\u0005\u0002\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}