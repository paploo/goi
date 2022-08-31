module Goi
  module Transformer
    class BaseTransformer

      def initialize(config:)
        @config = config
      end

      attr_reader :config

      def transform(linkages:)
        # Make no transformation
        linkages
      end

    end
  end
end
