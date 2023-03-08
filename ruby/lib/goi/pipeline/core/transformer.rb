# frozen_string_literal: true

module Goi
  module Pipeline
    module Core
      # Transformers can transform the data but not the types.
      # This allows for extensible plug-n-play and easier semantics, and is safe because of our importer/exporter rules.
      class Transformer
        def transform(value) = raise NotImplementedError
      end
    end
  end
end

