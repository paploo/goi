lexer grammar FuriganaCurlyBraceTemplateLexer;

@header {
    package net.paploo.goi.gen.antlr;
}

//Rule precedence reminder:
// 1. The LONGEST match wins.
// 2. In a tie, the first rule wins.

//mode DEFAULT;
STR: (~('{' | '}'))+; //Don't match on either curly brace; first to not collide, and second to make it error if unmatched.
GROUP_START: '{' -> pushMode(GROUP_MODE);
INVALID_CHAR: .; //If no other rule matches, this will catch the input to make it more handlable.

mode GROUP_MODE;
NATIVE_GROUP_END: '}' -> popMode;
GROUP_PART_SEPARATOR: '|' -> pushMode(RUBY_TEXT_MODE);
//https://en.wikipedia.org/wiki/CJK_Unified_Ideographs_(Unicode_block)
CJK_CHAR: '\u4E00' .. '\u9FFF' | '々'; //CJK Ideographs; does not include kana or fixed width forms; treat 々 as an ideograph since it is used as such.
//https://en.wikipedia.org/wiki/Halfwidth_and_Fullwidth_Forms_(Unicode_block)
FULL_WIDTH_ALAPHANUMERIC_CHAR: '\uFF10' .. '\uFF3A' | '＆'; //Cheat in ＆ since it can be used in 'word' position.
HIRAGANA_CHAR: '\u3040' .. '\u309F';
KATAKANA_CHAR: '\u30A0' .. '\u30FF';
GROUP_INVALID_CHAR: .; //If no other rule matches, this will catch the input to make it more handlable.

mode RUBY_TEXT_MODE;
RUBY_GROUP_END: '}' -> popMode, popMode;
RUBY_SEPARATOR: '・' | '.';
//Grabbed from https://en.wikipedia.org/wiki/Kana unicode charts, mottiing the RUBY_SEPARATOR from the katakana chart.
HIRAGANA: [ぁ-ゖ]+; //Limited to supset of inputs we permit.
KATAKANA: [ァ-ヺー]+; //Limited to subset of inputs we permit.
ROMAJI: [a-zA-Z0-9]+;
RUBY_TEXT_INVALID_CHAR: .; //If no other rule matches, this will catch the input to make it more handlable.