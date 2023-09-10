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

      # Looks through the given list of rules for the first match, and applies it.
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

      # Uses a pattern to match the verb (or a portion of it) and give a replacement).
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

      # Sequences the output of one rule as the input to the next rule.
      class ChainRule

        def initialize(*rules)
          @rules = rules
        end

        attr_reader :rules

        def matches?(dictionary_spelling)
          # TODO: This is a good argument for refactoring to have rules return a wrapped value of either Matched(foo) or Unmatched
          # Can only really know as we go along, so we basically have to evaluate each step and see if it still matches.
          current_spelling = dictionary_spelling
          rules.each do |rule|
            if rule.matches?(current_spelling)
              current_spelling = rule.call(current_spelling)
            else
              return false
            end
          end

          true
        end

        def call(dictionary_spelling)
          rules.inject(dictionary_spelling) do |current_spelling, rule|
            rule.call(current_spelling)
          end
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
      def self.verb_set(positive_plain_present:,
                        positive_plain_past:,
                        positive_plain_te:,
                        negative_plain_present:,
                        negative_plain_past:,
                        negative_plain_te:,

                        positive_polite_present:,
                        positive_polite_past:,
                        positive_polite_te: nil,
                        negative_polite_present:,
                        negative_polite_past:,
                        # Potential forms all conjugate as ichidan verbs, so we give that default here.
                        positive_plain_potential:,
                        negative_plain_potential: positive_plain_potential && ChainRule.new(positive_plain_potential, PatternRule.new(/る$/, 'ない')),
                        positive_polite_potential: positive_plain_potential && ChainRule.new(positive_plain_potential, PatternRule.new(/る$/, 'ます')),
                        negative_polite_potential: positive_plain_potential && ChainRule.new(positive_plain_potential, PatternRule.new(/る$/, 'ません')))
        {
          inflection('POSITIVE', 'PLAIN', 'PRESENT') => positive_plain_present,
          inflection('POSITIVE', 'PLAIN', 'PAST') => positive_plain_past,
          inflection('POSITIVE', 'PLAIN', 'TE') => positive_plain_te,

          inflection('NEGATIVE', 'PLAIN', 'PRESENT') => negative_plain_present,
          inflection('NEGATIVE', 'PLAIN', 'PAST') => negative_plain_past,
          inflection('NEGATIVE', 'PLAIN', 'TE') => negative_plain_te,

          inflection('POSITIVE', 'POLITE', 'PRESENT') => positive_polite_present,
          inflection('POSITIVE', 'POLITE', 'PAST') => positive_polite_past,
          inflection('POSITIVE', 'POLITE', 'TE') => positive_polite_te,

          inflection('NEGATIVE', 'POLITE', 'PRESENT') => negative_polite_present,
          inflection('NEGATIVE', 'POLITE', 'PAST') => negative_polite_past,

          inflection('POSITIVE', 'PLAIN', 'POTENTIAL') => positive_plain_potential,
          inflection('NEGATIVE', 'PLAIN', 'POTENTIAL') => negative_plain_potential,
          inflection('POSITIVE', 'POLITE', 'POTENTIAL') => positive_polite_potential,
          inflection('NEGATIVE', 'POLITE', 'POTENTIAL') => negative_polite_potential,
        }.compact
      end

      # Type safe convenience for the standard set of adjective conjugations
      def self.adjective_set(positive_plain_present:,
                             positive_plain_past:,
                             positive_plain_te:,
                             negative_plain_present:,
                             negative_plain_past:,
                             negative_plain_te:)
        {
          inflection('POSITIVE', 'PLAIN', 'PRESENT') => positive_plain_present,
          inflection('POSITIVE', 'PLAIN', 'PAST') => positive_plain_past,
          inflection('POSITIVE', 'PLAIN', 'TE') => positive_plain_te,

          inflection('NEGATIVE', 'PLAIN', 'PRESENT') => negative_plain_present,
          inflection('NEGATIVE', 'PLAIN', 'PAST') => negative_plain_past,
          inflection('NEGATIVE', 'PLAIN', 'TE') => negative_plain_te,
        }.compact
      end

      def self.godan_rule_set(
        う:,
        く:,
        ぐ:,
        す:,
        ず:,
        つ:,
        づ:,
        ぬ:,
        ふ:,
        ぶ:,
        ぷ:,
        む:,
        る:
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

      #TODO: Now that we know the shape better, redo to be more modular with more type safety around:
      #   1. Having a definition for a type,
      #   2. Having each inflection have to be defined, and
      #   3. Have a conjugator for each block that can do one of many things, and returnes either a Some(conjugated), or a None.
      # Some conjugators we might want:
      #   - Pattern conjugator
      #   - A conjugator for godan verbs that matches on the ending verb and substitutes according to the returned value.
      #   - The ability to delegate to an existing conjugator.
      #   - The ability to take the output of one conjugator and turn it into the input to another (would make potential verbs and other derived forms easier)
      # To make this last part work, we probably need to divide things differently?
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
          negative_polite_past: godan_suffix_rule_set('ませんでした'),

          positive_plain_potential: godan_rule_set(
            う: 'える',
            く: 'ける',
            ぐ: 'げる',
            す: 'せる',
            ず: nil,
            つ: 'てる',
            づ: nil,
            ぬ: 'ねる',
            ふ: nil,
            ぶ: 'べる',
            ぷ: nil,
            む: 'める',
            る: 'れる',
            ),
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
          negative_polite_past: PatternRule.new(/る$/, 'ませんでした'),

          positive_plain_potential: PatternRule.new(/る$/, 'られる')
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
          negative_polite_past: PatternRule.new(/する$/, 'しませんでした'),

          positive_plain_potential: PatternRule.new(/する$/, 'できる')
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
          negative_polite_past: PatternRule.new(/くる$/, 'きませんでした'),

          positive_plain_potential: PatternRule.new(/くる$/, 'こられる')
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
          negative_polite_past: PatternRule.new(/([行い])く$/, '\1きませんでした'),

          positive_plain_potential: PatternRule.new(/([行い])く$/, '\1ける')
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
          negative_polite_past: PatternRule.new(/ある$/, 'ありませんでした'),

          positive_plain_potential: PatternRule.new(/ある$/, 'ありえる')
        ),

        'AI_SURU_VERB': verb_set(
          positive_plain_present: PatternRule.new(/する$/, 'する'),
          positive_plain_past: PatternRule.new(/する$/, 'した'),
          positive_plain_te: PatternRule.new(/する$/, 'して'),
          negative_plain_present: PatternRule.new(/する$/, 'さない'), # This one is different than する.
          negative_plain_past: PatternRule.new(/する$/, 'しなかった'),
          negative_plain_te: PatternRule.new(/する$/, 'しなくて'),

          positive_polite_present: PatternRule.new(/する$/, 'します'),
          positive_polite_past: PatternRule.new(/する$/, 'しました'),
          negative_polite_present: PatternRule.new(/する$/, 'しません'),
          negative_polite_past: PatternRule.new(/する$/, 'しませんでした'),

          positive_plain_potential: PatternRule.new(/する$/, 'できる')
        ),

        'COPULA_VERB': verb_set(
          positive_plain_present: PatternRule.new(/だ$/, 'だ'),
          positive_plain_past: PatternRule.new(/だ$/, 'だった'),
          positive_plain_te: PatternRule.new(/だ$/, 'で'),
          negative_plain_present: PatternRule.new(/だ$/, 'じゃない'),
          negative_plain_past: PatternRule.new(/だ$/, 'じゃなかった'),
          negative_plain_te: nil,

          positive_polite_present: PatternRule.new(/だ$/, 'です'),
          positive_polite_past: PatternRule.new(/だ$/, 'でした'),
          positive_polite_te: PatternRule.new(/だ$/, 'でありまして'),
          negative_polite_present: PatternRule.new(/だ$/, 'じゃないです'),
          negative_polite_past: PatternRule.new(/だ$/, 'じゃないかったです'),

          positive_plain_potential: nil
        ),

        'I_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/い$/, 'い'),
          positive_plain_past: PatternRule.new(/い$/, 'かった'),
          positive_plain_te: PatternRule.new(/い$/, 'くて'),
          negative_plain_present: PatternRule.new(/い$/, 'くない'),
          negative_plain_past: PatternRule.new(/い$/, 'くなかった'),
          negative_plain_te: PatternRule.new(/い$/, 'くなくて'),
        ),

        'NA_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/$/, 'だ'),
          positive_plain_past: PatternRule.new(/$/, 'だった'),
          positive_plain_te: PatternRule.new(/$/, 'で'),
          negative_plain_present: PatternRule.new(/$/, 'じゃない'),
          negative_plain_past: PatternRule.new(/$/, 'じゃなかった'),
          negative_plain_te: PatternRule.new(/$/, 'ではなくて'),
        ),

        'YOI_ADJECTIVE': adjective_set(
          positive_plain_present: PatternRule.new(/いい$/, 'いい'),
          positive_plain_past: PatternRule.new(/いい$/, 'よかった'),
          positive_plain_te: PatternRule.new(/いい$/, 'よくて'),
          negative_plain_present: PatternRule.new(/いい$/, 'よくない'),
          negative_plain_past: PatternRule.new(/いい$/, 'よくなかった'),
          negative_plain_te: PatternRule.new(/いい$/, 'よくなくて'),
        ),
      }.freeze

    end
  end
end

