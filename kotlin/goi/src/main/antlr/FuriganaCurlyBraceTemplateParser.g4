parser grammar FuriganaCurlyBraceTemplateParser;

options { tokenVocab=FuriganaCurlyBraceTemplateLexer; }

@header {
    pacakge net.paploo.goi.gen.antlr
}

//Root rule:
template: (string | group)+;

string: STR;

//group: GROUP_START nativeChars GROUP_PART_SEPARATOR rubySubGroups RUBY_GROUP_END;
//nativeChars: CHAR+;
//rubySubGroups: (rubyText RUBY_SEPARATOR)* rubyText;
//rubyText: RUBY_TEXT+;

group: GROUP_START (rubyGroup | emptyGroup);
emptyGroup: nativeChars NATIVE_GROUP_END;
rubyGroup: nativeChars GROUP_PART_SEPARATOR rubySubGroups RUBY_GROUP_END;
rubySubGroups: (rubyText RUBY_SEPARATOR)* rubyText;
rubyText: RUBY_TEXT+;
nativeChars: CHAR+;