module Goi
  module Exporter
    class BaseExporter

      def initialize(config:)
        @config = config
      end

      attr_reader :config

      def export(linkages:)
        # Do nothing
      end

    end
  end
end
