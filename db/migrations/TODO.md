After we move to Kotlin, there are a few revamps to do:
- The preferred spelling should be *augmented* with a `furigana_template` version.
  - This is potentially just an addition to the spelling records?
  - But we might also just want to restructure to have a spelling/phonetic/furigana_template grouping
    per Spelling record, and then assign those meanings by attaching to them.
- Spelling types (katakana, hiragana, kanji, et cetera, aren't real useful beyond the preferred spelling.
  - If we retain them, we need to expand to handle more complex cases probably.