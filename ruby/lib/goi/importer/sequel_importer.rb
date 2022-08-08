require 'date'
require 'sequel'

require_relative '../model/vocabulary'
require_relative '../nihongo'
require_relative 'base_importer'

module Goi
  module Importer

    class SequelImporter < BaseImporter

      LINKAGE_FIELD_ID_COL_MAP = {
        :preferred_definition => :preferred_definition_id,
        :preferred_spelling => :preferred_spelling_id,
        :phonetic_spelling => :phonetic_spelling_id,
        :alt_phonetic_spelling => :alt_phonetic_spelling_id,
        :kanji_spelling => :kanji_spelling_id
      }

      def initialize(config:)
        super(config:)
        @db = Sequel.postgres(config.db_config)

        @vocabulary_parser = VocabularyParser.new
        @definition_parser = DefinitionParser.new
        @spelling_parser = SpellingParser.new
      end

      attr_reader :db

      attr_reader :vocabulary_parser, :definition_parser, :spelling_parser

      def import
        library = Library.new(db:).load_all

        library.vocabulary.map do |vocabulary_record|
          vocabulary = vocabulary_parser.parse(vocabulary_record:, library:)
          vocabulary_id = vocabulary.id
          Goi::Model::Vocabulary::Linkages.new(
            vocabulary:,
            preferred_definition: definition_parser.parse(vocabulary_id:, library:, linkage_field: :preferred_definition),
            preferred_spelling: spelling_parser.parse(vocabulary_id:, library:, linkage_field: :preferred_spelling, required: true),
            phonetic_spelling: spelling_parser.parse(vocabulary_id:, library:, linkage_field: :phonetic_spelling, required: true),
            alt_phonetic_spelling: spelling_parser.parse(vocabulary_id:, library:, linkage_field: :alt_phonetic_spelling, required: false),
            kanji_spelling: spelling_parser.parse(vocabulary_id:, library:, linkage_field: :kanji_spelling, required: false),
          )
        end
      end

      private
      class VocabularyParser

        def parse(vocabulary_record:, library:)
          Goi::Model::Vocabulary::Vocabulary.new(
            id: vocabulary_record.fetch(:id),
            word_class_code: vocabulary_record.fetch(:word_class_code),
            conjugation_kind_code: vocabulary_record[:conjugation_kind_code],
            jlpt_level: vocabulary_record[:jlpt_level],
            row_num: vocabulary_record.fetch(:row_num),
            date_added: vocabulary_record.fetch(:date_added),
            tags: parse_tags(vocabulary_record:, library:),
            lesson_codes: parse_lesson_codes(vocabulary_record:, library:)
          )
        end

        private

        def parse_tags(vocabulary_record:, library:)
          vocabulary_record[:tags]&.clean&.delete('{}')&.split(/\s+|,\s*/) || []
        end

        def parse_lesson_codes(vocabulary_record:, library:)
          id = vocabulary_record.fetch(:id)
          library.references[id]&.map { |ref_rec| ref_rec[:lesson_code] }&.compact&.uniq || []
        end

      end

      class DefinitionParser

        def parse(vocabulary_id:, library:,  linkage_field:)
          id = Goi::Model::Vocabulary::Definition.create_id(vocabulary_id:, linkage_field:)
          record = resolve_record(vocabulary_id:, library:, linkage_field:)

          Goi::Model::Vocabulary::Definition.new(
            id:,
            vocabulary_id:,
            value: record[:value],
            sort_rank: 1
          )
        end

        private

        def resolve_record(vocabulary_id:, library:, linkage_field:)
          linkages = library.linkages.fetch(vocabulary_id)
          all_definition_records = library.definitions.fetch(vocabulary_id)

          all_definition_records.find do |r|
            r[:id] == linkages[LINKAGE_FIELD_ID_COL_MAP[linkage_field]]
          end
        end

      end

      class SpellingParser

        def parse(vocabulary_id:, library:,  linkage_field:, required:)
          id = Goi::Model::Vocabulary::Spelling.create_id(vocabulary_id:, linkage_field:)
          record = resolve_record(vocabulary_id:, library:, linkage_field:)

          return nil if !required && record.nil?

          Goi::Model::Vocabulary::Spelling.new(
            id:,
            vocabulary_id:,
            spelling_kind_code: record[:spelling_kind_code],
            value: record[:value]
          )
        end

        private

        def resolve_record(vocabulary_id:, library:, linkage_field:)
          linkages = library.linkages.fetch(vocabulary_id)
          all_definition_records = library.spellings.fetch(vocabulary_id)

          all_definition_records.find do |r|
            r[:id] == linkages[LINKAGE_FIELD_ID_COL_MAP[linkage_field]]
          end
        end

        def resolve_spelling_value(vocabulary_id:, library:)

        end

      end

      class Library

        def initialize(db:)
          @db = db
        end

        attr_reader :vocabulary, :definitions, :spellings, :linkages, :references

        def load_all
            @vocabulary = load_vocabulary
            @definitions = load_definitions.group_by { |d| d[:vocabulary_id] }
            @spellings = load_spellings.group_by { |d| d[:vocabulary_id] }
            @linkages = load_linkages.group_by { |d| d[:vocabulary_id] }.transform_values { |ls| ls.first }
            @references = load_references.group_by { |d| d[:vocabulary_id] }

            self
        end

        private

        attr_reader :db

        def load_vocabulary
          db[Sequel[:vocabulary][:vocabulary]].order(:row_num).all
        end

        def load_definitions
          db[Sequel[:vocabulary][:definition]].all
        end

        def load_spellings
          db[Sequel[:vocabulary][:spelling]].all
        end

        def load_linkages
          db[Sequel[:vocabulary][:linkages]].all
        end

        def load_references
          db[Sequel[:vocabulary][:reference]].all
        end
      end

    end

  end
end
