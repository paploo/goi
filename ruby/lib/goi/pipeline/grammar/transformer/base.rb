# frozen_string_literal: true

module Goi
  module Pipeline
    module Grammar
      module Transformer
        class Base < Pipeline::Core::Transformer
          def transform(hydrated_rules) = raise NotImplementedError
        end
      end
    end
  end
end
