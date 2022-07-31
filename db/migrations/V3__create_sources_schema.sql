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
    source_code varchar not null references source.source(code),
    label text not null,
    section_number int,
    subsection_number int
);
create index on source.lesson(source_code);
create index on source.lesson(section_number, subsection_number);

create table vocabulary.linked_lesson (
    vocabulary_id uuid not null references vocabulary.vocabulary(id),
    lesson_code varchar not null references source.lesson(code)
);
create unique index on vocabulary.linked_lesson(vocabulary_id, lesson_code);
create index on vocabulary.linked_lesson(lesson_code);
