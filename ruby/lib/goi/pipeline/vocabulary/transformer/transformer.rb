# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      class Transformer < Goi::Pipeline::Core::Transformer
        def transform(value) = raise NotImplementedError
      end
    end
  end
end