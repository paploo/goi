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
		STR=1, GROUP_START=2, INVALID_CHAR=3, NATIVE_GROUP_END=4, GROUP_PART_SEPARATOR=5, 
		CJK_CHAR=6, FULL_WIDTH_ALAPHANUMERIC_CHAR=7, HIRAGANA_CHAR=8, KATAKANA_CHAR=9, 
		GROUP_INVALID_CHAR=10, RUBY_GROUP_END=11, RUBY_SEPARATOR=12, HIRAGANA=13, 
		KATAKANA=14, ROMAJI=15, RUBY_TEXT_INVALID_CHAR=16;
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
			"STR", "GROUP_START", "INVALID_CHAR", "NATIVE_GROUP_END", "GROUP_PART_SEPARATOR", 
			"CJK_CHAR", "FULL_WIDTH_ALAPHANUMERIC_CHAR", "HIRAGANA_CHAR", "KATAKANA_CHAR", 
			"GROUP_INVALID_CHAR", "RUBY_GROUP_END", "RUBY_SEPARATOR", "HIRAGANA", 
			"KATAKANA", "ROMAJI", "RUBY_TEXT_INVALID_CHAR"
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
		"\u0004\u0000\u0010X\u0006\uffff\uffff\u0006\uffff\uffff\u0006\uffff\uffff"+
		"\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002"+
		"\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005"+
		"\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002"+
		"\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002\f\u0007\f\u0002"+
		"\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f\u0001\u0000"+
		"\u0004\u0000%\b\u0000\u000b\u0000\f\u0000&\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0004\fI\b\f\u000b\f\f\fJ\u0001\r\u0004\rN"+
		"\b\r\u000b\r\f\rO\u0001\u000e\u0004\u000eS\b\u000e\u000b\u000e\f\u000e"+
		"T\u0001\u000f\u0001\u000f\u0000\u0000\u0010\u0003\u0001\u0005\u0002\u0007"+
		"\u0003\t\u0004\u000b\u0005\r\u0006\u000f\u0007\u0011\b\u0013\t\u0015\n"+
		"\u0017\u000b\u0019\f\u001b\r\u001d\u000e\u001f\u000f!\u0010\u0003\u0000"+
		"\u0001\u0002\u0007\u0002\u0000{{}}\u0002\u0000\u3005\u3005\u4e00\u8000"+
		"\u9fff\u0002\u0000\u8000\uff06\u8000\uff06\u8000\uff10\u8000\uff3a\u0002"+
		"\u0000..\u30fb\u30fb\u0001\u0000\u3041\u3096\u0002\u0000\u30a1\u30fa\u30fc"+
		"\u30fc\u0003\u000009AZazY\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005"+
		"\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0001\t\u0001"+
		"\u0000\u0000\u0000\u0001\u000b\u0001\u0000\u0000\u0000\u0001\r\u0001\u0000"+
		"\u0000\u0000\u0001\u000f\u0001\u0000\u0000\u0000\u0001\u0011\u0001\u0000"+
		"\u0000\u0000\u0001\u0013\u0001\u0000\u0000\u0000\u0001\u0015\u0001\u0000"+
		"\u0000\u0000\u0002\u0017\u0001\u0000\u0000\u0000\u0002\u0019\u0001\u0000"+
		"\u0000\u0000\u0002\u001b\u0001\u0000\u0000\u0000\u0002\u001d\u0001\u0000"+
		"\u0000\u0000\u0002\u001f\u0001\u0000\u0000\u0000\u0002!\u0001\u0000\u0000"+
		"\u0000\u0003$\u0001\u0000\u0000\u0000\u0005(\u0001\u0000\u0000\u0000\u0007"+
		",\u0001\u0000\u0000\u0000\t.\u0001\u0000\u0000\u0000\u000b2\u0001\u0000"+
		"\u0000\u0000\r6\u0001\u0000\u0000\u0000\u000f8\u0001\u0000\u0000\u0000"+
		"\u0011:\u0001\u0000\u0000\u0000\u0013<\u0001\u0000\u0000\u0000\u0015>"+
		"\u0001\u0000\u0000\u0000\u0017@\u0001\u0000\u0000\u0000\u0019E\u0001\u0000"+
		"\u0000\u0000\u001bH\u0001\u0000\u0000\u0000\u001dM\u0001\u0000\u0000\u0000"+
		"\u001fR\u0001\u0000\u0000\u0000!V\u0001\u0000\u0000\u0000#%\b\u0000\u0000"+
		"\u0000$#\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&$\u0001\u0000"+
		"\u0000\u0000&\'\u0001\u0000\u0000\u0000\'\u0004\u0001\u0000\u0000\u0000"+
		"()\u0005{\u0000\u0000)*\u0001\u0000\u0000\u0000*+\u0006\u0001\u0000\u0000"+
		"+\u0006\u0001\u0000\u0000\u0000,-\t\u0000\u0000\u0000-\b\u0001\u0000\u0000"+
		"\u0000./\u0005}\u0000\u0000/0\u0001\u0000\u0000\u000001\u0006\u0003\u0001"+
		"\u00001\n\u0001\u0000\u0000\u000023\u0005|\u0000\u000034\u0001\u0000\u0000"+
		"\u000045\u0006\u0004\u0002\u00005\f\u0001\u0000\u0000\u000067\u0007\u0001"+
		"\u0000\u00007\u000e\u0001\u0000\u0000\u000089\u0007\u0002\u0000\u0000"+
		"9\u0010\u0001\u0000\u0000\u0000:;\u0002\u3040\u309f\u0000;\u0012\u0001"+
		"\u0000\u0000\u0000<=\u0002\u30a0\u30ff\u0000=\u0014\u0001\u0000\u0000"+
		"\u0000>?\t\u0000\u0000\u0000?\u0016\u0001\u0000\u0000\u0000@A\u0005}\u0000"+
		"\u0000AB\u0001\u0000\u0000\u0000BC\u0006\n\u0001\u0000CD\u0006\n\u0001"+
		"\u0000D\u0018\u0001\u0000\u0000\u0000EF\u0007\u0003\u0000\u0000F\u001a"+
		"\u0001\u0000\u0000\u0000GI\u0007\u0004\u0000\u0000HG\u0001\u0000\u0000"+
		"\u0000IJ\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000"+
		"\u0000\u0000K\u001c\u0001\u0000\u0000\u0000LN\u0007\u0005\u0000\u0000"+
		"ML\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000"+
		"\u0000OP\u0001\u0000\u0000\u0000P\u001e\u0001\u0000\u0000\u0000QS\u0007"+
		"\u0006\u0000\u0000RQ\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000"+
		"TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000U \u0001\u0000\u0000"+
		"\u0000VW\t\u0000\u0000\u0000W\"\u0001\u0000\u0000\u0000\u0007\u0000\u0001"+
		"\u0002&JOT\u0003\u0005\u0001\u0000\u0004\u0000\u0000\u0005\u0002\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}