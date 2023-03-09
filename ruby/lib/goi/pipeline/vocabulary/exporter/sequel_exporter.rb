# frozen_string_literal: true

require 'sequel'
require_relative '../record_builder'

module Goi
  module Pipeline
    module Vocabulary
      module Exporter

        # Writes the loaded data directly into the database.
        #
        # We currently use a naïve method based on the google sheet being the source of truth:
        # 1. Delete all vocabulary
        # 2. Reload all, emitting an error for any that doen't load.
        # This is really slow, loading about 100 vocabs/sec on my machine.
        #
        # One strategy to make this faster is to record a deep checksum for each vocabulary,
        # and then update the ones that have changed (and, as long as the google sheet is
        # the source of truth, delete any vocab that no longer exists).
        class SequelExporter < Vocabulary::Exporter::Base

          def initialize(db_config:)
            super()

            @db_config = db_config
            @db = nil

            @record_builder = Vocabulary::RecordBuilder.new
          end

          attr_reader :db_config

          def export(linkages)
            naive_export(linkages:)
          end

          private

          attr_reader :record_builder

          def db
            Sequel.extension :pg_array
            @db ||= Sequel.postgres(db_config).tap do |db|
              # Not needed for export, but part of proper setup for pg_array support.
              db.extension(:pg_array)
            end
          end

          def naive_export(linkages:)
            delete_all
            write_linkages(linkages:)
            refresh_materialized_views
          end

          def delete_all
            # Catches all the relevant data due to cascading rules.
            db[Sequel[:vocabulary][:vocabulary]].delete
            db["VACUUM"] # Clean-up the tables.
          end

          def refresh_materialized_views
            # When/if we have materialized views, this is the place to do the refresh.
            # db["REFRESH MATERIALIZED VIEW CONCURRENTLY some_schema.some_view"]
          end

          def write_linkages(linkages:)
            p = Pipeline::Core::SafeProcessor.new(id_getter: ->(ln) { ln.vocabulary.id })
            p.process(linkages) do |linkage|
              record_group = record_builder.build_record_group(linkage)
              write_record_group(record_group:)
            end
          end

          def write_record_group(record_group:)
            # Doing many small transactions had similar perf and behaves better for partial loads.
            db.transaction do
              db[Sequel[:vocabulary][:vocabulary]].insert(record_group[:vocabulary])
              db[Sequel[:vocabulary][:definition]].insert(record_group[:preferred_definition])
              db[Sequel[:vocabulary][:spelling]].insert(record_group[:preferred_spelling])
              db[Sequel[:vocabulary][:spelling]].insert(record_group[:phonetic_spelling])
              record_group[:alt_phonetic_spelling] && db[Sequel[:vocabulary][:spelling]].insert(record_group[:alt_phonetic_spelling])
              record_group[:kanji_spelling] && db[Sequel[:vocabulary][:spelling]].insert(record_group[:kanji_spelling])
              record_group[:conjugation_set] && db[Sequel[:vocabulary][:conjugation_set]].insert(record_group[:conjugation_set])
              db[Sequel[:vocabulary][:linkages]].insert(record_group[:linkages])

              record_group[:references].each do |ref|
                db[Sequel[:vocabulary][:reference]].insert(ref)
              end

              record_group[:conjugations].each do |conj|
                db[Sequel[:vocabulary][:conjugation]].insert(conj)
              end
            end
          end

        end

      end
    end
  end
end
