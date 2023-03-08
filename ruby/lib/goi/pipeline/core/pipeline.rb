# frozen_string_literal: true

module Goi
  module Pipeline
    module Core
      class Pipeline

        def initialize(importer:,
                       transformers: [],
                       exporters: [])
          @importer = importer
          @transformers = transformers
          @exporters = exporters
        end

        attr_reader :importer
        attr_reader :transformers
        attr_reader :exporters

        def run
          @start_at = Time.now
          log("Pipeline Start: #{self}")

          log("Importer Start: #{importer}")
          imported_value = importer.import
          log("Importer Complete: #{importer}")

          transformed_value = transformers.inject(imported_value) do |value, transformer|
            log("Transformer Start: #{transformer}")
            result = transformer.transform(value)
            log("Transformer Complete: #{transformer}")
          end

          exporters.each do |exporter|
            log("Exporter Start: #{exporter}")
            exporter.export(transformed_value)
            log("Exporter Complete: #{exporter}")
          end

          log("Pipeline Complete: #{self}")
        end

        private

        def logger = $stderr

        def log(message)
          delta_t = Time.now - @start_at
          logger.puts("[Pipeline](#{"%0.06f" % delta_t.to_f} sec) #{message}")
        end

      end
    end
  end
end
