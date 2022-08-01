--
-- VOCAB
--

-- sample
insert into vocabulary.vocabulary (id, word_class_code, conjugation_kind_code, jlpt_level, tags) values ('6de86986-05ed-58da-a055-9d49c89f7a06', 'NOUN', null, 5, '{school}');
insert into vocabulary.definition (id, vocabulary_id, rank, value) values ('7201c1f4-ded5-55b4-ad1b-008280ff7168', '6de86986-05ed-58da-a055-9d49c89f7a06', 1, 'student');
insert into vocabulary.spelling (id, vocabulary_id, spelling_kind_code, value) values ('8676a568-fd0e-5b49-9793-0b45461c1e96', '6de86986-05ed-58da-a055-9d49c89f7a06', 'KANJI', '学生');
insert into vocabulary.spelling (id, vocabulary_id, spelling_kind_code, value) values ('4321972b-78b3-5592-bdcc-1fe1567b90ce', '6de86986-05ed-58da-a055-9d49c89f7a06', 'HIRAGANA', 'がくせい');
insert into vocabulary.linkages (vocabulary_id, preferred_definition, preferred_spelling, phonetic_spelling) values ('6de86986-05ed-58da-a055-9d49c89f7a06', '7201c1f4-ded5-55b4-ad1b-008280ff7168', '8676a568-fd0e-5b49-9793-0b45461c1e96', '4321972b-78b3-5592-bdcc-1fe1567b90ce');