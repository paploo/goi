# frozen_string_literal: true

require 'sequel'
require_relative '../record_builder'
require_relative '../../core/safe_processor'

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
            p = Pipeline::Core::SafeProcessor.new(id_getter: ->(r) { r.rule.id })
            p.process(hydrated_rules) do |hydrated_rule|
              record_group = record_builder.build_record_group(hydrated_rule)
              write_record_group(record_group:)
            end
          end

          def write_record_group(record_group:)
            # Doing many small transactions had similar perf and behaves better for partial loads.
            db.transaction do
              db[Sequel[:grammar][:rule]].insert(record_group[:rule])

              record_group[:examples].each do |example|
                db[Sequel[:grammar][:example]].insert(example)
              end

              record_group[:rule_references].each do |rule_reference|
                db[Sequel[:grammar][:rule_reference]].insert(rule_reference)
              end

              record_group[:example_references].each do |example_reference|
                db[Sequel[:grammar][:example_reference]].insert(example_reference)
              end
            end
          end

        end

      end
    end
  end
end
