--
-- Grammar
--

create schema grammar;

create table grammar.rule (
    id uuid primary key,
    meaning text not null,
    title_preferred_spelling text not null,
    title_phonetic_spelling text default null,
    title_furigana_template text default null,
    how_to_use text[],
    jlpt_level int default null,
    tags varchar[] default '{}'
);

create table grammar.example (
    id uuid primary key,
    rule_id uuid not null references grammar.rule(id) on delete cascade,
    meaning text not null,
    text_preferred_spelling text not null,
    text_phonetic_spelling text,
    text_furigana_template text,
    sort_rank int default null,
    tags varchar[] default '{}'
);
create index on grammar.example(rule_id);

create table grammar.rule_reference (
    rule_id uuid not null references grammar.rule(id) on delete cascade,
    lesson_code varchar not null references source.lesson(code)
);
create unique index on grammar.rule_reference(rule_id, lesson_code);
create index on grammar.rule_reference(lesson_code);

create table grammar.example_reference (
    rule_id uuid not null references grammar.example(id) on delete cascade,
    lesson_code varchar not null references source.lesson(code)
);
create unique index on grammar.example_reference(rule_id, lesson_code);
create index on grammar.example_reference(lesson_code);
