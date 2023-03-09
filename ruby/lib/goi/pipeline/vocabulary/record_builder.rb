# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary

      class RecordBuilder

        def build_record_group(linkages)
          {
            vocabulary: build_vocabulary_record(linkages.vocabulary),
            preferred_definition: build_definition_record(linkages.preferred_definition),
            preferred_spelling: build_spelling_record(linkages.preferred_spelling),
            phonetic_spelling: build_spelling_record(linkages.phonetic_spelling),
            alt_phonetic_spelling: build_spelling_record(linkages.alt_phonetic_spelling),
            kanji_spelling: build_spelling_record(linkages.kanji_spelling),
            conjugation_set: build_conjugation_set_record(linkages.conjugation_set),
            conjugations: build_conjugation_records(linkages.conjugation_set),
            references: build_reference_records(linkages.vocabulary),
            linkages: build_linkage_record(linkages)
          }
        end

        private

        def build_vocabulary_record(vocabulary)
          {
            id: vocabulary.id,
            word_class_code: vocabulary.word_class_code,
            conjugation_kind_code: vocabulary.conjugation_kind_code,
            jlpt_level: vocabulary.jlpt_level,
            row_num: vocabulary.row_num,
            tags: vocabulary.tags.join(',').then { |s| "{#{s}}" },
            date_added: vocabulary.date_added
          }
        end

        def build_definition_record(definition)
          {
            id: definition.id,
            vocabulary_id: definition.vocabulary_id,
            sort_rank: definition.sort_rank,
            value: definition.value
          }
        end

        def build_spelling_record(spelling)
          return nil if spelling.nil? #In some cases can be null

          {
            id: spelling.id,
            vocabulary_id: spelling.vocabulary_id,
            spelling_kind_code: spelling.spelling_kind_code,
            value: spelling.value
          }
        end

        def build_conjugation_set_record(conjugation_set)
          return nil if conjugation_set.nil?

          {
            id: conjugation_set.id,
            vocabulary_id: conjugation_set.vocabulary_id
          }
        end

        def build_conjugation_records(conjugation_set)
          return [] if conjugation_set.nil?

          conjugation_set.conjugations.map do |conjugation|
            {
              id: conjugation.id,
              conjugation_set_id: conjugation_set.id,
              politeness_code: conjugation.politeness_code,
              charge_code: conjugation.charge_code,
              form_code: conjugation.form_code,
              sort_rank: conjugation.sort_rank,
              value: conjugation.value
            }
          end
        end

        def build_reference_records(vocabulary)
          vocabulary.lesson_codes.map do |lesson_code|
            {
              vocabulary_id: vocabulary.id,
              lesson_code: lesson_code
            }
          end
        end

        def build_linkage_record(linkages)
          {
            vocabulary_id: linkages.vocabulary.id,
            preferred_definition_id: linkages.preferred_definition.id,
            preferred_spelling_id: linkages.preferred_spelling.id,
            phonetic_spelling_id: linkages.phonetic_spelling.id,
            alt_phonetic_spelling_id: linkages.alt_phonetic_spelling&.id,
            kanji_spelling_id: linkages.kanji_spelling&.id,
            conjugation_set_id: linkages.conjugation_set&.id
          }
        end

      end

    end
  end
end
