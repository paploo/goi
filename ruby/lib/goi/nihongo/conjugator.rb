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
                             positive_plain_te: nil,
                             negative_plain_present: nil,
                             negative_plain_past: nil)
        {
          inflection('POSITIVE', 'PLAIN', 'PRESENT') => positive_plain_present,
          inflection('POSITIVE', 'PLAIN', 'PAST') => positive_plain_past,
          inflection('POSITIVE', 'PLAIN', 'TE') => positive_plain_te,

          inflection('NEGATIVE', 'PLAIN', 'PRESENT') => negative_plain_present,
          inflection('NEGATIVE', 'PLAIN', 'PAST') => negative_plain_past
        }.compact
      end

      def self.godan_rule_set(
        う: nil,
        く: nil,
        ぐ: nil,
        す: nil,
        ず: nil,
        つ: nil,
        づ: nil,
        ぬ: nil,
        ふ: nil,
        ぶ: nil,
        ぷ: nil,
        む: nil,
        る: nil
      )
        rules = [
          PatternRule.new(/う$/, う),
          PatternRule.new(/く$/, く),
          PatternRule.new(/ぐ$/, ぐ),
          PatternRule.new(/す$/, す),
          PatternRule.new(/ず$/, ず),
          PatternRule.new(/つ$/, つ),
          PatternRule.new(/づ$/, づ),
          PatternRule.new(/ぬ$/, ぬ),
          PatternRule.new(/ふ$/, ふ),
          PatternRule.new(/ぶ$/, ぶ),
          PatternRule.new(/ぷ$/, ぷ),
          PatternRule.new(/む$/, む),
          PatternRule.new(/る$/, る)
        ].reject { |r| r.replacement.nil? }

        SetRule.new(*rules)
      end

      def self.godan_suffix_rule_set(suffix)
        godan_rule_set(
          う: "い#{suffix}",
          く: "き#{suffix}",
          ぐ: "ぎ#{suffix}",
          す: "し#{suffix}",
          ず: "じ#{suffix}",
          つ: "ち#{suffix}",
          づ: "ぢ#{suffix}",
          ぬ: "に#{suffix}",
          ふ: "ひ#{suffix}",
          ぶ: "び#{suffix}",
          ぷ: "ぴ#{suffix}",
          む: "み#{suffix}",
          る: "り#{suffix}"
        )
      end

      U_ENDING_REGEX = /([うくぐすずつづぬふぶぷむる])$/

      RULES = {

        'GODAN_VERB': verb_set(
          # https://blog.lingodeer.com/japanese-verb-conjugation-guide/
          positive_plain_present: PatternRule.new(U_ENDING_REGEX, '\1'),
          positive_plain_past: godan_rule_set(
            う: 'った',
            く: 'いた',
            ぐ: 'いだ',
            す: 'した',
            ず: nil,
            つ: 'った',
            づ: nil,
            ぬ: 'んだ',
            ふ: nil,
            ぶ: 'んだ',
            ぷ: nil,
            む: 'んだ',
            る: 'った'
          ),
          positive_plain_te: godan_rule_set(
            う: 'って',
            く: 'いて',
            ぐ: 'いで',
            す: 'して',
            ず: nil,
            つ: 'って',
            づ: nil,
            ぬ: 'んで',
            ふ: nil,
            ぶ: 'んで',
            ぷ: nil,
            む: 'んで',
            る: 'って'
            ),

          negative_plain_present: godan_rule_set(
            う: 'わない',
            く: 'かない',
            ぐ: 'がない',
            す: 'さない',
            ず: nil,
            つ: 'たない',
            づ: nil,
            ぬ: 'なない',
            ふ: nil,
            ぶ: 'ばない',
            ぷ: nil,
            む: 'まない',
            る: 'らない'
          ),
          negative_plain_past: godan_rule_set(
            う: 'わなかった',
            く: 'かなかった',
            ぐ: 'がなかった',
            す: 'さなかった',
            ず: nil,
            つ: 'たなかった',
            づ: nil,
            ぬ: 'ななかった',
            ふ: nil,
            ぶ: 'ばなかった',
            ぷ: nil,
            む: 'まなかった',
            る: 'らなかった'
          ),
          negative_plain_te: godan_rule_set(
            う: 'わなくて',
            く: 'かなくて',
            ぐ: 'がなくて',
            す: 'さなくて',
            ず: nil,
            つ: 'たなくて',
            づ: nil,
            ぬ: 'ななくて',
            ふ: nil,
            ぶ: 'ばなくて',
            ぷ: nil,
            む: 'まなくて',
            る: 'らなくて'
          ),

          positive_polite_present: godan_suffix_rule_set('ます'),
          positive_polite_past: godan_suffix_rule_set('ました'),
          negative_polite_present: godan_suffix_rule_set('ません'),
          negative_polite_past: godan_suffix_rule_set('ませんでした')
        ),

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

        'IKU_VERB': verb_set(
          positive_plain_present: PatternRule.new(/([行い])く$/, '\1く'),
          positive_plain_past: PatternRule.new(/([行い])く$/, '\1った'),
          positive_plain_te: PatternRule.new(/([行い])く$/, '\1って'),
          negative_plain_present: PatternRule.new(/([行い])く$/, '\1かない'),
          negative_plain_past: PatternRule.new(/([行い])く$/, '\1かなかった'),
          negative_plain_te: PatternRule.new(/([行い])く$/, '\1かなくて'),

          positive_polite_present: PatternRule.new(/([行い])く$/, '\1きます'),
          positive_polite_past: PatternRule.new(/([行い])く$/, '\1きました'),
          negative_polite_present: PatternRule.new(/([行い])く$/, '\1きません'),
          negative_polite_past: PatternRule.new(/([行い])く$/, '\1きませんでした')
        ),

        'ARU_VERB': verb_set(
          positive_plain_present: PatternRule.new(/ある$/, 'ある'),
          positive_plain_past: PatternRule.new(/ある$/, 'あった'),
          positive_plain_te: PatternRule.new(/ある$/, 'あって'),
          negative_plain_present: PatternRule.new(/ある$/, 'ない'),
          negative_plain_past: PatternRule.new(/ある$/, 'なかった'),
          negative_plain_te: PatternRule.new(/ある$/, 'なくて'),

          positive_polite_present: PatternRule.new(/ある$/, 'あります'),
          positive_polite_past: PatternRule.new(/ある$/, 'ありました'),
          negative_polite_present: PatternRule.new(/ある$/, 'ありません'),
          negative_polite_past: PatternRule.new(/ある$/, 'ありませんでした')
        ),

        'AI_SURU_VERB': verb_set(
          positive_plain_present: PatternRule.new(/する$/, 'する'),
          positive_plain_past: PatternRule.new(/する$/, 'した'),
          positive_plain_te: PatternRule.new(/する$/, 'して'),
          negative_plain_present: PatternRule.new(/する$/, 'さない'),
          negative_plain_past: PatternRule.new(/する$/, 'しなかった'),
          negative_plain_te: PatternRule.new(/する$/, 'しなくて'),

          positive_polite_present: PatternRule.new(/する$/, 'します'),
          positive_polite_past: PatternRule.new(/する$/, 'しました'),
          negative_polite_present: PatternRule.new(/する$/, 'しません'),
          negative_polite_past: PatternRule.new(/する$/, 'しませんでした')
        ),

        'I_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/い$/, 'い'),
          positive_plain_past: PatternRule.new(/い$/, 'かった'),
          positive_plain_te: PatternRule.new(/い$/, 'くて'),
          negative_plain_present: PatternRule.new(/い$/, 'くない'),
          negative_plain_past: PatternRule.new(/い$/, 'くなかった')
        ),

        'NA_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/$/, 'だ'),
          positive_plain_past: PatternRule.new(/$/, 'だった'),
          positive_plain_te: PatternRule.new(/$/, 'で'),
          negative_plain_present: PatternRule.new(/$/, 'じゃない'),
          negative_plain_past: PatternRule.new(/$/, 'じゃなかった')
        ),

        'YOI_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/いい$/, 'いい'),
          positive_plain_past: PatternRule.new(/いい$/, 'よかった'),
          positive_plain_te: PatternRule.new(/いい$/, 'よくて'),
          negative_plain_present: PatternRule.new(/いい$/, 'よくない'),
          negative_plain_past: PatternRule.new(/いい$/, 'よくなかった')
        ),
      }.freeze

    end
  end
end

