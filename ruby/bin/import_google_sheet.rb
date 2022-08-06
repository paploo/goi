require 'csv'
require 'pathname'

require_relative '../lib/goi'
require_relative '../lib/goi/importer'
require_relative '../lib/goi/exporter'

module ImportGoogleSheet
  class Application

    def initialize(file_path:, importer:, exporter:)
      @file_path = Pathname(file_path)
      @importer = importer
      @exporter = exporter
    end

    attr_reader :file_path, :importer, :exporter

    def run
      rows = CSV.read(file_path.to_s, headers: true).map(&:to_h)
      linkages = importer.parse(rows)
      exporter.export(linkages:)
    end

  end
end

# TODO: Take these as command line arguments
file_path = Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'new_sheet.csv')
importer = Goi::Importer::GoogleSheetImporter.new
exporter = Goi::Exporter::IOExporter.new

app = ImportGoogleSheet::Application.new(file_path:, importer:, exporter:)
app.run
