# frozen_string_literal: true

require_relative '../../../core/validation_messages'
require_relative '../../../core/extensions'

module Goi
  module Pipeline
    module Grammar
      module Transformer
        class ValidationTransformer < Grammar::Transformer::Base

          def transform(hydrated_rules)
            # Validation rules
            report = report(hydrated_rules:)

            # Report results
            io.puts(report.formatted)
            io.puts("(#{report.formatted_summary})")

            # Halt if we have an error!
            error_count = report.count(level: :error)
            raise RuntimeError, "Validation step failed with #{error_count} errors" if error_count.positive?

            # Return linkages unchanged
            hydrated_rules
          end

          private

          EMPTY_UUID = "00000000-0000-0000-0000-000000000000"
          MIN_CREATE_DATE = Date.new(2020, 1, 1)

          def io = $stderr

          def report(hydrated_rules:)
            Goi::Core::ValidationReport.new(
              title: 'Validation Report',
              messages: [
                duplicate_rule_ids(hydrated_rules:),
                empty_rule_ids(hydrated_rules:),
                duplicate_example_ids(hydrated_rules:),
                empty_example_ids(hydrated_rules:),
                rule_title_placeholder_case(hydrated_rules:),
                rule_meaning_placeholder_case(hydrated_rules:),
                invalid_date_added(hydrated_rules:),
                missing_examples(hydrated_rules:)
              ].compact.filter { |r| !r.empty? }
            )
          end

          def duplicate_rule_ids(hydrated_rules:)
            ids = hydrated_rules.map { |hr| hr.rule.id }
            conflicts = ids.find_duplicates

            message = "There are #{conflicts.length} conflicting IDs: #{conflicts}"
            level = conflicts.empty? ? :info : :error

            Goi::Core::ValidationReport.new(
              title: "Duplicate Rule IDs",
              messages: [Goi::Core::ValidationMessage.new(level:, message:)]
            )
          end

          def duplicate_example_ids(hydrated_rules:)
            ids = hydrated_rules.map do |hr|
              hr.examples.map { |ex| ex.id }
            end
            conflicts = ids.find_duplicates

            message = "There are #{conflicts.length} conflicting IDs: #{conflicts}"
            level = conflicts.empty? ? :info : :error

            Goi::Core::ValidationReport.new(
              title: "Duplicate Example IDs",
              messages: [Goi::Core::ValidationMessage.new(level:, message:)]
            )
          end

          def empty_rule_ids(hydrated_rules:)
            has_empty = hydrated_rules.any? { |hr| hr.rule.id.nil? || hr.rule.id == EMPTY_UUID }
            return nil unless has_empty

            Goi::Core::ValidationReport.new(
              title: "Empty Rule IDs",
              messages: [Goi::Core::ValidationMessage.error("One or more rules have IDs that are null or #{EMPTY_UUID}")]
            )
          end

          def empty_example_ids(hydrated_rules:)
            has_empty = hydrated_rules.any? do |hr|
              hr.examples.any? { |ex| ex.id.nil? || ex.id == EMPTY_UUID }
            end

            return nil unless has_empty

            Goi::Core::ValidationReport.new(
              title: "Empty Example IDs",
              messages: [Goi::Core::ValidationMessage.error("One or more rules have examples with IDs that are null or #{EMPTY_UUID}")]
            )
          end

          def rule_title_placeholder_case(hydrated_rules:)
            mistitled_hydrated_rules = hydrated_rules.select { |hr| hr.rule.title.preferred_spelling =~ /[A-Z]/}
            return nil if mistitled_hydrated_rules.empty?

            messages = mistitled_hydrated_rules.map do |hr|
              Goi::Core::ValidationMessage.warn("Rule #{hr.rule.id} title placeholder width not wide: #{hr.rule.title.preferred_spelling.inspect}")
            end

            Goi::Core::ValidationReport.new(
              title: 'Rule Titles: Expect Full-Width Placeholders',
              messages: messages
            )
          end

          def rule_meaning_placeholder_case(hydrated_rules:)
            mistitled_hydrated_rules = hydrated_rules.select { |hr| hr.rule.meaning =~ /[Ａ-Ｚ]/}
            return nil if mistitled_hydrated_rules.empty?

            messages = mistitled_hydrated_rules.map do |hr|
              Goi::Core::ValidationMessage.warn("Rule #{hr.rule.id} has full-width characters: #{hr.rule.meaning.inspect}")
            end

            Goi::Core::ValidationReport.new(
              title: 'Rule Meanings: Expect NO Full-Width characters',
              messages: messages
            )
          end

          def invalid_date_added(hydrated_rules:)
            invalid = hydrated_rules.select { |hr| hr.rule.date_added.nil? || hr.rule.date_added < MIN_CREATE_DATE }
            return nil if invalid.empty?

            messages = invalid.map do |hr|
              Goi::Core::ValidationMessage.error("Rule #{hr.rule.id} has an invalid date added: #{hr.rule.date_added&.to_s}")
            end

            Goi::Core::ValidationReport.new(
              title: 'Rule Date Added Invalid',
              messages: messages
            )
          end

          def missing_examples(hydrated_rules:)
            empty_hydrated_rules = hydrated_rules.select { |hr| hr.examples.empty? }
            return nil if empty_hydrated_rules.empty?

            messages = empty_hydrated_rules.map do |hr|
              Goi::Core::ValidationMessage.warn("Missing examples for Rule ID #{hr.rule.id}")
            end

            Goi::Core::ValidationReport.new(
              title: 'Missing Examples',
              messages: messages
            )
          end

        end
      end
    end
  end
end
