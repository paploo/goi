require 'csv'

require_relative '../lib/goi'
require_relative '../lib/goi/importer'

module ImportGoogleSheet
  class Application

    def initialize(file_path)
      @file = file_path
      @parser = Goi::Importer::GoogleSheetImporter.new
    end

    attr_reader :file, :parser

    def run()
      rows = CSV.read(file, headers: true).map(&:to_h)
      data = parser.parse(rows:)
      data.each { |link| puts(link.inspect) }
    end

  end
end

file_path = File.expand_path(File.join(File.dirname(__FILE__), '..', '..', 'files', 'new_sheet.csv'))
app = ImportGoogleSheet::Application.new(file_path)
app.run
