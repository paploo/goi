require 'pg'
require 'sequel'
require 'sequel/extensions/pg_array'

require_relative '../sql/vocabulary_record_builder'

module Goi
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
    class SequelExporter < BaseExporter

      def initialize(config:)
        super(config:)
        @record_builder = Goi::SQL::VocabularyRecordBuilder.new
      end

      def export(linkages:)
        naive_export(linkages:)
      end

      private

      attr_reader :record_builder

      def db
        Sequel.extension :pg_array
        @db ||= Sequel.postgres(config.db_config).tap do |db|
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
        db[Sequel[:vocabulary][:vocabulary]].delete
        db["VACUUM"] # Clean-up the table.
      end

      def refresh_materialized_views
        # When/if we have materialized views, this is the place to do the refresh.
        # db["REFRESH MATERIALIZED VIEW CONCURRENTLY some_schema.some_view"]
      end

      def write_linkages(linkages:)
        error_ids = []
        start_t = Time.now
        STDERR.puts("[#{self.class.name}] writing #{linkages.length} records.")

        linkages.each do |linkage|
          begin
            record_group = record_builder.build_record_group(linkage)
            write_record_group(record_group:)
          rescue => err
            msg = "[#{self.class.name}] WRITE ERROR: cannot write vocabulary ID #{linkage.vocabulary.id} due to error: #{err.full_message}"
            STDERR.puts(msg)
            error_ids << linkage.vocabulary.id
          end
        end

        delta_t = Time.now - start_t
        rate = linkages.length.to_f / delta_t
        if !error_ids.empty?
          STDERR.puts("#{self.class.name} Failed to load #{error_ids.length} vocabulary for ids:")
          error_ids.each { |id| STDERR.puts "\t#{id}" }
        end
        STDERR.puts("[#{self.class.name}] wrote #{linkages.length} records with #{error_ids.length} errors in #{delta_t} seconds (#{rate} rec/sec)")
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