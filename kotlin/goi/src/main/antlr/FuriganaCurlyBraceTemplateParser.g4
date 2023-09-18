parser grammar FuriganaCurlyBraceTemplateParser;

options { tokenVocab=FuriganaCurlyBraceTemplateLexer; }

@header {
    package net.paploo.goi.gen.antlr;
}

//Root rule:
template: (string | group)* EOF;
string: STR;

//group: GROUP_START (rubyGroup | emptyGroup); //if we wanted to support empry groups.
group: GROUP_START rubyGroup;

emptyGroup: nativeChars NATIVE_GROUP_END;

rubyGroup: nativeChars GROUP_PART_SEPARATOR rubySubGroups RUBY_GROUP_END;
rubySubGroups: (rubyText RUBY_SEPARATOR)* rubyText;
rubyText: HIRAGANA+ | KATAKANA+ | ROMAJI+; //TODO: Remove Romaji when in a native group.
nativeChars: (CJK_CHAR | FULL_WIDTH_ALAPHANUMERIC_CHAR)+;

//TODO: Split rubyGroup into a nativeRubyGroup of nativChars to Kana, and pronunciationRubyGroup of native+kana to romaji
//pronounciationGroup: nativeChars GROUP_PART_SEPARATOR pronunciationSubGroups RUBY_GROUP_END;
//pronunciationSubGroups: (rubyTex)