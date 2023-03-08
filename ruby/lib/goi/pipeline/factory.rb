# frozen_string_literal: true

require_relative 'vocabulary/importer'
require_relative 'vocabulary/transformer'
require_relative 'vocabulary/exporter'

require_relative 'grammar/importer'
require_relative 'grammar/transformer'
require_relative 'grammar/exporter'

module Goi
  module Pipeline

    class Factory

      class Config
        def initialize(db_config:, infile_pathname:, output_dir_pathname:)
          @db_config = db_config
          @infile_pathname = infile_pathname
          @output_dir_pathname = output_dir_pathname
        end

        attr_reader :db_config
        attr_reader :infile_pathname
        attr_reader :output_dir_pathname
      end

      def initialize(config:)
        @config = config
      end

      attr_reader :config

      def vocabulary_pipeline
        db_config = config.db_config
        infile_pathname = config.infile_pathname
        output_dir_pathname = config.output_dir_pathname

        Pipeline::Core::Pipeline.new(
          importer: Vocabulary::Importer::GoogleSheetImporter.new(infile_pathname:),
          #importer: Vocabulary::Importer::SequelImporter.new(db_config:),
          transformers: [
            Vocabulary::Transformer::DuoLessonCodeTransformer.new,
            Vocabulary::Transformer::ValidationTransformer.new
          ],
          exporters: [
            #Vocabulary::Exporter::IOExporter.new(io: $stdout),
            Vocabulary::Exporter::GoogleSheetExporter.new(outfile_pathname: outfile_path(output_dir_pathname, :vocabulary, :google_sheet)),
            Vocabulary::Exporter::SqlFileExporter.new(db_config:, outfile_pathname: outfile_path(output_dir_pathname, :vocabulary, :sql)),
            Vocabulary::Exporter::SequelExporter.new(db_config:),
            Vocabulary::Exporter::AnkiExporter.new(outfile_pathname: outfile_path(output_dir_pathname, :vocabulary, :anki))
          ]
        )
      end

      def grammar_pipeline
        db_config = config.db_config
        infile_pathname = config.infile_pathname
        output_dir_pathname = config.output_dir_pathname

        Pipeline::Core::Pipeline.new(
          importer: Grammar::Importer::JSONImporter.new(infile_pathname:),
          transformers: [],
          exporters: [
            #Grammar::Exporter::IOExporter.new(io: $stdout)
            Grammar::Exporter::SequelExporter.new(db_config:)
          ]
        )
      end

      private

      OUTFILE_NAMES = {
        vocabulary: {
          google_sheet: 'google_sheet.csv',
          anki: 'anki.csv',
          sql: 'data.sql'
        }
      }.freeze

      def outfile_path(output_dir_pathname, namespace, key)
        # I don't normally map over a monad in a helper method, but this makes the call points (all in this file) much cleaner.
        if output_dir_pathname.nil?
          nil
        else
          file_name = OUTFILE_NAMES[namespace] && OUTFILE_NAMES[namespace][key]
          raise ArgumentError("No outfile path for namespace: #{namespace} and key: #{key}") if file_name.nil?
          output_dir_pathname + file_name
        end
      end

    end

  end
end
