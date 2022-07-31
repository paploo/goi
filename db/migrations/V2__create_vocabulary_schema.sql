--
-- VOCABULARY
--
-- Vocabulary comes in various forms, such as nounds, verbs (with conjugation),
-- and phrases. Additionally, vocabulary can be used in any combination of
-- hirigana, katakana, and kanji (or for phrases, a mixture).
--
-- Thus we establish the following:
-- 1. For every vocabulary, we have a core record.
-- 2. We have multiple forms attached via auxiliary records.
-- 3. Each vocabulary word has a selected preferred form.
-- 

create schema vocabulary;

--noun, verb, adjective, adverb, interjection, phrase
create table vocabulary.word_class (
    code varchar not null primary key,
    label varchar not null
);

--e.g. ru_verb, u_verb, i_adjective, irregular_verb
create table vocabulary.conjugation_kind (
    code varchar not null primary key,
    label varchar not null,
    word_class_code varchar not null references vocabulary.word_class(code)
);

--en_EN or jn_JP
create table vocabulary.language (
    code varchar not null primary key,
    label varchar not null
);

--hirigana, katakana, kanji, latin, mixed
create table vocabulary.form_kind (
    code varchar not null primary key,
    label varchar not null
);

create table vocabulary.vocabulary (
    id uuid not null primary key,

    word_class_code varchar not null references vocabulary.word_class(code),
    conjugation_kind_code varchar not null references vocabulary.conjugation_kind(code),

    jlpt_level int,
    tags varchar[] not null default '{}'
);
create index on vocabulary.vocabulary using gin (tags);

create table vocabulary.forms (
    id uuid not null primary key,
    vocabulary_id uuid not null references vocabulary.vocabulary(id),

    kind varchar not null references vocabulary.form_kind(code),
    language_code varchar not null references vocabulary.language(code),

    value text not null
);
create unique index on vocabulary.forms(vocabulary_id);
create index on vocabulary.forms(value);
create index on vocabulary.forms (language_code, value);

-- TODO: Consider alternate:
-- 1. forms don't have a reference to vocabulary_id
-- 2. vocabulary has the preferred ID linkages on it.
-- 3. alternate forms use postgres array, which allows for ordering.
--
-- Downsides:
-- - Searching forms doesn't give me the related vocabulary without complex joins
-- - No foreign key constraints.
-- - Possible many-to-many form linkage.
-- Upsides:
-- - Less tables to do basic operations.
-- - Easier to quickly grab the right forms because less joining.
-- - Ensures key forms is defined.
create table vocabulary.key_forms (
    id uuid not null primary key,
    vocabulary_id uuid not null unique references vocabulary.vocabulary(id),

    english_form_id uuid not null references vocabulary.forms(id),
    japanese_preferred_form_id uuid not null references vocabulary.forms(id),
    japanese_phonetic_form_id uuid not null references vocabulary.forms(id)
);
create index on vocabulary.key_forms(vocabulary_id);
