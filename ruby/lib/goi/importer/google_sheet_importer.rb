require 'date'

require_relative '../model/vocabulary'
require_relative '../nihongo'
require_relative 'base_importer'

module Goi
  module Importer

    class GoogleSheetImporter < BaseImporter

      PREFERRED_SPELLING_FIELD_DATA = {linkage_field: :preferred_spelling, key: 'preferred_spelling'}.freeze
      PHONETIC_SPELLING_FIELD_DATA = {linkage_field: :phonetic_spelling, key: 'phonetic_spelling'}.freeze
      ALT_PHONETIC_SPELLING_FIELD_DATA = {linkage_field: :alt_phonetic_spelling, key: 'alt_phon_spell'}.freeze
      KANJI_SPELLING_FIELD_DATA = {linkage_field: :kanji_spelling, key: 'kanji_spelling'}.freeze

      def initialize(config:)
        super(config:)
        @vocabulary_parser = VocabularyParser.new
        @definition_parser = DefinitionParser.new
        @spelling_parser = SpellingParser.new
        @conjugation_parser = ConjugationParser.new
      end

      attr_reader :vocabulary_parser, :definition_parser, :spelling_parser, :conjugation_parser

      def import
        rows = CSV.read(config.file_pathname.to_s, headers: true).map(&:to_h)
        parse_rows(rows:)
      end

      private

      def parse_rows(rows:)
        rows.map { |row| parse_row(row:) }
      end

      def parse_row(row:)
        vocabulary = parse_vocabulary(row:)
        vocabulary_id = vocabulary.id
        Goi::Model::Vocabulary::Linkages.new(
          vocabulary:,
          preferred_definition: parse_definition(row:, vocabulary_id:),
          preferred_spelling: parse_spelling(row:, vocabulary_id:, field_data: PREFERRED_SPELLING_FIELD_DATA, required: true),
          phonetic_spelling: parse_spelling(row:, vocabulary_id:, field_data: PHONETIC_SPELLING_FIELD_DATA, required: true),
          alt_phonetic_spelling: parse_spelling(row:, vocabulary_id:, field_data: ALT_PHONETIC_SPELLING_FIELD_DATA, required: false),
          kanji_spelling: parse_spelling(row:, vocabulary_id:, field_data: KANJI_SPELLING_FIELD_DATA, required: false),
          conjugation_set: parse_conjugation(row:, vocabulary_id:)
        )
      end

      def parse_vocabulary(row:)
        vocabulary_parser.parse_row(row:)
      end

      def parse_definition(row:, vocabulary_id:)
        definition_parser.parse_row(row:, vocabulary_id:)
      end

      def parse_spelling(row:, field_data:, required:, vocabulary_id:)
        spelling_parser.parse_row(row:, field_data:, required:, vocabulary_id:)
      end

      def parse_conjugation(row:, vocabulary_id:)
        conjugation_parser.parse_row(row:, vocabulary_id:)
      end

      class VocabularyParser

        def parse_row(row:)
          Goi::Model::Vocabulary::Vocabulary.new(
            id: row[ID_KEY]&.clean || create_id(row:),
            word_class_code: row.fetch_required(WORD_CLASS_CODE_KEY).clean,
            conjugation_kind_code: row[CONJUGATION_KIND_CODE_KEY]&.clean,
            jlpt_level: row[JLPT_LEVEL_KEY]&.clean&.to_i,
            row_num: row.fetch_required(ROW_NUM_KEY).clean.to_i,
            date_added: row.fetch(DATE_ADDED_KEY)&.clean&.then { |s| Date.parse(s) },
            tags: array_parse(row[TAGS_KEY]&.clean),
            lesson_codes: array_parse(row[LESSON_CODES_KEY]&.clean)
          )
        end

        private

        ID_KEY = 'id'.freeze
        WORD_CLASS_CODE_KEY = 'word_class_code'.freeze
        CONJUGATION_KIND_CODE_KEY = 'conjugation_kind_code'.freeze
        JLPT_LEVEL_KEY = 'jlpt_level'.freeze
        ROW_NUM_KEY = 'row_num'.freeze
        DATE_ADDED_KEY = 'date_added'.freeze
        TAGS_KEY = 'tags'.freeze
        LESSON_CODES_KEY = 'lesson_codes'.freeze

        # Use the existing ID, or make one predictably if not present.
        # This is so that if we load the same Google Sheet to make several exports, we don't get random UUIDs!
        def create_id(row:)
          keys = [
            row[DefinitionParser::DEFINITION_KEY],
            row[PREFERRED_SPELLING_FIELD_DATA[:key]],
            row[PHONETIC_SPELLING_FIELD_DATA[:key]],
            row[ALT_PHONETIC_SPELLING_FIELD_DATA[:key]],
            row[KANJI_SPELLING_FIELD_DATA[:key]]
          ]

          Goi::Model::Vocabulary::Vocabulary.create_id(*keys).tap { |id|
            STDERR.puts "Created Missing Vocab ID #{id} with key array: #{keys.inspect}"
          }
        end

        def array_parse(value)
          value&.clean&.delete('{}')&.split(/\s+|,\s*/) || []
        end

        def const_case(s)
          # TODO: Make this also turn any non-alphanum char to an underscore.
          s.upcase
        end

      end

      class DefinitionParser

        DEFINITION_KEY = 'definition'.freeze

        def parse_row(row:, vocabulary_id:)
          Goi::Model::Vocabulary::Definition.new(
            id: Goi::Model::Vocabulary::Definition.create_id(vocabulary_id:, linkage_field: :preferred_definition),
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

        def parse_row(row:, field_data:, required: true, vocabulary_id:)
          key = field_data.fetch(:key)
          value = row[key]&.clean

          if value.nil?
            raise(ArgumentError, "Missing required spelling for key #{key.inspect} from row #{row.inspect}") if required
            return nil
          end

          linkage_field = field_data.fetch(:linkage_field)
          id = Goi::Model::Vocabulary::Spelling.create_id(vocabulary_id:, linkage_field:)

          Goi::Model::Vocabulary::Spelling.new(
            id:,
            vocabulary_id:,
            spelling_kind_code: infer_kind_code(value),
            value:
          )
        end

        private

        def infer_kind_code(value)
          raw_class = Goi::Nihongo::StringClassification.classify_string(value)
          CODE_MAP.fetch(raw_class) do |k|
            raise(ArgumentError, "Cannot code illegal classification '#{k.inspect}' for string #{value.inspect}")
          end
        end

      end


      class ConjugationParser

        def parse_row(row:, vocabulary_id:)
          set = parse_conjugation_set(row:, vocabulary_id:)
          set.conjugations.empty? ? nil : set
        end

        private

        KEY_MAP = {
          'dictionary_form' => { politeness: 'PLAIN', charge: 'POSITIVE', form: 'PRESENT', sort_rank: 1 },
          'past_form' => { politeness: 'PLAIN', charge: 'POSITIVE', form: 'PAST', sort_rank: 1 },
          'te_form' => { politeness: 'PLAIN', charge: 'POSITIVE', form: 'TE', sort_rank: 1 },

          'negative_form' => { politeness: 'PLAIN', charge: 'NEGATIVE', form: 'PRESENT', sort_rank: 1 },
          'negative_past_form' => { politeness: 'PLAIN', charge: 'NEGATIVE', form: 'PAST', sort_rank: 1 },
          'negative_te_form' => { politeness: 'PLAIN', charge: 'NEGATIVE', form: 'TE', sort_rank: 1 },

          'polite_form' => { politeness: 'POLITE', charge: 'POSITIVE', form: 'DICTIONARY', sort_rank: 1 },
          'polite_past_form' => { politeness: 'POLITE', charge: 'POSITIVE', form: 'PAST', sort_rank: 1 },
          'polite_te_form' => { politeness: 'POLITE', charge: 'POSITIVE', form: 'TE', sort_rank: 1 },

          'polite_negative_form' => { politeness: 'POLITE', charge: 'NEGATIVE', form: 'DICTIONARY', sort_rank: 1 },
          'polite_negative_past_form' => { politeness: 'POLITE', charge: 'NEGATIVE', form: 'PAST', sort_rank: 1 },
          'polite_negative_te_form' => { politeness: 'POLITE', charge: 'NEGATIVE', form: 'TE', sort_rank: 1 },
        }.freeze

        def parse_conjugation_set(row:, vocabulary_id:)
          id = Goi::Model::Vocabulary::ConjugationSet.create_id(vocabulary_id:)
          conjugations = parse_conjugations(row:, conjugation_set_id: id)

          Goi::Model::Vocabulary::ConjugationSet.new(
            id:,
            vocabulary_id:,
            conjugations:
          )
        end

        def parse_conjugations(row:, conjugation_set_id:)
          KEY_MAP.keys.map { |key| parse_conjugation(row:, key:, conjugation_set_id:) }
        end

        def parse_conjugation(row:, key:, conjugation_set_id:)
          value = row[key]&.clean
          return nil if value.nil?

          politeness_code, charge_code, form_code, sort_rank = KEY_MAP[key].values
          id = Goi::Model::Vocabulary::Conjugation.create_id(
            conjugation_set_id:,
            politeness_code:,
            charge_code:,
            form_code:,
            sort_rank:
          )

          Goi::Model::Vocabulary::Conjugation.new(
            id:,
            conjugation_set_id:,
            politeness_code:,
            charge_code:,
            form_code:,
            sort_rank:,
            value:
          )
        end

      end

    end
  end
end
