# frozen_string_literal: true

require 'sequel'
require 'pp'
require_relative '../record_builder'

module Goi
  module Pipeline
    module Grammar
      module Exporter

        # Write the loaded data diretly into the database.
        #
        # Like with the Vocabulary SequelExporter, we take the nuclear option of rebuilding the data within the
        # grammar schema each time. This is heavy-handed, but is a workflow that is (currently) working for me.
        #
        # In the future I'd ideally have a sync process, but that would take a lot of comparison code and gives
        # me little benefit because the database is not a transactional source of truth right now. And even if it
        # were, then I'd switch to it being the importer, and only export to non-db sources, so I wtill wouldn't
        # need it.
        class SequelExporter < Grammar::Exporter::Base

          def initialize(db_config:)
            super()

            @db_config = db_config
            @db = nil

            @record_builder = Grammar::RecordBuilder.new
          end

          attr_reader :db_config

          def export(hydrated_rules)
            naive_export(hydrated_rules:)
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

          def naive_export(hydrated_rules:)
            delete_all
            write_hydrated_rules(hydrated_rules:)
            refresh_materialized_views
          end

          def delete_all
            # Catches all the relevant data due to cascading rules.
            db[Sequel[:grammar][:rule]].delete
            db["VACUUM"] # Clean-up the tables.
          end

          def refresh_materialized_views
            # When/if we have materialized views, this is the place to do the refresh.
            # db["REFRESH MATERIALIZED VIEW CONCURRENTLY some_schema.some_view"]
          end

          def write_hydrated_rules(hydrated_rules:)
            error_ids = []
            start_t = Time.now
            STDERR.puts("[#{self.class.name}] writing #{hydrated_rules.length} records.")

            hydrated_rules.each do |hydrated_rule|
              begin
                record_group = record_builder.build_record_group(hydrated_rule)
                write_record_group(record_group:)
              rescue => err
                id = hydrated_rule.rule.id
                msg = "[#{self.class.name}] WRITE ERROR: cannot write grammar rule ID #{id} due to error: #{err.full_message}"
                STDERR.puts(msg)
                error_ids << id
              end
            end

            delta_t = Time.now - start_t
            rate = hydrated_rules.length.to_f / delta_t
            unless error_ids.empty?
              STDERR.puts("#{self.class.name} Failed to load #{error_ids.length} vocabulary for ids:")
              error_ids.each { |id| STDERR.puts "\t#{id}" }
            end
            STDERR.puts("[#{self.class.name}] wrote #{hydrated_rules.length} records with #{error_ids.length} errors in #{delta_t} seconds (#{rate} rec/sec)")
          end

          def write_record_group(record_group:)
            PP.pp(record_group, $>, 200)
          end

        end

      end
    end
  end
end