# frozen_string_literal: true

require_relative 'importer'
require_relative 'transformer'
require_relative 'exporter'

module Goi
  module Pipeline
    module Vocabulary
      module Library

        def self.default(db_config:,
                         infile_pathname:,
                         output_dir_pathname:)
          Pipeline::Core::Pipeline.new(
            importer: Vocabulary::Importer::GoogleSheetImporter.new(infile_pathname:),
            #importer: Vocabulary::Importer::SequelImporter.new(db_config:),
            transformers: [
              Vocabulary::Transformer::DuoLessonCodeTransformer.new,
              Vocabulary::Transformer::ValidationTransformer.new
            ],
            exporters: [
              #Vocabulary::Exporter::IOExporter.new(io: $stdout),
              Vocabulary::Exporter::GoogleSheetExporter.new(outfile_pathname: output_file(output_dir_pathname:, outfile_key: :google_sheet)),
              Vocabulary::Exporter::AnkiExporter.new(outfile_pathname: output_file(output_dir_pathname:, outfile_key: :anki)),
              Vocabulary::Exporter::SqlFileExporter.new(db_config:, outfile_pathname: output_file(output_dir_pathname:, outfile_key: :sql))
            ]
          )
        end

        private

        OUTFILE_NAMES = {
          google_sheet: 'google_sheet.csv',
          anki: 'anki.csv',
          sql: 'data.sql'
        }.freeze

        def self.output_file(output_dir_pathname:, outfile_key:)
          # I don't normally map over a monad in a helper method, but this makes the call points (all in this file) much cleaner.
          if output_dir_pathname.nil?
            nil
          else
            output_dir_pathname + OUTFILE_NAMES[outfile_key]
          end
        end

        private_class_method :output_file

      end
    end
  end
end
