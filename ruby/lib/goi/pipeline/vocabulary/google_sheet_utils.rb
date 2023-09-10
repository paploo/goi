# frozen_string_literal: true

module Goi
  module Pipeline
    module Vocabulary
      module GoogleSheetUtils

        # Note: sort_rank is used for if we have multiple of the same form, like if we put in the alternate potential form for ichidan verbs.
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

          { key: 'positive_plain_potential', politeness: 'PLAIN', charge: 'POSITIVE', form: 'POTENTIAL', sort_rank: 1 },
          { key: 'negative_plain_potential', politeness: 'PLAIN', charge: 'NEGATIVE', form: 'POTENTIAL', sort_rank: 1 },
          { key: 'positive_polite_potential', politeness: 'POLITE', charge: 'POSITIVE', form: 'POTENTIAL', sort_rank: 1 },
          { key: 'negative_polite_potential', politeness: 'POLITE', charge: 'NEGATIVE', form: 'POTENTIAL', sort_rank: 1 },
        ].freeze

        CONJUGATION_KEYS = CONJUGATION_KEY_DATA.map { |kd| kd[:key] }

        CONJUGATION_KEY_DATA_MAP = CONJUGATION_KEY_DATA.map { |kd| [kd[:key], kd] }.to_h.freeze

      end
    end
  end
end
