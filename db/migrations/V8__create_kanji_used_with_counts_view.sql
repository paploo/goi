-- Add a view giving all the count of all charaters seen, by vocabulary_id and spelling_id
--
-- This can be used to futher generate aggregate counts, filtered/uniqued/grouped by various attributes of the objects,
-- e.g. only using preferred spellings.
--
-- Note that we do not order because (1) it nearly doubles the exec time, and (2) we are still too granular.
--
-- Example: getting the total counts of each character across all spellings:
-- select character, sum(count) as count from vocabulary.spelling_character_counts group by character order by count desc;
create view vocabulary.spelling_character_counts as
select vocabulary_id, id as spelling_id, unnest(regexp_split_to_array(value, '')) as character, count(*) as count
from vocabulary.spelling
group by vocabulary_id, spelling_id, character;


-- View giving only the kanji used in the vocabulary spellings, along with their frequency count as seen in the
-- vocabulary spelling table.
--
-- This counts all spellings, not just the preferred spellings; however, since this is just kanji, phonetic spellings
-- are inherently dropped, leaving only preferred and alternate spellings, which is the typical use case.
--
-- Results are pre-sorted; note that because of how null sorts, we have to use coalesce on the jlpt_level column;
-- this is unecessary on the other columns.
create view kanji.kanji_vocabulary_frequency as
with char_counts as (
    select spc.character, sum(spc.count) as count --note that one must sum the counts to get the correct count.
    from vocabulary.spelling_character_counts as spc
    join kanji.kanji_character as kanji on kanji.character = spc.character
    group by spc.character
)
select kanji_character.*, char_counts.count from kanji.kanji_character
join char_counts on char_counts.character = kanji_character.character
order by char_counts.count desc, coalesce(jlpt_level,0) desc, grade_level asc, frequency_ranking asc, stroke_count asc;