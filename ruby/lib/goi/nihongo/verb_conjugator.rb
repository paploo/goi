module Goi
  module Nihongo
    module VerbConjugator

      def self.conjugate(dictionary_spelling:, conjugation_kind_code:, charge_code:, politeness_code:, form_code:)
        raise NotImplementedError
      end

    end
  end
end
