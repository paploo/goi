require 'pathname'

require_relative '../lib/goi'
require_relative '../lib/goi/importer'
require_relative '../lib/goi/exporter'

module Goi
  module Bin
    module Transcode
      class Application

        def initialize(config:)
          @config = config
        end

        attr_reader :config

        def run
          linkages = config.importer.import
          config.exporter.export(linkages:)
        end

      end

      class Config

        def initialize(importer_class:, importer_config:, exporter_class:, exporter_config:)
          @importer = importer_class.new(config: importer_config)
          @exporter = exporter_class.new(config: exporter_config)
        end

        attr_reader :importer, :exporter

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
  outfile_path: nil
}

importer_class = IMPORTERS[:google]
importer_config = Goi::Importer::Config.new(file_pathname: args[:infile_path], db_config: args[:db_config])
exporter_class = EXPORTERS[:google]
exporter_config = Goi::Exporter::Config.new(file_pathname: args[:outfile_path], db_config: args[:db_config])

config = Goi::Bin::Transcode::Config.new(importer_class:,
                                          exporter_class:,
                                          importer_config:,
                                          exporter_config:)

app = Goi::Bin::Transcode::Application.new(config:)
app.run