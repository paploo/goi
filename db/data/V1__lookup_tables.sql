---
--- WORD CLASSES
---
INSERT INTO vocabulary.word_class (code, label) VALUES ('NOUN', 'Noun');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PROPER_NOUN', 'Proper Noun');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PRONOUN', 'Pronoun');
INSERT INTO vocabulary.word_class (code, label) VALUES ('ADJECTIVE', 'Adjective');
INSERT INTO vocabulary.word_class (code, label) VALUES ('VERB', 'Verb');
INSERT INTO vocabulary.word_class (code, label) VALUES ('ADVERB', 'Adverb');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PREPOSITION', 'Preposition');
INSERT INTO vocabulary.word_class (code, label) VALUES ('INTERJECTION', 'Interjection');
INSERT INTO vocabulary.word_class (code, label) VALUES ('CONJUNCTION', 'Conjunction');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PARTICLE', 'Particle');
INSERT INTO vocabulary.word_class (code, label) VALUES ('EXPRESSION', 'Expression');
INSERT INTO vocabulary.word_class (code, label) VALUES ('CONTRACTION', 'Contraction');

---
--- CONJUGATION KIND
---
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('IRREGULAR_VERB', 'Irregular Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('GODAN_VERB', 'Godan Verb / U-Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('ICHIDAN_VERB', 'Ichidan Verb / Ru-Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('IRREGULAR_ADJECTIVE', 'Irregular Adjective', 'ADJECTIVE');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('I_ADJECTIVE', 'I-Adjective', 'ADJECTIVE');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('NA_ADJECTIVE', 'Na-Adjective', 'ADJECTIVE');

---
--- SPELLING KIND
---
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('HIRAGANA', 'Hiragana');
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('KATAKANA', 'Katakana');
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('KANJI', 'Kanji');
