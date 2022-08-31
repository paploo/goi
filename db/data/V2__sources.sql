---
--- SOURCES AND LESSONS
---

--
-- Genki
--

insert into source.source (code, label) values ('GENKI3', 'Genki, 3rd Ed');

insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L00', 'L00', 'GENKI3', 'Greetings and Numbers', 0, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L01', 'L01', 'GENKI3', 'New Friends', 1, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L02', 'L02', 'GENKI3', 'Shopping', 2, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L03', 'L03', 'GENKI3', 'Making a Date', 3, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L04', 'L04', 'GENKI3', 'The First Date', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L05', 'L05', 'GENKI3', 'A Trip to Okinawa', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L06', 'L06', 'GENKI3', 'A Day in Robert''s Life', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L07', 'L07', 'GENKI3', 'Family Picture', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L08', 'L08', 'GENKI3', 'Barbecue', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L09', 'L09', 'GENKI3', 'Kabuki', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L10', 'L10', 'GENKI3', 'Winter Vacation Plans', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L11', 'L11', 'GENKI3', 'After the Vacation', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('GENKI3_L12', 'L12', 'GENKI3', 'Feeling Ill', 4, null);

--
-- Duolingo
--

insert into source.source (code, label) values ('DUO', 'Duolingo');

-- Starting 2022-08-30, they redid the way lessons are divided, into 90 smaller units, seemingly each with what would've been two lessons before.
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U01', 'U01', 'DUO', 'Use hiragana', 1, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U02', 'U02', 'DUO', 'Use hiragana', 2, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U03', 'U03', 'DUO', 'Say hello and goodbye, use katakana', 3, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U04', 'U04', 'DUO', 'Introduce yourself, use katakana', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U05', 'U05', 'DUO', 'Get to know people, use katakana', 5, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U06', 'U06', 'DUO', 'Order food, ask for the time', 6, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U07', 'U07', 'DUO', 'Talk about your day, describe', 7, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U08', 'U08', 'DUO', 'Ask people about themselves', 8, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U09', 'U09', 'DUO', 'Describe your family, ask about food', 9, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U10', 'U10', 'DUO', 'Talk about your schedule', 10, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U11', 'U11', 'DUO', 'Say where people are from, talk about hobbies', 11, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U12', 'U12', 'DUO', 'Describe extended family', 12, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U13', 'U13', 'DUO', 'Talk about clothes, describe your hobbies', 13, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U14', 'U14', 'DUO', 'Discuss weather, talk about food and drink', 14, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U15', 'U15', 'DUO', 'Get directions, discuss food you like', 15, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U16', 'U16', 'DUO', 'Express days and times, shop for common items', 16, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U17', 'U17', 'DUO', 'Discuss relationships, make requests', 17, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number)  values ('DUO_U18', 'U18', 'DUO', 'Describe nature, talk about school', 18, null);


-- We keep the legacy entries as follows, and have now annotated which go into which unit.
-- Unit 1
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_01', '01_01', 'DUO', 'Hiragana 1', 1, 1);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_02', '01_02', 'DUO', 'Hiragana 2', 1, 2);
-- Unit 2
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_03', '01_03', 'DUO', 'Hiragana 3', 1, 3);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_04', '01_04', 'DUO', 'Hiragana 4', 1, 4);
-- Unit 3
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_05', '01_05', 'DUO', 'Greetings', 1, 5);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_06', '01_06', 'DUO', 'Katakana 1', 1, 6);
-- Unit 4
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_07', '01_07', 'DUO', 'Intro 1', 1, 7);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_08', '01_08', 'DUO', 'Katakana 2', 1, 8);
-- Unit 5
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_09', '01_09', 'DUO', 'Intro 2', 1, 9);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_01_10', '01_10', 'DUO', 'Katakana 3', 1, 10);

-- Unit 6
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_01', '02_01', 'DUO', 'Food 1', 2, 1);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_02', '02_02', 'DUO', 'Time', 2, 2);
-- Unit 7
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_03', '02_03', 'DUO', 'Routines', 2, 3);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_04', '02_04', 'DUO', 'Home 1', 2, 4);
-- Unit 8
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_05', '02_05', 'DUO', 'Intro 3', 2, 5);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_06', '02_06', 'DUO', 'Counting', 2, 6);
-- Unit 9
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_07', '02_07', 'DUO', 'Family 1', 2, 7);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_08', '02_08', 'DUO', 'Restaurant', 2, 8);
-- Unit 10
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_09', '02_09', 'DUO', 'Activity 1', 2, 9);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_10', '02_10', 'DUO', 'Position', 2, 10);
-- Unit 11
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_11', '02_11', 'DUO', 'Vacation 1', 2, 11);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_12', '02_12', 'DUO', 'Hobby 1', 2, 12);
-- Unit 12
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_13', '02_13', 'DUO', 'Family 2', 2, 13);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_14', '02_14', 'DUO', 'Transit 1', 2, 14);
-- Unit 13
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_15', '02_15', 'DUO', 'Clothes 1', 2, 15);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_16', '02_16', 'DUO', 'Hobby 2', 2, 16);
-- Unit 14
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_17', '02_17', 'DUO', 'Weather 1', 2, 17);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_18', '02_18', 'DUO', 'Food 2', 2, 18);
-- Unit 15
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_19', '02_19', 'DUO', 'Direct. 1', 2, 19);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_20', '02_20', 'DUO', 'Food 3', 2, 20);
-- Unit 16
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_21', '02_21', 'DUO', 'Dates', 2, 21);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_22', '02_22', 'DUO', 'Shopping 1', 2, 22);
-- Unit 17
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_23', '02_23', 'DUO', 'People 1', 2, 23);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_24', '02_24', 'DUO', 'Activity 2', 2, 24);
-- Unit 18
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_25', '02_25', 'DUO', 'Nature 1', 2, 25);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('DUO_02_26', '02_26', 'DUO', 'Classroom', 2, 26);

--
-- Lulilanguages Apps
--

insert into source.source (code, label) values ('LULI_JP', 'Lulilanguages Japanese!');

insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_JP_L03', 'L03', 'LULI_JP', 'Basics', 3, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_JP_L04', 'L04', 'LULI_JP', 'Verbs', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_JP_L05', 'L05', 'LULI_JP', 'Questions', 5, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_JP_L06', 'L06', 'LULI_JP', 'Adjectives', 6, null);

insert into source.source (code, label) values ('LULI_KANJI', 'Lulilanguages Kanji');

insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_KANJI_L01', 'L01', 'LULI_KANJI', 'JLPT N5', 1, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_KANJI_L02', 'L02', 'LULI_KANJI', 'JLPT N4', 2, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_KANJI_L03', 'L03', 'LULI_KANJI', 'JLPT N3', 3, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_KANJI_L04', 'L04', 'LULI_KANJI', 'JLPT N2', 4, null);
insert into source.lesson (code, lesson_code, source_code, label, section_number, subsection_number) values ('LULI_KANJI_L05', 'L05', 'LULI_KANJI', 'JLPT N1', 5, null);
