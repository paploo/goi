--
-- Create Vocabulary Views
--
-- These are convencience views for working with common join patterns.

-- All but the conjugation columns, arranged mostly like the google sheet
create view vocabulary.vocabulary_summary as
select vocab.id,
       pref_def.value as preferrred_definition_value,
       pref_spell.value as preferred_spelling_value,
       phon_spell.value as phonetic_spelling_value,
       alt_phon_spell.value as alt_phonetic_spelling_value,
       kanji_spell.value as kanji_spelling_value,
       vocab.word_class_code,
       vocab.conjugation_kind_code,
       conj_set.id as conjugation_set_id,
       vocab.jlpt_level,
       ref.lesson_codes,
       vocab.tags,
       vocab.row_num,
       vocab.date_added
from vocabulary.vocabulary as vocab
         join vocabulary.linkages as link on link.vocabulary_id = vocab.id
         left join vocabulary.definition as pref_def on pref_def.id = link.preferred_definition_id
         left join vocabulary.spelling as pref_spell on pref_spell.id = link.preferred_spelling_id
         left join vocabulary.spelling as phon_spell on phon_spell.id = link.phonetic_spelling_id
         left join vocabulary.spelling as alt_phon_spell on alt_phon_spell.id = link.alt_phonetic_spelling_id
         left join vocabulary.spelling as kanji_spell on kanji_spell.id = link.kanji_spelling_id
         left join vocabulary.conjugation_set as conj_set on conj_set.id = link.conjugation_set_id
         left join (select vocabulary_id, array_agg(lesson_code) as lesson_codes from vocabulary.reference group by vocabulary_id) as ref on ref.vocabulary_id = vocab.id;

-- Preferred Conjugations: All conjugations with the lowest sort_rank for easier joins.
create view vocabulary.preferred_conjugation as
select distinct on (conjugation_set_id, politeness_code, charge_code, form_code)
    conjugation_set_id,
    politeness_code,
    charge_code,
    form_code,
    value
from vocabulary.conjugation
order by conjugation_set_id, politeness_code, charge_code, form_code, sort_rank asc;

-- Conjugations Summary: A flat table of all conjugations.
--
-- I ended up generating this out of a script.
-- It's crazy but performs well with the small number of records present.
-- Could switch to a materialized view to only compute once per vocab load.
create view vocabulary.flattened_conjugation as
with conjugations as (
select distinct on (conjugation_set_id, politeness_code, charge_code, form_code)
    conjugation_set_id,
    politeness_code,
    charge_code,
    form_code,
    value
from vocabulary.conjugation
order by conjugation_set_id, politeness_code, charge_code, form_code, sort_rank asc
)
select positive_plain_present.conjugation_set_id as conjugation_set_id,
       positive_plain_present.value as positive_plain_present,
       positive_plain_past.value as positive_plain_past,
       positive_plain_te.value as positive_plain_te,
       positive_plain_conditional_eba.value as positive_plain_conditional_eba,
       positive_plain_conditional_tara.value as positive_plain_conditional_tara,
       positive_plain_potential.value as positive_plain_potential,
       positive_plain_passive.value as positive_plain_passive,
       positive_plain_causative.value as positive_plain_causative,
       positive_plain_imperative.value as positive_plain_imperative,
       positive_polite_present.value as positive_polite_present,
       positive_polite_past.value as positive_polite_past,
       positive_polite_te.value as positive_polite_te,
       positive_polite_conditional_eba.value as positive_polite_conditional_eba,
       positive_polite_conditional_tara.value as positive_polite_conditional_tara,
       positive_polite_potential.value as positive_polite_potential,
       positive_polite_passive.value as positive_polite_passive,
       positive_polite_causative.value as positive_polite_causative,
       positive_polite_imperative.value as positive_polite_imperative,
       negative_plain_present.value as negative_plain_present,
       negative_plain_past.value as negative_plain_past,
       negative_plain_te.value as negative_plain_te,
       negative_plain_conditional_eba.value as negative_plain_conditional_eba,
       negative_plain_conditional_tara.value as negative_plain_conditional_tara,
       negative_plain_potential.value as negative_plain_potential,
       negative_plain_passive.value as negative_plain_passive,
       negative_plain_causative.value as negative_plain_causative,
       negative_plain_imperative.value as negative_plain_imperative,
       negative_polite_present.value as negative_polite_present,
       negative_polite_past.value as negative_polite_past,
       negative_polite_te.value as negative_polite_te,
       negative_polite_conditional_eba.value as negative_polite_conditional_eba,
       negative_polite_conditional_tara.value as negative_polite_conditional_tara,
       negative_polite_potential.value as negative_polite_potential,
       negative_polite_passive.value as negative_polite_passive,
       negative_polite_causative.value as negative_polite_causative,
       negative_polite_imperative.value as negative_polite_imperative
