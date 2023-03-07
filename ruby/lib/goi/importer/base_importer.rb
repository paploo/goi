module Goi
  module Importer
    class BaseImporter

      def initialize(config:)
        @config = config
      end

      attr_reader :config

      def inport
        # Do Nothing
      end

    end
  end
end
