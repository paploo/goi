module Goi
  module Transformer
    class ValidationTransformer < BaseTransformer

      def transform(linkages:)
        # Validation rules
        messages = [
          duplicate_ids(linkages:),
          duplicate_preferred_spellings(linkages:)
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
