require_relative '../core/validation_messages'

module Goi
  module Transformer
    class ValidationTransformer < BaseTransformer

      def transform(linkages:)
        # Validation rules
        messages = [
          duplicate_ids(linkages:),
          duplicate_preferred_spellings(linkages:),
          verbs_missing_conjugations(linkages:),
          adjectives_missing_conjugations(linkages:)
        ].flatten

        # Report results
        report_results(messages:) unless messages.empty?

        # Return linkages unchanged
        linkages
      end

      private

      def io = STDERR

      def report_results(messages:)
        io.puts ">> VALIDATION REPORT"
        messages.each { |msg| io.puts(msg) }
        io.puts "<< END REPORT"

        report = Goi::Core::ValidationReport.new(
          title: "Foo",
          messages: [
            Goi::Core::ValidationMessage.warn("Warning!"),
            Goi::Core::ValidationMessage.info("info"),
            Goi::Core::ValidationReport.new(title: "Bar", messages: [Goi::Core::ValidationMessage.info("info 2")])
          ]
        )
        STDERR.puts(report.formatted)

        raise RuntimeError, "STOP!"
      end

      def duplicate_ids(linkages:)
        conflicts = find_duplicates(linkages.map { |ln| ln.vocabulary.id })
        message = "There are #{conflicts.length} conflicting IDs: #{conflicts}"
        raise RuntimeError, message unless conflicts.empty?
        [message]
      end

      def duplicate_preferred_spellings(linkages:)
        duplicates = find_duplicates(linkages.map { |ln| ln.preferred_spelling.value })
        message = "There are #{duplicates.length} duplicated preferred spellings: #{duplicates}"
        [message]
      end

      def verbs_missing_conjugations(linkages:)
        empty_conjugations(linkages:, word_class_code: "VERB", label: "verbs")
      end

      def adjectives_missing_conjugations(linkages:)
        empty_conjugations(linkages:, word_class_code: "ADJECTIVE", label: "adjectives")
      end

      def empty_conjugations(linkages:, word_class_code:, label:)
        targets = linkages.filter { |ln| ln.vocabulary.word_class_code == word_class_code }
        empty_targets = targets.filter { |ln| ln.conjugation_set.nil? }
        empty_target_spellings =  empty_targets.map { |ln| ln.preferred_spelling.value }

        if empty_targets.empty?
          []
        else
          message = "#{empty_targets.length} of #{targets.length} #{label} have no conjugations: #{empty_target_spellings.inspect}"
          [message]
        end
      end

      def find_duplicates(array)
        counts = Hash.new
        array.each do |elem|
          counts[elem] ||= 0
          counts[elem] += 1
        end
        counts.select { |_, count| count > 1 }.keys
      end

    end
  end
end
