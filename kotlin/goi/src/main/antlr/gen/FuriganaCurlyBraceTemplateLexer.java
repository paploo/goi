// Generated from /Users/paploo/Library/CloudStorage/Dropbox/Projects/Ruby/goi/kotlin/goi/src/main/antlr/FuriganaCurlyBraceTemplateLexer.g4 by ANTLR 4.13.1

    pacakge net.paploo.goi.gen.antlr

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
		STR=1, GROUP_START=2, NATIVE_GROUP_END=3, CHAR=4, GROUP_PART_SEPARATOR=5, 
		RUBY_GROUP_END=6, RUBY_SEPARATOR=7, RUBY_TEXT=8;
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
			"STR", "GROUP_START", "NATIVE_GROUP_END", "CHAR", "GROUP_PART_SEPARATOR", 
			"RUBY_GROUP_END", "RUBY_SEPARATOR", "RUBY_TEXT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'{'", null, null, "'|'", null, "'\\u30FB'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "STR", "GROUP_START", "NATIVE_GROUP_END", "CHAR", "GROUP_PART_SEPARATOR", 
			"RUBY_GROUP_END", "RUBY_SEPARATOR", "RUBY_TEXT"
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
		"\u0004\u0000\b/\u0006\uffff\uffff\u0006\uffff\uffff\u0006\uffff\uffff"+
		"\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002"+
		"\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005"+
		"\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001\u0000\u0004\u0000"+
		"\u0015\b\u0000\u000b\u0000\f\u0000\u0016\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0000\u0000\b\u0003\u0001\u0005\u0002\u0007"+
		"\u0003\t\u0004\u000b\u0005\r\u0006\u000f\u0007\u0011\b\u0003\u0000\u0001"+
		"\u0002\u0003\u0001\u0000{{\u0001\u0000|}\u0002\u0000|}\u30fb\u30fb-\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0001"+
		"\u0007\u0001\u0000\u0000\u0000\u0001\t\u0001\u0000\u0000\u0000\u0001\u000b"+
		"\u0001\u0000\u0000\u0000\u0002\r\u0001\u0000\u0000\u0000\u0002\u000f\u0001"+
		"\u0000\u0000\u0000\u0002\u0011\u0001\u0000\u0000\u0000\u0003\u0014\u0001"+
		"\u0000\u0000\u0000\u0005\u0018\u0001\u0000\u0000\u0000\u0007\u001c\u0001"+
		"\u0000\u0000\u0000\t \u0001\u0000\u0000\u0000\u000b\"\u0001\u0000\u0000"+
		"\u0000\r&\u0001\u0000\u0000\u0000\u000f+\u0001\u0000\u0000\u0000\u0011"+
		"-\u0001\u0000\u0000\u0000\u0013\u0015\b\u0000\u0000\u0000\u0014\u0013"+
		"\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000\u0016\u0014"+
		"\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000\u0000\u0000\u0017\u0004"+
		"\u0001\u0000\u0000\u0000\u0018\u0019\u0005{\u0000\u0000\u0019\u001a\u0001"+
		"\u0000\u0000\u0000\u001a\u001b\u0006\u0001\u0000\u0000\u001b\u0006\u0001"+
		"\u0000\u0000\u0000\u001c\u001d\u0005}\u0000\u0000\u001d\u001e\u0001\u0000"+
		"\u0000\u0000\u001e\u001f\u0006\u0002\u0001\u0000\u001f\b\u0001\u0000\u0000"+
		"\u0000 !\b\u0001\u0000\u0000!\n\u0001\u0000\u0000\u0000\"#\u0005|\u0000"+
		"\u0000#$\u0001\u0000\u0000\u0000$%\u0006\u0004\u0002\u0000%\f\u0001\u0000"+
		"\u0000\u0000&\'\u0005}\u0000\u0000\'(\u0001\u0000\u0000\u0000()\u0006"+
		"\u0005\u0001\u0000)*\u0006\u0005\u0001\u0000*\u000e\u0001\u0000\u0000"+
		"\u0000+,\u0005\u30fb\u0000\u0000,\u0010\u0001\u0000\u0000\u0000-.\b\u0002"+
		"\u0000\u0000.\u0012\u0001\u0000\u0000\u0000\u0004\u0000\u0001\u0002\u0016"+
		"\u0003\u0005\u0001\u0000\u0004\u0000\u0000\u0005\u0002\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}