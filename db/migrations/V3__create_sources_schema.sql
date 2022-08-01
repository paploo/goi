--
-- Sources
--

create schema source;

create table source.source (
    code varchar primary key,
    label text not null
);

create table source.lesson (
    code varchar primary key,
    lesson_code varchar not null,
    source_code varchar not null references source.source(code),
    label text not null,
    section_number int,
    subsection_number int,
    check ( code = concat(source_code, '_', lesson_code) )
);
create unique index on source.lesson(source_code, lesson_code);
create index on source.lesson(source_code);
create index on source.lesson(section_number, subsection_number);

create table vocabulary.reference (
    vocabulary_id uuid not null references vocabulary.vocabulary(id),
    lesson_code varchar not null references source.lesson(code)
);
create unique index on vocabulary.reference(vocabulary_id, lesson_code);
create index on vocabulary.reference(lesson_code);
