module Goi
  module Pipeline
    module Vocabulary
      class Exporter < Goi::Pipeline::Core::Exporter
        def export(value) = raise NotImplementedError
      end
    end
  end
end
