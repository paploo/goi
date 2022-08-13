require 'pathname'

require_relative '../lib/goi'
require_relative '../lib/goi/importer'
require_relative '../lib/goi/exporter'

module Goi
  module Bin
    module TranscodeBulk
      class Application

        def initialize(config:)
          @config = config
        end

        attr_reader :config

        def run
          linkages = config.importer.import
          config.exporters.each do |exporter|
            exporter.export(linkages:)
          end
        end

      end

      class Config

        class ExportPhase
          def initialize(exporter_class:, exporter_config:)
            @exporter_class = exporter_class
            @exporter_config = exporter_config
          end

          attr_reader :exporter_class, :exporter_config
        end

        def initialize(importer_class:, importer_config:, export_phases:)
          @importer = importer_class.new(config: importer_config)
          @exporters = export_phases.map { |phase| phase.exporter_class.new(config: phase.exporter_config) }
        end

        attr_reader :importer, :exporters

      end

    end
  end
end

IMPORTERS = {
  google: Goi::Importer::GoogleSheetImporter,
  sequel: Goi::Importer::SequelImporter
}.freeze

EXPORTERS = {
  google: Goi::Exporter::GoogleSheetExporter,
  anki: Goi::Exporter::AnkiExporter,
  sql: Goi::Exporter::SqlFileExporter
}.freeze

args = {
  infile_path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
  db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
  export_phases: [
    { klass: EXPORTERS[:google], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'google_sheet.csv') },
    { klass: EXPORTERS[:anki], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'anki.csv') },
    { klass: EXPORTERS[:sql], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'data.sql') }
  ]
}

importer_class = IMPORTERS[:google]
importer_config = Goi::Importer::Config.new(file_pathname: args[:infile_path], db_config: args[:db_config])

export_phases = args[:export_phases].map do |phase_args|
  Goi::Bin::TranscodeBulk::Config::ExportPhase.new(
    exporter_class: phase_args[:klass],
    exporter_config: Goi::Exporter::Config.new(file_pathname: phase_args[:path], db_config: args[:db_config])
  )
end

config = Goi::Bin::TranscodeBulk::Config.new(importer_class:,
                                             importer_config:,
                                             export_phases:)

app = Goi::Bin::TranscodeBulk::Application.new(config:)
app.run
