# frozen_string_literal: true

require_relative '../../../model/vocabulary'
require_relative '../../../pipeline/core/anki_exportable'

module Goi
  module Pipeline
    module Grammar
      module Exporter
        class AnkiExporter < Grammar::Exporter::Base
          include Pipeline::Core::AnkiExportable

          def initialize(outfile_pathname:, example_count: 3)
            super()
            @outfile_pathname = outfile_pathname
            @example_count = example_count
          end

          attr_reader :outfile_pathname
          attr_reader :example_count

          def export(hydrated_rules)
            open_path(outfile_pathname) do |io|
              write_file_headers(io)

              rows = rule_rows(hydrated_rules:)
              rows.each do |row|
                io.puts(row.to_csv)
              end

            end
          end

          private

          RULE_HEADERS = [
            'id',
            'title',
            'title_phonetic',
            'meaning',
            'how_to_use',
            'jlpt_level',
            'date_added',
            'row_num',
          ].freeze

          EXAMPLE_HEADERS = [
            'text',
            'text_phonetic',
            'meaning'
          ].freeze

          TAGS_HEADER = 'tags'

          def rule_rows(hydrated_rules:)
            hydrated_rules.map { |hydrated_rule| hydrated_rule_row(hydrated_rule:) }.compact
          end

          def header_row
            example_headers = (1..example_count).flat_map do |example_number|
              EXAMPLE_HEADERS.map { |h| "example_#{example_number}_#{h}" }
            end

            RULE_HEADERS + example_headers + [TAGS_HEADER]
          end

          def tags_column_index
            header_row&.index(TAGS_HEADER)
          end

          def deck = '日本語 Grammar'.freeze

          def note_type = '日本語 Grammar'.freeze

          def hydrated_rule_row(hydrated_rule:)
            rule = rule_row(hydrated_rule.rule)

            examples = (0...example_count).map do |i|
              example_row_or_nils(hydrated_rule.examples[i])
            end

            tags = tags_field(tags(hydrated_rule))

            rule + examples.flatten + [tags]
          end

          def rule_row(rule)
            [
              rule.id,
              rule.title.furigana&.to_anki || rule.title.preferred_spelling,
              rule.title.phonetic_spelling,
              rule.meaning,
              how_to_use_field(rule.how_to_use),
              rule.jlpt_level&.to_s,
              rule.date_added.iso8601,
              rule.row_num.to_s
            ]
          end

          def example_row(example)
            [
              example.text.furigana&.to_anki || example.text.preferred_spelling,
              example.text.phonetic_spelling,
              example.meaning
            ]
          end

          def example_row_or_nils(example)
            if example.nil?
              Array.new(EXAMPLE_HEADERS.length)
            else
              example_row(example)
            end
          end

          def tags(hydrated_rule)
            tagables = [hydrated_rule.rule] + hydrated_rule.examples

            tagables.flat_map do |tagable|
              tagable.tags + tagable.lesson_codes
            end.uniq.sort
          end

          def tags_field(tags)
            tagized = tags.map {|s| tagize(s)}
            to_array_field(tagized)
          end

          def how_to_use_field(how_to_use_array)
            how_to_use_array.map { |s| "<li>#{s}</li>" }.join
          end

        end
      end
    end
  end
end
