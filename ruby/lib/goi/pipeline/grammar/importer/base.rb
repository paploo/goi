# frozen_string_literal: true

module Goi
  module Pipeline
    module Grammar
      module Importer
        class Base < Pipeline::Core::Importer
          def import = raise NotImplementedError
        end
      end
    end
  end
end

