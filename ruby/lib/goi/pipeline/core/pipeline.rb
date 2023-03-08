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
          log("> Pipeline: #{self}")

          log(">> Importer: #{importer}")
          imported_value = importer.import
          log("<< Importer: #{importer}")

          log(">> Transformers")
          transformed_value = transformers.inject(imported_value) do |value, transformer|
            log(">>> Transformer: #{transformer}")
            result = transformer.transform(value)
            log("<<< Transformer: #{transformer}")
            result
          end
          log("<< Transformers")

          log(">> Exporters")
          exporters.each do |exporter|
            log(">>> Exporter: #{exporter}")
            exporter.export(transformed_value)
            log("<<< Exporter: #{exporter}")
          end
          log("<< Exporters")

          log("< Pipeline: #{self}")
        end

        private

        def logger = $stderr

        def log(message)
          delta_t = Time.now - @start_at
          logger.puts("[Pipeline](#{"%0.03f" % delta_t.to_f} sec) #{message}")
        end

      end
    end
  end
end
