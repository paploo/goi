module Goi
  module Nihongo
    module Conjugator

      class ConjugationPatternError < RuntimeError
        def initialize(dictionary_spelling:, conjugation_kind_code:, inflection:)
          message = "Expected #{dictionary_spelling} to match rule for #{conjugation_kind_code} with inflection #{inflection}"
          super(message)

          @dictionary_spelling = dictionary_spelling
          @conjugation_kind_code = conjugation_kind_code
          @inflection = inflection
        end

        attr_reader :dictionary_spelling
        attr_reader :conjugation_kind_code
        attr_reader :inflection

      end

      def self.conjugate(dictionary_spelling:, conjugation_kind_code:, inflection:)
        rule = RULES.dig(conjugation_kind_code.to_sym, inflection)
        return nil if rule.nil?

        if rule.matches?(dictionary_spelling)
          rule.call(dictionary_spelling)
        else
          raise ConjugationPatternError.new(dictionary_spelling:, conjugation_kind_code:, inflection:)
        end
      end

      def self.inflection(charge_code, politeness_code, form_code)
        Goi::Model::Vocabulary::Conjugation::Inflection.new(charge_code:, politeness_code:, form_code:)
      end

      class SetRule

        def initialize(*rules)
          @rules = rules
        end

        attr_reader :rules

        def matches?(dictionary_spelling)
          rules.any? do |rule|
            rule.matches?(dictionary_spelling)
          end
        end

        def call(dictionary_spelling)
          rule = rules.find do |rule|
            rule.matches?(dictionary_spelling)
          end

          rule&.call(dictionary_spelling)
        end

      end

      class PatternRule
        def initialize(pattern, replacement)
          @pattern = pattern
          @replacement = replacement
        end

        attr_reader :pattern
        attr_reader :replacement

        def matches?(dictionary_spelling)
          pattern =~ dictionary_spelling
        end

        def call(dictionary_spelling)
          # TODO: Should be able to track when a pattern doesn't match and give an error response.
          dictionary_spelling.sub(pattern, replacement)
        end
      end

      def self.concat_sets(a, b)
        h = Hash.new
        ks = (a.keys + b.keys).uniq
        ks.each do |k|
          h[k] = if a.has_key?(k) && b.has_key?(k)
                   SetRule.new(a[k], b[k])
                 else
                   a[k] || b[k]
                 end
        end
        h
      end

      # Type safe convenience for the standard set of verb conjugations
      def self.verb_set(positive_plain_present: nil,
                        positive_plain_past: nil,
                        positive_plain_te: nil,
                        negative_plain_present: nil,
                        negative_plain_past: nil,
                        negative_plain_te: nil,
                        positive_polite_present: nil,
                        positive_polite_past: nil,
                        negative_polite_present: nil,
                        negative_polite_past: nil)
        {
          inflection('POSITIVE', 'PLAIN', 'PRESENT') => positive_plain_present,
          inflection('POSITIVE', 'PLAIN', 'PAST') => positive_plain_past,
          inflection('POSITIVE', 'PLAIN', 'TE') => positive_plain_te,

          inflection('NEGATIVE', 'PLAIN', 'PRESENT') => negative_plain_present,
          inflection('NEGATIVE', 'PLAIN', 'PAST') => negative_plain_past,
          inflection('NEGATIVE', 'PLAIN', 'TE') => negative_plain_te,

          inflection('POSITIVE', 'POLITE', 'PRESENT') => positive_polite_present,
          inflection('POSITIVE', 'POLITE', 'PAST') => positive_polite_past,

          inflection('NEGATIVE', 'POLITE', 'PRESENT') => negative_polite_present,
          inflection('NEGATIVE', 'POLITE', 'PAST') => negative_polite_past,
        }.compact
      end

      # Type safe convenience for the standard set of adjective conjugations
      def self.adjective_set(positive_plain_present: nil,
                             positive_plain_past: nil,
                             negative_plain_present: nil,
                             negative_plain_past: nil)
        {
          inflection('POSITIVE', 'PLAIN', 'PRESENT') => positive_plain_present,
          inflection('POSITIVE', 'PLAIN', 'PAST') => positive_plain_past,

          inflection('NEGATIVE', 'PLAIN', 'PRESENT') => negative_plain_present,
          inflection('NEGATIVE', 'PLAIN', 'PAST') => negative_plain_past
        }.compact
      end

      RULES = {
        'IRREGULAR_VERB': nil,

        'GODAN_VERB': nil,

        'ICHIDAN_VERB': verb_set(
          positive_plain_present: PatternRule.new(/る$/, 'る'),
          positive_plain_past: PatternRule.new(/る$/, 'た'),
          positive_plain_te: PatternRule.new(/る$/, 'て'),
          negative_plain_present: PatternRule.new(/る$/, 'ない'),
          negative_plain_past: PatternRule.new(/る$/, 'なかった'),
          negative_plain_te: PatternRule.new(/る$/, 'なくて'),

          positive_polite_present: PatternRule.new(/る$/, 'ます'),
          positive_polite_past: PatternRule.new(/る$/, 'ました'),
          negative_polite_present: PatternRule.new(/る$/, 'ません'),
          negative_polite_past: PatternRule.new(/る$/, 'ませんでした')
        ),

        'SURU_VERB': verb_set(
          positive_plain_present: PatternRule.new(/する$/, 'する'),
          positive_plain_past: PatternRule.new(/する$/, 'した'),
          positive_plain_te: PatternRule.new(/する$/, 'して'),
          negative_plain_present: PatternRule.new(/する$/, 'しない'),
          negative_plain_past: PatternRule.new(/する$/, 'しなかった'),
          negative_plain_te: PatternRule.new(/する$/, 'しなくて'),

          positive_polite_present: PatternRule.new(/する$/, 'します'),
          positive_polite_past: PatternRule.new(/する$/, 'しました'),
          negative_polite_present: PatternRule.new(/する$/, 'しません'),
          negative_polite_past: PatternRule.new(/する$/, 'しませんでした')
        ),

        'KURU_VERB': verb_set(
          positive_plain_present: PatternRule.new(/くる$/, 'くる'),
          positive_plain_past: PatternRule.new(/くる$/, 'きた'),
          positive_plain_te: PatternRule.new(/くる$/, 'きて'),
          negative_plain_present: PatternRule.new(/くる$/, 'こない'),
          negative_plain_past: PatternRule.new(/くる$/, 'こなかった'),
          negative_plain_te: PatternRule.new(/くる$/, 'こなくて'),

          positive_polite_present: PatternRule.new(/くる$/, 'きます'),
          positive_polite_past: PatternRule.new(/くる$/, 'きました'),
          negative_polite_present: PatternRule.new(/くる$/, 'きません'),
          negative_polite_past: PatternRule.new(/くる$/, 'きませんでした')
        ),

        'IRREGULAR_ADJECTIVE': nil,

        'I_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/い$/, 'い'),
          positive_plain_past: PatternRule.new(/い$/, 'かった'),
          negative_plain_present: PatternRule.new(/い$/, 'くない'),
          negative_plain_past: PatternRule.new(/い$/, 'くなかった')
        ),

        'NA_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/$/, 'だ'),
          positive_plain_past: PatternRule.new(/$/, 'だった'),
          negative_plain_present: PatternRule.new(/$/, 'じゃない'),
          negative_plain_past: PatternRule.new(/$/, 'じゃなかった')
        ),

        'YOI_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/いい$/, 'いい'),
          positive_plain_past: PatternRule.new(/いい$/, 'よかった'),
          negative_plain_present: PatternRule.new(/いい$/, 'よくない'),
          negative_plain_past: PatternRule.new(/いい$/, 'よくなかった')
        ),
      }.freeze

    end
  end
end

