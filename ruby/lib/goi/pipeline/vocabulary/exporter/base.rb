module Goi
  module Pipeline
    module Vocabulary
      module Exporter
        class Base < Pipeline::Core::Exporter
          def export(value) = raise NotImplementedError
        end
      end
    end
  end
end
