---
--- Kanji
---

-- Create schema kanji
create schema kanji;

-- Create the kanji_character table
--
-- Note that the unicode code point is in decimal int; postgres gives the most elegant tooling for this encoding, for example:
-- select unicode_code_point, to_hex(unicode_code_point), chr(unicode_code_point) from kanji.kanji_character
create table kanji.kanji_character (
    id uuid primary key,
    character char(1) not null,
    unicode_code_point int not null,
    meanings text[] not null,
    on_readings text[] not null default '{}',
    kun_readings text[] not null default '{}',
    nanori_readings text[] not null default '{}',
    stroke_count int not null,
    jlpt_level int,
    grade_level int,
    frequency_ranking int
);
create index on kanji.kanji_character(character);
create index on kanji.kanji_character using gin(meanings);
create index on kanji.kanji_character using gin(on_readings);
create index on kanji.kanji_character using gin(kun_readings);

-- Create convenience views

-- These allow using like/ilike to substring search.

create view kanji.meaning as select id as kanji_character_id, character, unnest(meanings) as meaning from kanji.kanji_character;
create view kanji.kun_reading as select id as kanji_character_id, character, unnest(kun_readings) as reading from kanji.kanji_character;
create view kanji.on_reading as select id as kanji_character_id, character, unnest(on_readings) as reading from kanji.kanji_character;

--
-- Create the character cross-ref tables
--

-- It turns out that it's so cheap to run the query on the size of data we have, that building a view works fine for now.
-- If this ever becomes a perf burden, then we can maintain a materialized view with indices, which will be simpler than coding a table.
create view vocabulary.vocabulary_characters as select distinct on (vocabulary_id, character) vocabulary_id, unnest(regexp_split_to_array(value, '')) as character from vocabulary.spelling;

-- Unlike vocabulary.characters which is vocab focused, this one includes the spelling_id so that filters/joins to spellings can occur.
create view vocabulary.spelling_characters as select distinct on (vocabulary_id, spelling_id, character) vocabulary_id, id as spelling_id, unnest(regexp_split_to_array(value, '')) as character from vocabulary.spelling;