# frozen_string_literal: true

require_relative '../../vocabulary'

module Goi
  module Pipeline
    module Vocabulary
      module Transformer

        class AutoConjugationTransformer < Vocabulary::Transformer::Base

          def transform(linkages)
            linkages.map do |ln|
              # If it isn't conjugable, skip it.
              if ln.vocabulary.conjugation_kind_code.nil?
                # Do nothing
                ln
              else
                # Transform
                conjugation_set = transform_conjugation_set(
                  dictionary_spelling: ln.preferred_spelling.value,
                  conjugation_kind_code: ln.vocabulary.conjugation_kind_code,
                  conjugation_set: ln.conjugation_set || empty_conjugation_set(vocabulary_id: ln.vocabulary.id)
                )
                ln.copy(conjugation_set:)
              end
            end
          end

          private

          # def ensure_set(dictionary_spelling:, vocabulary_id:, conjugation_kind_code:, conjugation_set:)
          #   if conjugation_set.nil?
          #     empty_conjugation_set(vocabulary_id:)
          #   else
          #     transform_conjugation_set(dictionary_spelling:, conjugation_kind_code:, conjugation_set:)
          #   end
          # end

          def empty_conjugation_set(vocabulary_id:)
            Goi::Model::Vocabulary::ConjugationSet.new(
              id: Goi::Model::Vocabulary::ConjugationSet.create_id(vocabulary_id:),
              vocabulary_id:,
              conjugations: []
            )
          end

          def transform_conjugation_set(dictionary_spelling:, conjugation_kind_code:, conjugation_set:)
            new_conjugations = transform_conjugations(conjugation_set:, dictionary_spelling:, conjugation_kind_code:)

            Goi::Model::Vocabulary::ConjugationSet.new(
              id: conjugation_set.id,
              vocabulary_id: conjugation_set.vocabulary_id,
              conjugations: new_conjugations
            )
          end

          def transform_conjugations(conjugation_set:, dictionary_spelling:, conjugation_kind_code:)
            Goi::Model::Vocabulary::Conjugation::Inflection.all.flat_map do |inflection|
              original = select_all_for_inflection(conjugation_set:, inflection:)

              # If we already have one or more matching inflections, keep those; only add one if there wasn't anything there.
              if original.empty?
                derived = derive_spelling(dictionary_spelling:, conjugation_kind_code:, inflection:, conjugation_set_id: conjugation_set.id)
                derived.nil? ? [] : [derived]
              else
                original
              end
            end
          end

          def derive_spelling(dictionary_spelling:, conjugation_kind_code:, inflection:, conjugation_set_id:)
            derived_spelling = Goi::Nihongo::Conjugator.conjugate(dictionary_spelling:, conjugation_kind_code:, inflection:)

            return nil if derived_spelling.nil?

            politeness_code = inflection.politeness_code
            charge_code = inflection.charge_code
            form_code = inflection.form_code
            sort_rank = 1

            Goi::Model::Vocabulary::Conjugation.new(
              id: Goi::Model::Vocabulary::Conjugation.create_id(conjugation_set_id:, politeness_code:, charge_code:, form_code:, sort_rank:),
              conjugation_set_id:,
              politeness_code:,
              charge_code:,
              form_code:,
              sort_rank:,
              value: derived_spelling
            )
          end

          def select_all_for_inflection(conjugation_set:, inflection:)
            conjugation_set.conjugations.select { |elem| elem.inflection == inflection}.sort_by(&:sort_rank)
          end

        end

      end
    end
  end
end
