begin;
delete from vocabulary.linkages;
delete from vocabulary.reference;
delete from vocabulary.definition;
delete from vocabulary.spelling;
delete from vocabulary.vocabulary;
commit;

--rollback;