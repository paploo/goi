# frozen_string_literal: true

module Goi
  module Pipeline
    module Core
      # Mixin to add common tooling around Anki output handling
      #
      # Specifically:
      # 1. Headers writer, which uses overridable methods to get the properties, and
      # 2. Some utility methods for output
      module AnkiExportable

        def write_file_headers(io)
          io.puts("#separator: Comma") # Required to make columns map.
          io.puts("#deck: #{deck}") unless deck.nil?
          io.puts("#notetype: #{note_type}") unless note_type.nil?
          io.puts("#tags column: #{tags_column_index + 1}") unless tags_column_index.nil?
          io.puts("#columns: " + header_row.to_csv) unless header_row.nil?
        end

        def deck
          nil
        end

        def note_type
          nil
        end

        def tags_column_index
          nil
        end

        def header_row
          nil
        end

        def to_array_field(array) = array.join(' ').clean

        def humanize_const(s) = s.downcase.tr('_', ' ')

        def tagize(s) = s.downcase.tr('_', '-')

      end
    end
  end
end
