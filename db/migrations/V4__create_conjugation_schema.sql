---
--- Conjugations
---

-- Making a set allows adding extra properties later, and using the ID as a sync for Anki cards.
create table vocabulary.conjugation_set (
    id uuid primary key,
    vocabulary_id uuid not null references vocabulary.vocabulary(id)
);
create index on vocabulary.conjugation_set(vocabulary_id);
create unique index on vocabulary.conjugation_set(vocabulary_id, id);

alter table vocabulary.linkages add column conjugation_set_id uuid;
alter table vocabulary.linkages add foreign key (vocabulary_id, conjugation_set_id) references vocabulary.conjugation_set(vocabulary_id, id);

-- e.g.: PLAIN, POLITE
create table vocabulary.conjugation_politeness (
    code varchar primary key,
    label text not null,
    sort_rank int not null
);

-- e.g.: POSITIVE, NEGATIVE
create table vocabulary.conjugation_disposition (
    code  varchar primary key,
    label text not null,
    sort_rank int not null
);

-- e.g.: DICTIONARY, PAST, TE, CONDITIONAL_EBA, CONDITIONAL_TARA, POTENTIAL, PASSIVE, CAUSATIVE, IMPERATIVE
create table vocabulary.conjugation_form (
    code varchar primary key,
    label text not null,
    sort_rank int not null
);

-- Uniqueness is complex; can have multiple ordered entries (e.g. 食べられる and 食べれる are both positive casual potential)
create table vocabulary.conjugation (
    id uuid primary key,
    conjugation_set_id uuid not null references vocabulary.conjugation_set(id),
    politeness varchar not null references vocabulary.conjugation_politeness(code),
    disposition varchar not null references vocabulary.conjugation_disposition(code),
    form varchar not null references  vocabulary.conjugation_form(code),
    sort_rank int not null default 0,
    value text not null,

    unique (conjugation_set_id, politeness, disposition, form, sort_rank)
);
create index on vocabulary.conjugation(conjugation_set_id);
create index on vocabulary.conjugation(value);