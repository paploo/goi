----
---- This files is the full definition of the goi database tables.
----

--
-- Put initializers like role creation and loading of optional features here
--

create extension if not exists "uuid-ossp";



--
-- VOCABULARY
--

create schema vocabulary;

--noun, verb, adjective, adverb, interjection, phrase
create table vocabulary.word_class
(
    code  varchar primary key,
    label text not null
);

--e.g. ru_verb, u_verb, i_adjective, irregular_verb
create table vocabulary.conjugation_kind
(
    code            varchar primary key,
    label           text    not null,
    word_class_code varchar not null references vocabulary.word_class (code)
);

--hiragana, katakana, kanji, latin
create table vocabulary.spelling_kind
(
    code  varchar primary key,
    label text not null
);

create table vocabulary.vocabulary
(
    id                    uuid primary key,

    word_class_code       varchar   not null references vocabulary.word_class (code),
    conjugation_kind_code varchar references vocabulary.conjugation_kind (code),

    jlpt_level            int                default null,
    row_num               int, -- Used to give predictable, controllable ordering for spreadsheets and CSVs; can be changed!
    date_added            date,
    tags                  varchar[] not null default '{}'
);
create index on vocabulary.vocabulary using gin (tags);

create table vocabulary.definition
(
    id            uuid primary key,
    vocabulary_id uuid not null references vocabulary.vocabulary (id),
    sort_rank     int  not null default 0,
    value         text not null
);
create index on vocabulary.definition (vocabulary_id);
create index on vocabulary.definition (value);

create table vocabulary.spelling
(
    id                 uuid primary key,
    vocabulary_id      uuid    not null references vocabulary.vocabulary (id),
    spelling_kind_code varchar not null references vocabulary.spelling_kind (code),
    value              text    not null
);
create index on vocabulary.spelling (vocabulary_id);
create index on vocabulary.spelling (value);
create index on vocabulary.spelling (spelling_kind_code, value);

create unique index on vocabulary.definition (vocabulary_id, id);
create unique index on vocabulary.spelling (vocabulary_id, id);
create table vocabulary.linkages
(
    vocabulary_id            uuid not null unique references vocabulary.vocabulary (id),

    preferred_definition_id  uuid not null,

    -- One spelling may be referenced in multiple links, wherever it is applicable.
    preferred_spelling_id    uuid not null,
    phonetic_spelling_id     uuid not null,     --techhically this is the kana_spelling, but this name expresses intent over encoding.
    alt_phonetic_spelling_id uuid default null, --This handles some cases like number kanji and 何.
    kanji_spelling_id        uuid default null,

    foreign key (vocabulary_id, preferred_definition_id) references vocabulary.definition (vocabulary_id, id),
    foreign key (vocabulary_id, preferred_spelling_id) references vocabulary.spelling (vocabulary_id, id),
    foreign key (vocabulary_id, phonetic_spelling_id) references vocabulary.spelling (vocabulary_id, id),
    foreign key (vocabulary_id, alt_phonetic_spelling_id) references vocabulary.spelling (vocabulary_id, id),
    foreign key (vocabulary_id, kanji_spelling_id) references vocabulary.spelling (vocabulary_id, id)
);



--
-- Sources
--

create schema source;

create table source.source
(
    code  varchar primary key,
    label text not null
);

create table source.lesson
(
    code              varchar primary key,
    lesson_code       varchar not null,
    source_code       varchar not null references source.source (code),
    label             text    not null,
    section_number    int,
    subsection_number int,
    check ( code = concat(source_code, '_', lesson_code) )
);
create unique index on source.lesson (source_code, lesson_code);
create index on source.lesson (source_code);
create index on source.lesson (section_number, subsection_number);

create table vocabulary.reference
(
    vocabulary_id uuid    not null references vocabulary.vocabulary (id),
    lesson_code   varchar not null references source.lesson (code)
);
create unique index on vocabulary.reference (vocabulary_id, lesson_code);
create index on vocabulary.reference (lesson_code);