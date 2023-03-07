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
          imported_value = importer.import

          transformed_value = transformers.inject(imported_value) do |value, transformer|
            transformer.transform(value)
          end

          exporters.each do |exporter|
            exporter.export(transformed_value)
          end
        end

      end
    end
  end
end
