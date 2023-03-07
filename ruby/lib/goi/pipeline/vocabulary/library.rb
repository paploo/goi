# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      module Library

        # TODO: Move args as a configuration object that can apply to any combination of pipeline steps?
        def self.default(db_config:,
                         infile_pathname:)
          Pipeline::Core::Pipeline.new(
            importer: Vocabulary::Importer::GoogleSheetImporter.new(infile_pathname:),
            transformers: [],
            exporters: [
              Vocabulary::Exporter::IOExporter.new(io: $stdout)
            ]
          )
        end

      end
    end
  end
end
