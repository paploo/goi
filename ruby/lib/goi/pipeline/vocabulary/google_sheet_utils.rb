# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      module GoogleSheetUtils

        CONJUGATION_KEY_DATA = [
          { key: 'dictionary_form', politeness: 'PLAIN', charge: 'POSITIVE', form: 'PRESENT', sort_rank: 1 },
          { key: 'past_form', politeness: 'PLAIN', charge: 'POSITIVE', form: 'PAST', sort_rank: 1 },
          { key: 'te_form', politeness: 'PLAIN', charge: 'POSITIVE', form: 'TE', sort_rank: 1 },
          { key: 'negative_form', politeness: 'PLAIN', charge: 'NEGATIVE', form: 'PRESENT', sort_rank: 1 },
          { key: 'negative_past_form', politeness: 'PLAIN', charge: 'NEGATIVE', form: 'PAST', sort_rank: 1 },
          { key: 'negative_te_form', politeness: 'PLAIN', charge: 'NEGATIVE', form: 'TE', sort_rank: 1 },
          { key: 'polite_form', politeness: 'POLITE', charge: 'POSITIVE', form: 'PRESENT', sort_rank: 1 },
          { key: 'polite_past_form', politeness: 'POLITE', charge: 'POSITIVE', form: 'PAST', sort_rank: 1 },
          { key: 'polite_te_form', politeness: 'POLITE', charge: 'POSITIVE', form: 'TE', sort_rank: 1 },
          { key: 'polite_negative_form', politeness: 'POLITE', charge: 'NEGATIVE', form: 'PRESENT', sort_rank: 1 },
          { key: 'polite_negative_past_form', politeness: 'POLITE', charge: 'NEGATIVE', form: 'PAST', sort_rank: 1 },
          { key: 'polite_negative_te_form', politeness: 'POLITE', charge: 'NEGATIVE', form: 'TE', sort_rank: 1 },
        ].freeze

        CONJUGATION_KEYS = CONJUGATION_KEY_DATA.map { |kd| kd[:key] }

        CONJUGATION_KEY_DATA_MAP = CONJUGATION_KEY_DATA.map { |kd| [kd[:key], kd] }.to_h.freeze

      end
    end
  end
end
