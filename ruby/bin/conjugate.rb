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
          compute_conjugations.each do |inflection_set|
            output_inflection_set(inflection_set:, io: config.io)
          end
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

        def output_inflection_set(inflection_set:, io:)
          #TODO: Use CSV output for each row that can be copy/pasted into GoogleSheets?
          io.puts(inflection_set.dictionary_spelling)
          inflection_set.conjugations.each do |k, v|
            puts("#{k.code}: #{v}") unless v.nil?
          end
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

          # DEFAULT_INFLECTIONS = [
          #   {charge_code: }
          # ].map { |keys| Goi::Model::Vocabulary::Conjugation::Inflection.new(**keys) }.freeze

          DEFAULT_INFLECTIONS = Goi::Model::Vocabulary::Conjugation::Inflection.all

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
  # conjugation_kind_code: "ICHIDAN_VERB",
  # dictionary_spellings: ["食べる", "出かける"]
  #
  conjugation_kind_code: "AI_SURU_VERB",
  dictionary_spellings: ["愛する"]
)

Goi::Bin::Conjugate::Application.new(config:).run
