lexer grammar FuriganaCurlyBraceTemplateLexer;

@header {
    pacakge net.paploo.goi.gen.antlr
}

//STR: (~'{')+;
//GROUP_START: '{' -> pushMode(GROUP);
//
//mode GROUP;
//GROUP_END: '}' -> popMode;
//GROUP_SEPARATOR: '|';
//CHAR: (~('|' | '}'));
//RUBY_SEPARATOR: '・';
//RUBY_SUBGROUP: CHAR+;

STR: (~'{')+;
GROUP_START: '{' -> pushMode(GROUP_MODE);

mode GROUP_MODE;
NATIVE_GROUP_END: '}' -> popMode;
CHAR: (~('|' | '}'));
GROUP_PART_SEPARATOR: '|' -> pushMode(RUBY_TEXT_MODE);

mode RUBY_TEXT_MODE;
RUBY_GROUP_END: '}' -> popMode, popMode;
RUBY_SEPARATOR: '・';
RUBY_TEXT: (~('|' | '}' | '・'));