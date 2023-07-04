# frozen_string_literal: true

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

#TODO: Instantiate using command line args
#TODO: Use option parser to do that
config = Goi::Bin::Conjugate::Application::Config.new(
  conjugation_kind_code: "ICHIDAN_VERB",
  dictionary_spellings: ["食べる", "出かける"]
  #
  # conjugation_kind_code: "AI_SURU_VERB",
  # dictionary_spellings: ["愛する"]
)

Goi::Bin::Conjugate::Application.new(config:).run
