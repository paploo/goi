lexer grammar FuriganaCurlyBraceTemplateLexer;

@header {
    package net.paploo.goi.gen.antlr;
}



//mode DEFAULT;
STR: (~'{')+;
GROUP_START: '{' -> pushMode(GROUP_MODE);

mode GROUP_MODE;
NATIVE_GROUP_END: '}' -> popMode;
//https://en.wikipedia.org/wiki/CJK_Unified_Ideographs_(Unicode_block)
CJK_CHAR: '\u4E00' .. '\u9FFF'; //CJK IDeographs; does not include kana or fixed width forms.
//https://en.wikipedia.org/wiki/Halfwidth_and_Fullwidth_Forms_(Unicode_block)
FULL_WIDTH_ALAPHANUMERIC_CHAR: '\uFF10' .. '\uFF3A';
HIRAGANA_CHAR: '\u3040' .. '\u309F';
KATAKANA_CHAR: '\u30A0' .. '\u30FF';
OTHER_CHAR: ~('|' | '}' | '\u4E00' .. '\u9FFF' | '\uFF10' .. '\uFF3A'); //

GROUP_PART_SEPARATOR: '|' -> pushMode(RUBY_TEXT_MODE);

mode RUBY_TEXT_MODE;
RUBY_GROUP_END: '}' -> popMode, popMode;
RUBY_SEPARATOR: '・' | '.';
//Grabbed from https://en.wikipedia.org/wiki/Kana unicode charts, mottiing the RUBY_SEPARATOR from the katakana chart.
HIRAGANA: [ぁ-ゖ]+; //Limited to supset of inputs we permit.
KATAKANA: [ァ-ヺー]+; //Limited to subset of inputs we permit.
ROMAJI: [a-zA-Z0-9]+;