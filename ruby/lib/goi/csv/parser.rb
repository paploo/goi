require_relative '../model/vocabulary'
require_relative '../nihongo'

module Goi
  module CSV
    module Parser

      class GoogleSheetParser

        PREFERRED_SPELLING_KEY = 'preferred_spelling'.freeze
        PHONETIC_SPELLING_KEY = 'phonetic_spelling'.freeze
        ALT_PHONETIC_SPELLING_KEY = 'alt_phon_spell'.freeze
        KANJI_SPELLING_KEY = 'kanji_spelling'.freeze

        def initialize
          @vocabulary_parser = VocabularyParser.new
          @definition_parser = DefinitionParser.new
          @spelling_parser = SpellingParser.new
        end

        attr_reader :vocabulary_parser, :definition_parser, :spelling_parser

        def parse(rows:)
          rows.map { |row| parse_row(row:) }
        end

        private

        def parse_row(row:)
          vocabulary = parse_vocabulary(row:)
          vocabulary_id = vocabulary.id
          Goi::Model::Vocabulary::Linkages.new(
            vocabulary:,
            preferred_definition: parse_definition(row:, vocabulary_id:),
            preferred_spelling: parse_spelling(row:, vocabulary_id:, key: PREFERRED_SPELLING_KEY),
            phonetic_spelling: parse_spelling(row:, vocabulary_id:, key: PHONETIC_SPELLING_KEY),
            alt_phonetic_spelling: parse_spelling(row:, vocabulary_id:, key: ALT_PHONETIC_SPELLING_KEY),
            kanji_spelling: parse_spelling(row:, vocabulary_id:, key: KANJI_SPELLING_KEY)
          )
        end

        def parse_vocabulary(row:)
          vocabulary_parser.parse_row(row:)
        end

        def parse_definition(row:, vocabulary_id:)
          definition_parser.parse_row(row:, vocabulary_id:)
        end

        def parse_spelling(row:, key:, vocabulary_id:)
          spelling_parser.parse_row(row:, key:, vocabulary_id:)
        end

      end

      class VocabularyParser

        def parse(row:)
          Goi::Model::Vocabulary::Vocabulary.new(
            id: row[ID_KEY]&.clean,
            word_class_code: row.fetch_required(WORD_CLASS_CODE_KEY).clean,
            conjugation_kind_code: row.fetch_required(CONJUGATION_KIND_CODE_KEY).clean,
            jlpt_level: row[JLPT_LEVEL_KEY]&.clean&.to_i,
            row_num: row.fetch_required(ROW_NUM_KEY).clean.to_i,
            tags: arrray_parse(row[TAGS_KEY]),
            lesson_codes: array_parse(row[LESSON_CODES_KEY])
          )
        end

        private

        ID_KEY = 'id'.freeze
        WORD_CLASS_CODE_KEY = 'word_class_code'.freeze
        CONJUGATION_KIND_CODE_KEY = 'conjugation_kind_code'.freeze
        JLPT_LEVEL_KEY = 'jlpt_level'.freeze
        ROW_NUM_KEY = 'row_num'.freeze
        TAGS_KEY = 'tags'.freeze
        LESSON_CODES_KEY = 'lesson_codes'.freeze

        def array_parse(value)
          return nil if value.nil?
          value.clean.delete('{}').upcase.split(/\s+|,?\s*/)
        end

      end

      class DefinitionParser

        DEFINITION_KEY = 'definition'.freeze

        def parse(row:, vocabulary_id:)
          Goi::Model::Vocabulary::Definition.new(
            vocabulary_id:,
            value: row.fetch_required(DEFINITION_KEY).clean
          )
        end

      end

      class SpellingParser

        CODE_MAP = {
          hiragana: 'HIRAGANA',
          kanji: 'KANJI',
          katakana: 'KATAKANA'
        }.freeze

        def parse(row:, key:, vocabulary_id:)
          Goi::Model::Vocabulary::Spelling.new(
            vocabulary_id:,
            spelling_kind_code: infer_kind_code(row.fetch_required(key).clean),
            value:
          )
        end

        private

        def infer_kind_code(value)
          raw_class = Goi::Nihongo::StringClassification.classify(value)
          CODE_MAP.fetch(raw_class) do |k|
            raise(ArgumentError, "Cannot code illegal classification '#{k.inspect}' for string #{value.inspect}")
          end
        end

      end

    end
  end
end
