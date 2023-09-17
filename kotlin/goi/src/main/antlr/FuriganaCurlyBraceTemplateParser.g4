parser grammar FuriganaCurlyBraceTemplateParser;

options { tokenVocab=FuriganaCurlyBraceTemplateLexer; }

@header {
    package net.paploo.goi.gen.antlr;
}

//Root rule:
template: (string | group)+;

string: STR;

//group: GROUP_START (rubyGroup | emptyGroup); //if we wanted to support empry groups.
group: GROUP_START rubyGroup;
emptyGroup: nativeChars NATIVE_GROUP_END;
rubyGroup: nativeChars GROUP_PART_SEPARATOR rubySubGroups RUBY_GROUP_END;
rubySubGroups: (rubyText RUBY_SEPARATOR)* rubyText;
rubyText: HIRAGANA+ | KATAKANA+ | ROMAJI+;
nativeChars: (CJK_CHAR|FULL_WIDTH_ALAPHANUMERIC_CHAR)+;