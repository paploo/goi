require 'csv'
require 'pathname'

require_relative '../lib/goi'
require_relative '../lib/goi/importer'
require_relative '../lib/goi/exporter'

module Goi
  class Application

    def initialize(config:, importer:, exporter:)
      @config = config
      @importer = importer
      @exporter = exporter
    end

    attr_reader :config, :importer, :exporter

    def run
      linkages = importer.import
      exporter.export(linkages:)
    end

    class Config

      def initialize(infile_path:, db_config:, out_io:)
        @infile_path = infile_path
        @db_config = db_config
        @out_io = out_io
      end

      attr_reader :infile_path, :db_config, :out_io

    end
  end
end

# TODO: Take these as command line arguments
config = Goi::Application::Config.new(
  infile_path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
  db_config: {database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost'},
  out_io: STDOUT
)

importer = Goi::Importer::GoogleSheetImporter.new(config:)
#importer = Goi::Importer::SequelImporter.new(config:)

xporter = Goi::Exporter::GoogleSheetExporter.new(config:)
#exporter = Goi::Exporter::AnkiExporter.new(config:)
#exporter = Goi::Exporter::SqlFileExporter.new(config:)

app = Goi::Application.new(config:, importer:, exporter:)
app.run
