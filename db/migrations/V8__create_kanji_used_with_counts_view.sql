create view kanji.kanji_vocabulary_frequency as
with char_counts as (
    select spc.character, count(spc.character) as count
    from vocabulary.spelling_characters as spc
    join kanji.kanji_character as kanji on kanji.character = spc.character
    group by spc.character
)
select kanji_character.*, char_counts.count from kanji.kanji_character
join char_counts on char_counts.character = kanji_character.character
order by char_counts.count desc, coalesce(jlpt_level,0) desc, grade_level asc, frequency_ranking asc, stroke_count asc;