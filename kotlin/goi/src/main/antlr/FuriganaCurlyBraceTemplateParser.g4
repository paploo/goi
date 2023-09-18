parser grammar FuriganaCurlyBraceTemplateParser;

options { tokenVocab=FuriganaCurlyBraceTemplateLexer; }

@header {
    package net.paploo.goi.gen.antlr;
}

//Root rule:
template: (string | group)* EOF;
string: STR;

group: GROUP_START rubyGroup;

//TODO: Validate that native groups win over pronunciation groups when they match (empirically this seems true!).
rubyGroup: nativeGroup | pronuncationGroup;

//This is the normal use, like `{先生|せん・せい}`
nativeGroup: (cjkChar)+ GROUP_PART_SEPARATOR nativeRubySubGroups RUBY_GROUP_END;
cjkChar: CJK_CHAR | FULL_WIDTH_ALAPHANUMERIC_CHAR;
nativeRubySubGroups: (nativeRubyText RUBY_SEPARATOR)* nativeRubyText;
nativeRubyText: HIRAGANA+ | KATAKANA+;

//This allows using romaji to annotate. This is mostly useful for hints on odd/historic pronounciations;
// e.g. `〜て{は|wa}いけません` or some transliterations of 祝詞 where the standrad furigana doesn't match pronunciation.
pronuncationGroup: (jpChar)+ GROUP_PART_SEPARATOR pronuncationRubySubGroups RUBY_GROUP_END;
jpChar: CJK_CHAR | FULL_WIDTH_ALAPHANUMERIC_CHAR | HIRAGANA_CHAR | KATAKANA_CHAR;
pronuncationRubySubGroups: (pronunciationRubyText RUBY_SEPARATOR)* pronunciationRubyText;
pronunciationRubyText: ROMAJI+;