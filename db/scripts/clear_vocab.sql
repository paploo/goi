begin;
delete from vocabulary.linkages;
delete from vocabulary.reference;
delete from vocabulary.definition;
delete from vocabulary.spelling;
delete from vocabulary.vocabulary;
delete from vocabulary.conjugation;
delete from vocabulary.conjugation_set;
commit;

--rollback;
