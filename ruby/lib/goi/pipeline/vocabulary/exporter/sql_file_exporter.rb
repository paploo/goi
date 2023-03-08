# frozen_string_literal: true

require 'sequel'
require_relative '../../../sql/vocabulary_record_builder'

module Goi
  module Pipeline
    module Vocabulary
      module Exporter

        class SqlFileExporter < Vocabulary::Exporter::Base

          def initialize(db_config:, outfile_pathname:)
            super()

            @db_config = db_config
            @db = nil

            @outfile_pathname = outfile_pathname

            @record_builder = Goi::SQL::VocabularyRecordBuilder.new
          end

          attr_reader :db_config
          attr_reader :outfile_pathname

          def export(linkages)
            record_groups = linkages.map do |ln|
              record_builder.build_record_group(ln)
            end

            open_path(outfile_pathname) do |io|
              record_groups.each do |record_group|
                sql_stmts = generate_insert_sql(record_group)
                sql_stmts.each { |stmt| io.puts(stmt + ';') }
              end
            end
          end

          private

          def db
            @db ||= Sequel.postgres(db_config)
          end

          attr_reader :record_builder

          def generate_insert_sql(record_group)
            main_entities_sql = [
              "-- #{record_group[:preferred_spelling][:value]} | #{record_group[:preferred_definition][:value]} --",
              db[Sequel[:vocabulary][:vocabulary]].insert_sql(record_group[:vocabulary]),
              db[Sequel[:vocabulary][:definition]].insert_sql(record_group[:preferred_definition]),
              db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:preferred_spelling]),
              db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:phonetic_spelling]),
              record_group[:alt_phonetic_spelling] && db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:alt_phonetic_spelling]),
              record_group[:kanji_spelling] && db[Sequel[:vocabulary][:spelling]].insert_sql(record_group[:kanji_spelling]),
              record_group[:conjugation_set] && db[Sequel[:vocabulary][:conjugation_set]].insert_sql(record_group[:conjugation_set]),
              db[Sequel[:vocabulary][:linkages]].insert_sql(record_group[:linkages]),
            ].compact

            ref_group_sql = record_group[:references].map do |ref|
              db[Sequel[:vocabulary][:reference]].insert_sql(ref)
            end

            conjugation_sql = record_group[:conjugations].map do |conj|
              db[Sequel[:vocabulary][:conjugation]].insert_sql(conj)
            end

            main_entities_sql + ref_group_sql + conjugation_sql
          end

        end

      end
    end
  end
end
