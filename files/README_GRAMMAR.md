### Furigana template rules for grammar examples

This document defines the ruby/furigana template style used in `日本語 Vocab - Grammar.json` so that humans and AI tools can validate existing entries and add new ones consistently.

#### Template syntax
- Wrap each annotated token in curly braces: `{...}`
- Inside, write `kanji-or-token | reading` with a single pipe separator:
  - Example: `{先生|せん・せい}`
- When supplying per‑kanji readings inside one block, separate readings with the middle dot `・`.
  - Use `・` only; never use `.` or spaces as separators.
- When the reading is for the entire word (group/whole‑word ruby), do not include `・`.
- Do not include spaces inside a block.

#### Core rules
1. One block per word/compound that needs furigana
   - Put the entire kanji word or compound inside one block.
   - Do not split a single word into multiple adjacent blocks unless okurigana forces it (see rule 4).

2. Per‑kanji vs. whole‑word readings
   - Per‑kanji: Use `・` to split readings when you can unambiguously map one reading to each kanji character. The number of reading segments must exactly equal the number of kanji in the block.
     - Example: `先生 → {先生|せん・せい}`、`日本 → {日本|に・ほん}`、`日曜日 → {日曜日|にち・よう・び}`
   - Whole‑word: If the reading is lexicalized or cannot be cleanly assigned per kanji, give one continuous reading with no `・`.
     - Example: `昨日 → {昨日|きのう}`、`今日 → {今日|きょう}`、`明日 → {明日|あした}`、`今年 → {今年|ことし}`

3. Kana used for readings
   - Use hiragana for readings by default.
   - Katakana words written in kanji (rare in this set) should still use hiragana readings unless there is a strong orthographic reason to prefer katakana (e.g., fixed loanword pronunciations). When in doubt, prefer hiragana.

4. Okurigana and mixed kanji‑kana words
   - Only the kanji portion goes inside the block; leave okurigana outside.
     - Example: `食べる → {食|た}べる`、`話して → {話|はな}して`、`開けて → {開|あ}けて`

5. Particles and kana‑only words
   - Generally do not annotate plain kana‑only particles or words.
   - Project‑specific teaching exceptions are allowed to clarify pronunciation with Latin letters for particles (e.g. `は`) in limited patterns:
     - `{は|wa}` in 〜てはいけません / 〜てはいけない examples.
     - `{は|ha}` immediately before `ず` in はず constructions.
   - Outside of these curated contexts, do not add furigana to particles.

6. Punctuation, HTML, and surrounding text
   - Do not include punctuation, spaces, or HTML tags inside a furigana block.
   - Keep tags like `<br/>`, `<u>…</u>`, etc., outside the `{…|…}` structure.

7. Consistency of separators
   - Always use the middle dot `・` between per‑kanji reading segments.
   - Do not use periods `.`, slashes `/`, or any other characters as separators.

#### Known project‑specific decisions and exceptions
- Whole‑word readings (no `・`):
  - `{明日|あした}`、`{今日|きょう}`、`{昨日|きのう}`、`{今年|ことし}`
  - `{一人|ひとり}`、`{二人|ふたり}`
- Per‑kanji readings (use `・`) even though lexicalized for learners in many contexts:
  - `{日本|に・ほん}`、`{子供|こ・ども}`、`{寿司|す・し}`
  - People counter from three and up: `{三人|さん・にん}`、`{四人|よ・にん}`、`{五人|ご・にん}` …
    - Rarely, the Sino‑Japanese reading may be intended even for one person in specialized contexts: `{一人|いち・にん}`. Default to `{一人|ひとり}` unless the context explicitly requires `いちにん`.
- Particle pronunciation annotations (Latin letters), used deliberately for pedagogy:
  - `{は|wa}` within 〜てはいけません / 〜てはいけない
  - `{は|ha}` within はず (…はずです / …はずがない / …はずがありません)

If you find additional words that should be treated as whole‑word readings (e.g., 大人, 一昨日, 明後日, 入口/出口, etc.),
prefer whole‑word unless there is a project decision to split per‑kanji. When in doubt, check a reputable dictionary and the examples already in this repository for precedence.

#### Validation checklist (for humans or linters)
- Each `{…|…}` block:
  - Has exactly one `|` and no spaces inside.
  - If the left side contains N kanji characters and a per‑kanji reading is used, there are exactly N reading segments separated by `・`.
  - Uses `・` (not `.`) as the segment separator.
  - Does not wrap punctuation or HTML tags.
  - Uses hiragana for readings, except the curated particle cases which use ASCII `wa` or `ha`.
- Whole‑word items listed above are not split; there is no `・` inside their readings.
- Particle annotations appear only in the curated contexts listed above.

#### Examples
```
{先生|せん・せい}は{日本|に・ほん}に{行|い}きます。
{昨日|きのう}は{日曜日|にち・よう・び}でした。
ここで{写真|しゃ・しん}を{撮|と}って{は|wa}いけません。
レポートの{締|し}め{切|き}りは{明日|あした}じゃない{は|ha}ずです。
{寿司|す・し}が{一番|いち・ばん}{美味|おい}しいと{思|おも}います。
{食|た}べすぎたので、{今年|ことし}は{野菜|や・さい}を{多|おお}く{食|た}べます。
{部長|ぶ・ちょう}は{一人|ひとり}で{全部|ぜん・ぶ}できませんでした。
{三人|さん・にん}で{行|い}きます。
```

#### Sources and rationale (short)
- JLREQ (W3C): prefer group ruby when per‑character assignment is not appropriate.
- Standard dictionaries (Daijisen/Kotobank, Weblio) list whole‑word readings for lexicalized items like 明日/今日/昨日/今年.
- This project also includes pedagogical exceptions (particles and a few lexicalized words) for learner clarity and internal consistency.

Last updated: 2025-11-16 17:18 (local)
