# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      module Library

        def self.default(db_config:,
                         infile_pathname:,
                         output_dir_pathname:)
          Pipeline::Core::Pipeline.new(
            importer: Vocabulary::Importer::GoogleSheetImporter.new(infile_pathname:),
            transformers: [],
            exporters: [
              Vocabulary::Exporter::IOExporter.new(io: $stdout)
            ]
          )
        end

        private

        OUTFILE_NAMES = {
          google_sheet: 'google_sheet.csv',
          anki: 'anki.csv',
          data: 'data.sql'
        }.freeze

        def output_file(output_dir_pathname:, outfile_key:)
          output_dir_pathname + OUTFILE_NAMES[outfile_key]
        end

      end
    end
  end
end
