--
-- Google Sheets SQL
--
-- This gets the data according to what is needed to export to google sheets.
--
-- Typically, I run this against the DB in DataGrip and export as CSV, including headers.
--
-- TODO: Once the kanji and alt_phonetic links are fixed in the data, switch to joining on those
-- TODO: Make be a view to make this trivial
--

-- Goodgle Sheets Export View
select definition.value as definition, pref_spell.value as preferred_spelling, phon_spell.value as phonetic_spelling, alt_phon_spell.value as alt_phon_spell, case when (kanji_spell.value is not null) then kanji_spell.value when (pref_spell.spelling_kind_code = 'KANJI') then pref_spell.value end as kanji_spelling, vocab.word_class_code, vocab.conjugation_kind_code, vocab.jlpt_level, refs.lesson_codes, vocab.tags, vocab.id, vocab.row_num, vocab.date_added
from vocabulary.vocabulary as vocab
left join vocabulary.linkages as link on vocab.id = link.vocabulary_id
left join vocabulary.definition as definition on link.preferred_definition_id = definition.id
left join vocabulary.spelling as pref_spell on link.preferred_spelling_id = pref_spell.id
left join vocabulary.spelling as phon_spell on link.phonetic_spelling_id = phon_spell.id
left join (
    select vocabulary_id, array_agg(lesson_code) as lesson_codes
    from vocabulary.reference
    group by vocabulary_id
) as refs on refs.vocabulary_id = vocab.id
left join (
    select sp.*
    from vocabulary.spelling as sp
    left join vocabulary.linkages as links on links.vocabulary_id = sp.vocabulary_id
    where sp.id <> links.preferred_spelling_id AND sp.id <> links.phonetic_spelling_id
        AND (sp.spelling_kind_code = 'HIRAGANA' OR sp.spelling_kind_code = 'KATAKANA')
) as alt_phon_spell on alt_phon_spell.vocabulary_id = vocab.id
left join (
    select sp.*
    from vocabulary.spelling as sp
    left join vocabulary.linkages as links on links.vocabulary_id = sp.vocabulary_id
    where sp.id <> links.preferred_spelling_id AND sp.id <> links.phonetic_spelling_id
        AND sp.spelling_kind_code = 'KANJI'
) as kanji_spell on kanji_spell.vocabulary_id = vocab.id
ORDER BY vocab.row_num asc;
