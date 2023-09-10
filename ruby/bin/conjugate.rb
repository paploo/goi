# frozen_string_literal: true

require 'optparse'

require_relative '../lib/goi/model'
require_relative '../lib/goi/nihongo'

module Goi
  module Bin
    module Conjugate

      class Application

        def initialize(config:)
          @config = config
        end

        attr_reader :config

        def run
          inflection_sets = compute_conjugations
          io = config.io
          output_inflection_sets(inflection_sets:, io:)
        end

        private

        def compute_conjugations
          config.dictionary_spellings.map do |dictionary_spelling|
            conjugation_kind_code = config.conjugation_kind_code
            conjugations = config.inflections.map do |inflection|
              conjugated = Goi::Nihongo::Conjugator.conjugate(dictionary_spelling:, conjugation_kind_code:, inflection:)
              [inflection, conjugated]
            end.to_h
            InflectionSet.new(dictionary_spelling:, conjugations:)
          end
        end

        def output_inflection_sets(inflection_sets:, io:)
          output_inflection_header(io:)
          inflection_sets.each { |inflection_set| output_inflection_set(inflection_set:, io:) }
        end

        def output_inflection_set(inflection_set:, io:)
          conjugations = inflection_set.conjugations
          output_conjugations(conjugations:, io:)
        end

        def output_inflection_header(io:)
          header = config.inflections.map(&:code).join("\t")
          io.puts(header)
        end

        def output_conjugations(conjugations:, io:)
          values = config.inflections.map { |infl| conjugations[infl] }.join("\t")
          io.puts(values)
        end

        class InflectionSet

          def initialize(dictionary_spelling:, conjugations:)
            @dictionary_spelling = dictionary_spelling
            @conjugations = conjugations
          end

          attr_reader :dictionary_spelling
          attr_reader :conjugations

        end

        class Config

          ALL_INFLECTIONS = Goi::Model::Vocabulary::Conjugation::Inflection.all

          DEFAULT_INFLECTIONS = [
            { charge_code: 'POSITIVE', politeness_code: 'PLAIN', form_code: 'PRESENT' },
            { charge_code: 'POSITIVE', politeness_code: 'PLAIN', form_code: 'PAST' },
            { charge_code: 'POSITIVE', politeness_code: 'PLAIN', form_code: 'TE' },

            { charge_code: 'NEGATIVE', politeness_code: 'PLAIN', form_code: 'PRESENT' },
            { charge_code: 'NEGATIVE', politeness_code: 'PLAIN', form_code: 'PAST' },
            { charge_code: 'NEGATIVE', politeness_code: 'PLAIN', form_code: 'TE' },

            { charge_code: 'POSITIVE', politeness_code: 'POLITE', form_code: 'PRESENT' },
            { charge_code: 'POSITIVE', politeness_code: 'POLITE', form_code: 'PAST' },
            { charge_code: 'POSITIVE', politeness_code: 'POLITE', form_code: 'TE' },

            { charge_code: 'NEGATIVE', politeness_code: 'POLITE', form_code: 'PRESENT' },
            { charge_code: 'NEGATIVE', politeness_code: 'POLITE', form_code: 'PAST' },
            { charge_code: 'NEGATIVE', politeness_code: 'POLITE', form_code: 'TE' },

            { charge_code: 'POSITIVE', politeness_code: 'PLAIN', form_code: 'POTENTIAL' },
            { charge_code: 'NEGATIVE', politeness_code: 'PLAIN', form_code: 'POTENTIAL' },
            { charge_code: 'POSITIVE', politeness_code: 'POLITE', form_code: 'POTENTIAL' },
            { charge_code: 'NEGATIVE', politeness_code: 'POLITE', form_code: 'POTENTIAL' },
          ].map { |keys| Goi::Model::Vocabulary::Conjugation::Inflection.new(**keys) }.freeze

          def initialize(dictionary_spellings:, conjugation_kind_code:, inflections: nil)
            @dictionary_spellings = dictionary_spellings
            @conjugation_kind_code = conjugation_kind_code
            @inflections = inflections || DEFAULT_INFLECTIONS
          end

          attr_reader :dictionary_spellings
          attr_reader :conjugation_kind_code
          attr_reader :inflections

          def io
            STDOUT
          end


        end

      end

    end
  end
end

def parse_args
  args = {}

  # A little shared setup
  legal_kinds = Goi::Nihongo::Conjugator::RULES.keys.map(&:to_s)

  # First we opt-parse the options.
  OptionParser.new do |opts|
    opts.banner = "Usage: conjugate.rb [options] word1 word2"

    opts.on("-c", "--conj KIND", "Supply the conjugation kind code. Legal values include:", "\t#{legal_kinds.join(', ')}") do |c|
      kind = c&.upcase&.tr('-', '_')
      args[:conjugation_kind_code] = kind
    end

    opts.on("-h", "--help", "Show Help") do
      puts opts
      exit(0)
    end
  end.parse!

  # The remaining arguments are not options, but rather the words
  args[:dictionary_spellings] = ARGV

  # Do some validation
  if args[:conjugation_kind_code].nil? || !legal_kinds.include?(args[:conjugation_kind_code])
    raise "Expected kind code #{args[:conjugation_kind_code].inspect} to be one of #{legal_kinds}"
  end

  args
end

config = Goi::Bin::Conjugate::Application::Config.new(**parse_args)

Goi::Bin::Conjugate::Application.new(config:).run
