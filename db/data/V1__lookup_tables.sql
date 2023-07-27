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
INSERT INTO vocabulary.word_class (code, label) VALUES ('COUNTER', 'Counter');
INSERT INTO vocabulary.word_class (code, label) VALUES ('SUFFIX', 'Suffix');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PRE_NOUN_ADJECTIVAL', 'Pre-Noun Adjectival');
INSERT INTO vocabulary.word_class (code, label) VALUES ('AUXILIARY', 'Auxiliary');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PREFIX', 'Prefix');
INSERT INTO vocabulary.word_class (code, label) VALUES ('PUNCTUATION', 'Punctuation');

---
--- CONJUGATION KIND
---
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('IRREGULAR_VERB', 'Irregular Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('GODAN_VERB', 'Godan Verb / U-Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('ICHIDAN_VERB', 'Ichidan Verb / Ru-Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('SURU_VERB', 'Suru Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('KURU_VERB', 'Kuru Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('IKU_VERB', 'Iku Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('ARU_VERB', 'Aru Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('AI_SURU_VERB', 'Ai Suru Verb', 'VERB');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('IRREGULAR_ADJECTIVE', 'Irregular Adjective', 'ADJECTIVE');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('I_ADJECTIVE', 'I-Adjective', 'ADJECTIVE');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('YOI_ADJECTIVE', 'I-Adjective, yoi/ii Class', 'ADJECTIVE');
INSERT INTO vocabulary.conjugation_kind (code, label, word_class_code) VALUES ('NA_ADJECTIVE', 'Na-Adjective', 'ADJECTIVE');

---
--- SPELLING KIND
---
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('HIRAGANA', 'Hiragana');
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('KATAKANA', 'Katakana');
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('KANJI', 'Kanji');
INSERT INTO vocabulary.spelling_kind (code, label) VALUES ('PUNCTUATION', 'Punctuation');

---
--- CONJUGATION CHARGE
---
INSERT INTO vocabulary.conjugation_charge (code, label, sort_rank) VALUES ('POSITIVE', 'Positive', 1);
INSERT INTO vocabulary.conjugation_charge (code, label, sort_rank) VALUES ('NEGATIVE', 'Negative', 2);

---
--- CONJUGATION POLITENESS
---
INSERT INTO vocabulary.conjugation_politeness (code, label, sort_rank) VALUES ('PLAIN', 'Plain/Dictionary', 1);
INSERT INTO vocabulary.conjugation_politeness (code, label, sort_rank) VALUES ('POLITE', 'Ploite/Masu', 2);

---
--- CONJUGATION FORM
---
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('PRESENT', 'Present', 1);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('PAST', 'Past', 2);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('TE', '-te', 3);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('CONDITIONAL_EBA', '-eba Conditional', 4);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('CONDITIONAL_TARA', '-tara Conditional', 5);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('POTENTIAL', 'Potential', 6);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('PASSIVE', 'Passive', 7);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('CAUSATIVE', 'Causative', 8);
INSERT INTO vocabulary.conjugation_form (code, label, sort_rank) VALUES ('IMPERATIVE', 'Imperative', 9);
