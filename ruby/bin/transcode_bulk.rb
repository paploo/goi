require 'pathname'

require_relative '../lib/goi'
require_relative '../lib/goi/importer'
require_relative '../lib/goi/exporter'
require_relative '../lib/goi/transformer'

module Goi
  module Bin
    module TranscodeBulk
      class Application

        def initialize(config:)
          @config = config
        end

        attr_reader :config

        def run
          # Import
          linkages = config.importer.import

          # Transform
          linkages = config.transformers.inject(linkages) do |lns, tr|
            tr.transform(linkages: lns)
          end

          # Export
          config.exporters.each do |exporter|
            exporter.export(linkages:)
          end
        end

      end

      class Config

        class Phase
          def initialize(klass:, config:)
            @klass = klass
            @config = config
          end

          attr_reader :klass, :config
        end

        def initialize(importer_class:,
                       importer_config:,
                       export_phases:,
                       transform_phases:)
          @importer = importer_class.new(config: importer_config)
          @exporters = export_phases.map { |phase| phase.klass.new(config: phase.config) }
          @transformers = transform_phases.map { |phase| phase.klass.new(config: phase.config) }
        end

        attr_reader :importer, :exporters, :transformers

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
  anki_vocab: Goi::Exporter::AnkiVocabExporter,
  anki_conj: Goi::Exporter::AnkiConjugationExporter,
  sql: Goi::Exporter::SqlFileExporter
}.freeze

TRANSFORMERS = {
  duo_tag: Goi::Transformer::DuoLessonCodeTransformer
}.freeze

args = {
  infile_path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
  db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
  export_phases: [
    { klass: EXPORTERS[:google], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'google_sheet_vocab.csv') },
    { klass: EXPORTERS[:anki_vocab], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'anki_vocab.csv') },
    { klass: EXPORTERS[:anki_conj], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'anki_conj.csv') },
    { klass: EXPORTERS[:sql], path: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'data_vocab.sql') }
  ],
  transform_phases: [
    TRANSFORMERS[:duo_tag]
  ]
}

importer_class = IMPORTERS[:google]
importer_config = Goi::Importer::Config.new(file_pathname: args[:infile_path], db_config: args[:db_config])

export_phases = args[:export_phases].map do |phase_args|
  Goi::Bin::TranscodeBulk::Config::Phase.new(
    klass: phase_args[:klass],
    config: Goi::Exporter::Config.new(file_pathname: phase_args[:path], db_config: args[:db_config])
  )
end

transform_phases = args[:transform_phases].map do |phase_arg|
  Goi::Bin::TranscodeBulk::Config::Phase.new(
    klass: phase_arg,
    config: Goi::Transformer::Config.new
  )
end



config = Goi::Bin::TranscodeBulk::Config.new(importer_class:,
                                             importer_config:,
                                             export_phases:,
                                             transform_phases:)

app = Goi::Bin::TranscodeBulk::Application.new(config:)
app.run
