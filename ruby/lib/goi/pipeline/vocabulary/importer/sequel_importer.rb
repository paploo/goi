# frozen_string_literal: true

require 'date'
require 'sequel'

require_relative '../../../model/vocabulary'
require_relative '../../../nihongo'

module Goi
  module Pipeline
    module Vocabulary
      module Importer

        class SequelImporter < Vocabulary::Importer::Base

          LINKAGE_FIELD_ID_COL_MAP = {
            :preferred_definition => :preferred_definition_id,
            :preferred_spelling => :preferred_spelling_id,
            :phonetic_spelling => :phonetic_spelling_id,
            :alt_phonetic_spelling => :alt_phonetic_spelling_id,
            :kanji_spelling => :kanji_spelling_id,
            :conjugation_set_id => :conjugation_set_id
          }

          def initialize(db_config:)
            super()
            @db = Sequel.postgres(db_config)

            @vocabulary_parser = VocabularyParser.new
            @definition_parser = DefinitionParser.new
            @spelling_parser = SpellingParser.new
            @conjugation_parser = ConjugationParser.new
            @conjugation_set_parser = ConjugationSetParser.new(conjugation_parser: @conjugation_parser)
          end

          attr_reader :db

          attr_reader :vocabulary_parser
          attr_reader :definition_parser
          attr_reader :spelling_parser
          attr_reader :conjugation_set_parser
          attr_reader :conjugation_parser

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
                conjugation_set: conjugation_set_parser.parse(vocabulary_id:, library:)
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
              record = resolve_record(vocabulary_id:, library:, linkage_field:)

              Goi::Model::Vocabulary::Definition.new(
                id: record[:id],
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
              record = resolve_record(vocabulary_id:, library:, linkage_field:)

              return nil if !required && record.nil?

              Goi::Model::Vocabulary::Spelling.new(
                id: record[:id],
                vocabulary_id:,
                spelling_kind_code: record[:spelling_kind_code],
                value: record[:value]
              )
            end

            private

            def resolve_record(vocabulary_id:, library:, linkage_field:)
              linkages = library.linkages.fetch(vocabulary_id)
              all_spelling_records = library.spellings.fetch(vocabulary_id)

              all_spelling_records.find do |r|
                r[:id] == linkages[LINKAGE_FIELD_ID_COL_MAP[linkage_field]]
              end
            end

          end

          class ConjugationSetParser

            def initialize(conjugation_parser:)
              @conjugation_parser = conjugation_parser
            end

            def parse(vocabulary_id: String, library: Library)
              record = resolve_record(vocabulary_id:, library:, linkage_field: :conjugation_set_id)

              return nil if record.nil?

              conjugations = conjugation_parser.parse(conjugation_set_id: record[:id], library:)

              Goi::Model::Vocabulary::ConjugationSet.new(
                id: record[:id],
                vocabulary_id:,
                conjugations:
              )
            end

            private

            attr_reader :conjugation_parser

            def resolve_record(vocabulary_id:, library:, linkage_field:)
              linkages = library.linkages.fetch(vocabulary_id)
              all_conjugation_set_records = library.conjugation_sets.fetch(vocabulary_id, [])

              all_conjugation_set_records.find do |r|
                r[:id] == linkages[LINKAGE_FIELD_ID_COL_MAP[linkage_field]]
              end
            end

          end

          class ConjugationParser

            def parse(conjugation_set_id: String, library: Library)
              records = library.conjugations.fetch(conjugation_set_id)

              records.map do |record|
                Goi::Model::Vocabulary::Conjugation.new(
                  id: record[:id],
                  conjugation_set_id:,
                  politeness_code: record[:politeness_code],
                  charge_code: record[:charge_code],
                  form_code: record[:form_code],
                  sort_rank: record[:sort_rank],
                  value: record[:value]
                )
              end
            end

          end

          class Library

            def initialize(db:)
              @db = db
            end

            attr_reader :vocabulary
            attr_reader :definitions
            attr_reader :spellings
            attr_reader :linkages
            attr_reader :references
            attr_reader :conjugation_sets
            attr_reader :conjugations

            def load_all
              @vocabulary = load_vocabulary
              @definitions = load_definitions.group_by { |d| d[:vocabulary_id] }
              @spellings = load_spellings.group_by { |d| d[:vocabulary_id] }
              @linkages = load_linkages.group_by { |d| d[:vocabulary_id] }.transform_values(&:first)
              @references = load_references.group_by { |d| d[:vocabulary_id] }
              @conjugation_sets = load_conjugation_sets.group_by { |d| d[:vocabulary_id] }
              @conjugations = load_conjugations.group_by { |d| d[:conjugation_set_id] }

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

            def load_conjugation_sets
              db[Sequel[:vocabulary][:conjugation_set]].all
            end

            def load_conjugations
              db[Sequel[:vocabulary][:conjugation]].all
            end
          end

        end

      end
    end
  end
end
