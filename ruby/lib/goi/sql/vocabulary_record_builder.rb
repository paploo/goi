module Goi
  module SQL
    class VocabularyRecordBuilder

      def build_record_group(linkages)
        {
          vocabulary: build_vocabulary_record(linkages, :vocabulary),
          preferred_definition: build_definition_record(linkages, :preferred_definition),
          preferred_spelling: build_spelling_record(linkages, :preferred_spelling),
          phonetic_spelling: build_spelling_record(linkages, :phonetic_spelling),
          alt_phonetic_spelling: build_spelling_record(linkages, :alt_phonetic_spelling),
          kanji_spelling: build_spelling_record(linkages, :kanji_spelling),
          references: build_reference_records(linkages, :vocabulary),
          linkages: build_linkage_record(linkages)
        }
      end

      def build_vocabulary_record(linkages, key)
        vocabulary = linkages.send(key)
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

      def build_definition_record(linkages, key)
        definition = linkages.send(key)
        {
          id: definition.id,
          vocabulary_id: definition.vocabulary_id,
          sort_rank: definition.sort_rank,
          value: definition.value
        }
      end

      def build_spelling_record(linkages, key)
        spelling = linkages.send(key)
        return nil if spelling.nil? #In some cases can be null

        {
          id: spelling.id,
          vocabulary_id: spelling.vocabulary_id,
          spelling_kind_code: spelling.spelling_kind_code,
          value: spelling.value
        }
      end

      def build_reference_records(linkages, vocabulary_key)
        vocabulary = linkages.send(vocabulary_key)
        lesson_codes = linkages.vocabulary.lesson_codes

        lesson_codes.map do |lesson_code|
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
          kanji_spelling_id: linkages.kanji_spelling&.id
        }
      end

    end
  end
end
