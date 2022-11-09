module Goi
  module Nihongo
    module Conjugator

      def self.conjugate(dictionary_spelling:, conjugation_kind_code:, inflection:)
        rule = RULES.dig(conjugation_kind_code.to_sym, inflection)
        rule&.call(dictionary_spelling)
      end

      def self.inflection(charge_code, politeness_code, form_code)
        Goi::Model::Vocabulary::Conjugation::Inflection.new(charge_code:, politeness_code:, form_code:)
      end

      class RuleSet

        def initialize(*rules)
          @rules = rules
        end

        attr_reader :rules

        def call(dictionary_spelling)
          rule = rules.find do |rule|
            rule.pattern =~ dictionary_spelling
          end

          rule&.call(dictionary_spelling)
        end

      end

      class Rule
        def initialize(pattern, replacement)
          @pattern = pattern
          @replacement = replacement
        end

        attr_reader :pattern
        attr_reader :replacement

        def call(dictionary_spelling)
          dictionary_spelling.sub(pattern, replacement)
        end
      end

      RULES = {
        'IRREGULAR_VERB': nil,
        'GODAN_VERB': nil,
        'ICHIDAN_VERB': {
          inflection('POSITIVE', 'POLITE', 'PRESENT') => RuleSet.new(
            Rule.new(/る$/, 'ます')
          ),
          inflection('POSITIVE', 'POLITE', 'PAST') => RuleSet.new(
            Rule.new(/る$/, 'ました')
          )
        },
        'SURU_VERB': nil,
        'KURU_VERB': nil,

        'IRREGULAR_ADJECTIVE': nil,
        'I_ADJECTIVE': nil,
        'YOI_ADJECTIVE': nil,
        'NA_ADJECTIVE': nil,
      }.freeze

    end
  end
end
