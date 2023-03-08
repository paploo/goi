# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      module Transformer
        class Base < Pipeline::Core::Transformer
          def transform(linkages) = raise NotImplementedError
        end
      end
    end
  end
end