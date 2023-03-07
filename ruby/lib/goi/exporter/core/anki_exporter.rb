require 'csv'

require_relative 'base_exporter'

module Goi
  module Exporter
    module Core

      class BaseAnkiExporter < BaseExporter

        def export(linkages:)
          config_out_open do |io|
            # NOTE: To use the headers, you have to turn on the "New Import/export handling" in Anki settings.
            write_file_headers(io)

            rows = linkage_rows(linkages:)
            rows.each do |row|
              io.puts(row.to_csv)
            end
          end
        end

        private

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

        def linkage_rows(linkages:)
          linkages.map { |linkage| linkage_row(linkage:) }.compact
        end

        def linkage_row(linkage:)
          nil
        end

        def to_array_field(array) = array.join(' ').clean

        def humanize_const(s) = s.downcase.tr('_', ' ')

        def tagize(s) = s.downcase.tr('_', '-')

      end

    end
  end
end
