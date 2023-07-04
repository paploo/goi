# frozen_string_literal: true

require_relative '../../../core/validation_messages'
require_relative '../../../core/extensions'

module Goi
  module Pipeline
    module Vocabulary
      module Transformer

        class ValidationTransformer < Vocabulary::Transformer::Base

          def transform(linkages)
            # Validation rules
            report = report(linkages:)

            # Report results
            io.puts(report.formatted)
            io.puts("(#{report.formatted_summary})")

            # Halt if we have an error!
            error_count = report.count(level: :error)
            raise RuntimeError, "Validation step failed with #{error_count} errors" if error_count.positive?

            # Return linkages unchanged
            linkages
          end

          private

          def io = $stderr

          def report(linkages:)
            Goi::Core::ValidationReport.new(
              title: 'Validation Report',
              messages: [
                duplicate_ids(linkages:),
                duplicate_preferred_spellings(linkages:),
                invalid_phonetic_spellings(linkages:),
                missing_conjugations(linkages:),
                incorrect_conjugation_report(linkages:)
              ].compact.filter { |r| !r.empty? }
            )
          end

          def duplicate_ids(linkages:)
            conflicts = (linkages.map { |ln| ln.vocabulary.id }).find_duplicates
            message = "There are #{conflicts.length} conflicting IDs: #{conflicts}"
            level = conflicts.empty? ? :info : :error

            Goi::Core::ValidationReport.new(
              title: "Duplicate IDs",
              messages: [Goi::Core::ValidationMessage.new(level:, message:)]
            )
          end

          def duplicate_preferred_spellings(linkages:)
            duplicates = (linkages.map { |ln| ln.preferred_spelling.value }).find_duplicates
            message = "There are #{duplicates.length} duplicated preferred spellings: #{duplicates}"
            level = duplicates.empty? ? :info : :warn

            Goi::Core::ValidationReport.new(
              title: 'Duplicate Spellings',
              messages: [Goi::Core::ValidationMessage.new(level:, message:)]
            )
          end

          def invalid_phonetic_spellings(linkages:)
            messages = linkages.reject do |ln|
              Goi::Nihongo::StringClassification.classify_string(ln.phonetic_spelling.value) == :hiragana
            end.map do |ln|
              sp = ln.phonetic_spelling.value
              Goi::Core::ValidationMessage.error("Vocabulary #{ln.vocabulary.id} contains invalid characters in phonetic spelling: #{sp}")
            end

            Goi::Core::ValidationReport.new(
              title: 'Invalid Phonetic Spellings',
              messages: messages
            )
          end

          def missing_conjugations(linkages:)
            Goi::Core::ValidationReport.new(
              title: 'Missing Conjugations',
              messages: [
                empty_conjugation_message(linkages:, word_class_code: 'VERB', label: 'verbs'),
                empty_conjugation_message(linkages:, word_class_code: 'ADJECTIVE', label: 'adjectives')
              ]
            )
          end

          def incorrect_conjugation_report(linkages:)
            reports = linkages.map do |linkage|
              incorrect_conjugation_word_report(linkage:)
            end.compact

            Goi::Core::ValidationReport.new(
              title: 'Incorrect Conjugations',
              messages: reports
            )
          end

          def incorrect_conjugation_word_report(linkage:)
            conjugations = linkage.conjugation_set&.conjugations || []

            messages = conjugations.flat_map do |conjugation|

              actual = conjugation.value

              expected, pattern_error = nil
              begin
                expected = Goi::Nihongo::Conjugator.conjugate(
                  dictionary_spelling: linkage.preferred_spelling.value,
                  conjugation_kind_code: linkage.vocabulary.conjugation_kind_code,
                  inflection: conjugation.inflection
                )
              rescue Goi::Nihongo::Conjugator::ConjugationPatternError => cpe
                pattern_error = cpe
              end

              if !expected.nil? && actual != expected
                [Goi::Core::ValidationMessage.error("Expected #{expected} but got #{actual} for #{linkage.vocabulary.conjugation_kind_code} inflection #{conjugation.inflection}")]
              elsif !pattern_error.nil?
                [Goi::Core::ValidationMessage.warn("Expected #{linkage.preferred_spelling.value} to match a conjugation pattern for #{linkage.vocabulary.conjugation_kind_code} inflection #{conjugation.inflection}, producing #{actual}")]
              else
                []
              end
            end

            return nil if messages.empty?

            Goi::Core::ValidationReport.new(
              title: "#{linkage.preferred_spelling.value} Conjugations",
              messages:
            )
          end

          def empty_conjugation_message(linkages:, word_class_code:, label:)
            targets = linkages.filter { |ln| ln.vocabulary.word_class_code == word_class_code }
            empty_targets = targets.filter { |ln| ln.conjugation_set.nil? }
            empty_target_spellings =  empty_targets.map { |ln| ln.preferred_spelling.value }

            message = "#{empty_targets.length} of #{targets.length} #{label} have no conjugations: #{empty_target_spellings.inspect}"
            Goi::Core::ValidationMessage.info(message)
          end

        end

      end
    end
  end
end