from conjugations as positive_plain_present
    left join conjugations as positive_plain_past on positive_plain_past.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_past.charge_code = 'POSITIVE' and positive_plain_past.politeness_code = 'PLAIN' and positive_plain_past.form_code = 'PAST'
    left join conjugations as positive_plain_te on positive_plain_te.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_te.charge_code = 'POSITIVE' and positive_plain_te.politeness_code = 'PLAIN' and positive_plain_te.form_code = 'TE'
    left join conjugations as positive_plain_conditional_eba on positive_plain_conditional_eba.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_conditional_eba.charge_code = 'POSITIVE' and positive_plain_conditional_eba.politeness_code = 'PLAIN' and positive_plain_conditional_eba.form_code = 'CONDITIONAL_EBA'
    left join conjugations as positive_plain_conditional_tara on positive_plain_conditional_tara.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_conditional_tara.charge_code = 'POSITIVE' and positive_plain_conditional_tara.politeness_code = 'PLAIN' and positive_plain_conditional_tara.form_code = 'CONDITIONAL_TARA'
    left join conjugations as positive_plain_potential on positive_plain_potential.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_potential.charge_code = 'POSITIVE' and positive_plain_potential.politeness_code = 'PLAIN' and positive_plain_potential.form_code = 'POTENTIAL'
    left join conjugations as positive_plain_passive on positive_plain_passive.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_passive.charge_code = 'POSITIVE' and positive_plain_passive.politeness_code = 'PLAIN' and positive_plain_passive.form_code = 'PASSIVE'
    left join conjugations as positive_plain_causative on positive_plain_causative.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_causative.charge_code = 'POSITIVE' and positive_plain_causative.politeness_code = 'PLAIN' and positive_plain_causative.form_code = 'CAUSATIVE'
    left join conjugations as positive_plain_imperative on positive_plain_imperative.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_plain_imperative.charge_code = 'POSITIVE' and positive_plain_imperative.politeness_code = 'PLAIN' and positive_plain_imperative.form_code = 'IMPERATIVE'
    left join conjugations as positive_polite_present on positive_polite_present.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_present.charge_code = 'POSITIVE' and positive_polite_present.politeness_code = 'POLITE' and positive_polite_present.form_code = 'PRESENT'
    left join conjugations as positive_polite_past on positive_polite_past.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_past.charge_code = 'POSITIVE' and positive_polite_past.politeness_code = 'POLITE' and positive_polite_past.form_code = 'PAST'
    left join conjugations as positive_polite_te on positive_polite_te.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_te.charge_code = 'POSITIVE' and positive_polite_te.politeness_code = 'POLITE' and positive_polite_te.form_code = 'TE'
    left join conjugations as positive_polite_conditional_eba on positive_polite_conditional_eba.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_conditional_eba.charge_code = 'POSITIVE' and positive_polite_conditional_eba.politeness_code = 'POLITE' and positive_polite_conditional_eba.form_code = 'CONDITIONAL_EBA'
    left join conjugations as positive_polite_conditional_tara on positive_polite_conditional_tara.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_conditional_tara.charge_code = 'POSITIVE' and positive_polite_conditional_tara.politeness_code = 'POLITE' and positive_polite_conditional_tara.form_code = 'CONDITIONAL_TARA'
    left join conjugations as positive_polite_potential on positive_polite_potential.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_potential.charge_code = 'POSITIVE' and positive_polite_potential.politeness_code = 'POLITE' and positive_polite_potential.form_code = 'POTENTIAL'
    left join conjugations as positive_polite_passive on positive_polite_passive.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_passive.charge_code = 'POSITIVE' and positive_polite_passive.politeness_code = 'POLITE' and positive_polite_passive.form_code = 'PASSIVE'
    left join conjugations as positive_polite_causative on positive_polite_causative.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_causative.charge_code = 'POSITIVE' and positive_polite_causative.politeness_code = 'POLITE' and positive_polite_causative.form_code = 'CAUSATIVE'
    left join conjugations as positive_polite_imperative on positive_polite_imperative.conjugation_set_id = positive_plain_present.conjugation_set_id and positive_polite_imperative.charge_code = 'POSITIVE' and positive_polite_imperative.politeness_code = 'POLITE' and positive_polite_imperative.form_code = 'IMPERATIVE'
    left join conjugations as negative_plain_present on negative_plain_present.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_present.charge_code = 'NEGATIVE' and negative_plain_present.politeness_code = 'PLAIN' and negative_plain_present.form_code = 'PRESENT'
    left join conjugations as negative_plain_past on negative_plain_past.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_past.charge_code = 'NEGATIVE' and negative_plain_past.politeness_code = 'PLAIN' and negative_plain_past.form_code = 'PAST'
    left join conjugations as negative_plain_te on negative_plain_te.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_te.charge_code = 'NEGATIVE' and negative_plain_te.politeness_code = 'PLAIN' and negative_plain_te.form_code = 'TE'
    left join conjugations as negative_plain_conditional_eba on negative_plain_conditional_eba.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_conditional_eba.charge_code = 'NEGATIVE' and negative_plain_conditional_eba.politeness_code = 'PLAIN' and negative_plain_conditional_eba.form_code = 'CONDITIONAL_EBA'
    left join conjugations as negative_plain_conditional_tara on negative_plain_conditional_tara.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_conditional_tara.charge_code = 'NEGATIVE' and negative_plain_conditional_tara.politeness_code = 'PLAIN' and negative_plain_conditional_tara.form_code = 'CONDITIONAL_TARA'
    left join conjugations as negative_plain_potential on negative_plain_potential.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_potential.charge_code = 'NEGATIVE' and negative_plain_potential.politeness_code = 'PLAIN' and negative_plain_potential.form_code = 'POTENTIAL'
    left join conjugations as negative_plain_passive on negative_plain_passive.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_passive.charge_code = 'NEGATIVE' and negative_plain_passive.politeness_code = 'PLAIN' and negative_plain_passive.form_code = 'PASSIVE'
    left join conjugations as negative_plain_causative on negative_plain_causative.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_causative.charge_code = 'NEGATIVE' and negative_plain_causative.politeness_code = 'PLAIN' and negative_plain_causative.form_code = 'CAUSATIVE'
    left join conjugations as negative_plain_imperative on negative_plain_imperative.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_plain_imperative.charge_code = 'NEGATIVE' and negative_plain_imperative.politeness_code = 'PLAIN' and negative_plain_imperative.form_code = 'IMPERATIVE'
    left join conjugations as negative_polite_present on negative_polite_present.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_present.charge_code = 'NEGATIVE' and negative_polite_present.politeness_code = 'POLITE' and negative_polite_present.form_code = 'PRESENT'
    left join conjugations as negative_polite_past on negative_polite_past.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_past.charge_code = 'NEGATIVE' and negative_polite_past.politeness_code = 'POLITE' and negative_polite_past.form_code = 'PAST'
    left join conjugations as negative_polite_te on negative_polite_te.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_te.charge_code = 'NEGATIVE' and negative_polite_te.politeness_code = 'POLITE' and negative_polite_te.form_code = 'TE'
    left join conjugations as negative_polite_conditional_eba on negative_polite_conditional_eba.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_conditional_eba.charge_code = 'NEGATIVE' and negative_polite_conditional_eba.politeness_code = 'POLITE' and negative_polite_conditional_eba.form_code = 'CONDITIONAL_EBA'
    left join conjugations as negative_polite_conditional_tara on negative_polite_conditional_tara.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_conditional_tara.charge_code = 'NEGATIVE' and negative_polite_conditional_tara.politeness_code = 'POLITE' and negative_polite_conditional_tara.form_code = 'CONDITIONAL_TARA'
    left join conjugations as negative_polite_potential on negative_polite_potential.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_potential.charge_code = 'NEGATIVE' and negative_polite_potential.politeness_code = 'POLITE' and negative_polite_potential.form_code = 'POTENTIAL'
    left join conjugations as negative_polite_passive on negative_polite_passive.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_passive.charge_code = 'NEGATIVE' and negative_polite_passive.politeness_code = 'POLITE' and negative_polite_passive.form_code = 'PASSIVE'
    left join conjugations as negative_polite_causative on negative_polite_causative.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_causative.charge_code = 'NEGATIVE' and negative_polite_causative.politeness_code = 'POLITE' and negative_polite_causative.form_code = 'CAUSATIVE'
    left join conjugations as negative_polite_imperative on negative_polite_imperative.conjugation_set_id = positive_plain_present.conjugation_set_id and negative_polite_imperative.charge_code = 'NEGATIVE' and negative_polite_imperative.politeness_code = 'POLITE' and negative_polite_imperative.form_code = 'IMPERATIVE'
where positive_plain_present.charge_code = 'POSITIVE' and positive_plain_present.politeness_code = 'PLAIN' and positive_plain_present.form_code = 'PRESENT';
