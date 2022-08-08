require 'sequel'

module Goi
  module Exporter
    class SqlFileExporter < BaseExporter

      def initialize(mode: :sql)
        super()
        @db = Sequel.postgres('goi', user: 'postgres', password: 'postgres', host: 'localhost')
        @io = STDOUT
        @mode = mode
      end

      attr_reader :db
      attr_reader :io
      attr_reader :mode

      def export(linkages:)
        record_builder = VocabularyRecordBuilder.new
        record_groups = linkages.map do |ln|
          record_builder.build_record_group(ln)
        end

        if mode == :sql
          record_groups.each do |record_group|
            sql_stmts = generate_insert_sql(record_group)
            sql_stmts.each { |stmt| io.puts(stmt + ';') }
          end
        else
          Synchronizer.new(db:).sync(record_group)
        end
      end

      private

      def generate_insert_sql(record_group)
        main_entities_sql = [
          "-- #{record_group[:preferred_spelling][:value]} | #{record_group[:preferred_definition][:value]} --",
          db[Sequel[:vocabulary][:vocabulary]].insert_sql(record_group[:vocabulary]),
          db[Sequel[:vocabulary][:definition]].insert_sql(record_group[:preferred_definition]),
          db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:preferred_spelling]),
          db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:phonetic_spelling]),
          record_group[:alt_phonetic_spelling] && db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:alt_phonetic_spelling]),
          record_group[:kanji_spelling] && db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:kanji_spelling]),
          db[Sequel[:vocabulary][:linkages]].insert_sql(record_group[:linkages]),
        ].compact

        ref_group_sql = record_group[:references].map do |ref|
          db[Sequel[:vocabulary][:reference]].insert_sql(ref)
        end

        main_entities_sql + ref_group_sql
      end

      class Synchronizer

        # To sync we must:
        # - Identify records to insert,
        # - Identify records to remove,
        # - Identify records to update.
        #
        # * For the vocabulary, definition, spellings and links, we assume ID idempotency based on relationship.
        # * For the references we must go through the same sync actions.
        def initialize(db:)
          @db = db
        end

        def sync(record_groups)
          # First determine what to insert, update, and remove.
          db.transaction do
            library = Library.new(db:).load_all
            insert, update, delete = calculate_diff(library.vocabulary_ids, record_groups.map { |g| g[:vocabulary][:id ]})
          end
          raise NotImplementedError
        end

        private

        attr_reader :db

        def calculate_diff(old_values, new_values)
          [
            #insert
            new_values - old_values,
            #update
            new_values.intersection(old_values),
            #delete
            old_values - new_values
          ]
        end

        class Library

          def initialize(db:)
            @db = db
          end

          attr_reader :vocabulary_ids
          attr_reader :reference_codes

          def load_all
            load_vocabulary
            load_references

            self
          end

          private

          def load_vocabulary
            @vocabulary_ids = db[Sequel[:vocabulary][:vocabulary]]
                                .select(:id)
                                .all
                                .map { |r| r[:id] }
          end

          def load_references
            @references = db[Sequel[:vocabulary][:reference]]
                            .all
                            .group_by { |r| r[:vocabulary_id] }
                            .transform_values { |v| v.map { |r| r[:lesson_code] } }
          end

        end

        attr_reader :db

      end

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
end
