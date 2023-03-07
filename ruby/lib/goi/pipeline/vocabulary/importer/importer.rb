# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      class Importer < Goi::Pipeline::Core::Importer
        def import = raise NotImplementedError
      end
    end
  end
end
