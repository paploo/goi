require 'uuidtools'

module Goi
  module Model
    module Vocabulary

      # Linkages
      #
      # This ties together the various stand-alone
      class Linkages

        def self.wrap(attributes)
          new(**attributes)
        end

        def initialize(vocabulary:,
                       preferred_definition:,
                       preferred_spelling:,
                       phonetic_spelling:,
                       alt_phonetic_spelling: nil,
                       kanji_spelling: nil)
          @vocabulary = vocabulary
          @preferred_definition = preferred_definition
          @preferred_spelling = preferred_spelling
          @phonetic_spelling = phonetic_spelling
          @alt_phonetic_spelling = alt_phonetic_spelling
          @kanji_spelling = kanji_spelling

          #TODO: Rw-enable after prototyping
          #raise ArgumentError, "One or more links don't go with the given vocabulary" unless links_valid?
        end

        attr_reader :vocabulary, :preferred_definition, :preferred_spelling, :phonetic_spelling, :alt_phonetic_spelling, :kanji_spelling

        private

        def links_valid?
          required_validity = [
            preferred_definition&.vocabulary_id,
            preferred_spelling&.vocabulary_id,
            phonetic_spelling&.vocabulary_id
          ].all? { |id| !id.nil? && id == vocabulary.id }

          optional_validity = [
            alt_phonetic_spelling&.vocabulary_id,
            kanji_spelling&.vocabulary_id
          ].all? {|id| id.nil? || id == vocabulary.id }

          required_validity && optional_validity
        end

      end

      # Vocabulary
      class Vocabulary

        def self.wrap(attributes)
          new(**attributes)
        end

        def self.create_id
          UUIDTools::UUID.random_create.to_s
        end

        def initialize(id: nil,
                       word_class_code:,
                       conjugation_kind_code: nil,
                       jlpt_level: nil,
                       row_num:,
                       date_added:,
                       tags: [],
                       lesson_codes: [])
          @id = id || self.class.create_id
          @word_class_code = word_class_code
          @conjugation_kind_code = conjugation_kind_code
          @jlpt_level = jlpt_level
          @row_num = row_num
          @date_added = date_added
          @tags = (tags || []).sort
          @lesson_codes = (lesson_codes || []).sort
        end

        attr_reader :id, :word_class_code, :conjugation_kind_code, :jlpt_level, :row_num, :date_added, :tags, :lesson_codes

        def to_h
          { id:, word_class_code:, conjugation_kind_code:, jlpt_level:, row_num:, date_added:, tags:, lesson_codes: }
        end

      end

      # Definition
      class Definition

        UUID5_NAMESPACE = UUIDTools::UUID.parse('c7812647-678a-4bf5-bed3-b33fe499469c').to_s

        def self.create_id(vocabulary_id:, linkage_field:)
          name = [vocabulary_id, linkage_field].map(&:to_s).join('|')
          ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
          UUIDTools::UUID.sha1_create(ns, name).to_s
        end

        def self.wrap(attributes)
          new(**attributes)
        end

        def initialize(id:,vocabulary_id:, value:, sort_rank: 1)
          @id = id || raise(ArgumentError, "ID required")
          @vocabulary_id = vocabulary_id
          @value = value
          @sort_rank = sort_rank
        end

        attr_reader :id, :vocabulary_id, :value, :sort_rank

        def to_h
          { id:, vocabulary_id:, value:, sort_rank: }
        end

      end

      # Spelling
      class Spelling

        UUID5_NAMESPACE = UUIDTools::UUID.parse('546a4b2c-6b83-4fe9-902e-7c7ade990930').to_s

        # Create an ID that is consistent for the linkage position.
        # This makes database syncing scripts a LOT easier to manage.
        def self.create_id(vocabulary_id:, linkage_field:)
          name = [vocabulary_id, linkage_field].map(&:to_s).join('|')
          ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
          UUIDTools::UUID.sha1_create(ns, name).to_s
        end

        def self.wrap(attributes)
          new(**attributes)
        end

        def initialize(id:,vocabulary_id:, spelling_kind_code:, value:)
          @id = id || raise(ArgumentError, "ID required")
          @vocabulary_id = vocabulary_id
          @spelling_kind_code = spelling_kind_code
          @value = value
        end

        attr_reader :id, :vocabulary_id, :spelling_kind_code, :value

        def to_h
          { id:, vocabulary_id:, spelling_kind_code:, value: }
        end

      end

    end
  end
end
