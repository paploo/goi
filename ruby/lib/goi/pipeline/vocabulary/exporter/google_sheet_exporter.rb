# frozen_string_literal: true

require_relative '../../../model/google_sheet'

module Goi
  module Pipeline
    module Vocabulary
      module Exporter
        class GoogleSheetExporter < Vocabulary::Exporter::Base

          CONJUGATION_COLUMN_DATA = Goi::Model::GoogleSheet::CONJUGATION_KEY_DATA
          CONJUGATION_COLUMN_KEYS = Goi::Model::GoogleSheet::CONJUGATION_KEYS

          HEADERS = [
            'definition',
            'preferred_spelling',
            'phonetic_spelling',
            'alt_phon_spell',
            'kanji_spelling',
            'word_class_code',
            'conjugation_kind_code',
            'jlpt_level',
            'lesson_codes',
            'tags',
            'id',
            'row_num',
            'date_added'
          ].freeze + [nil] + CONJUGATION_COLUMN_KEYS

          def initialize(outfile_pathname:)
            super()
            @outfile_pathname = outfile_pathname
          end

          attr_reader :outfile_pathname

          def export(linkages)
            open_path(outfile_pathname) do |io|
              rows = [header_row] + linkage_rows(linkages:)
              rows.each do |row|
                io.puts(row.to_csv)
              end
            end
          end

          private

          def header_row
            HEADERS
          end

          def linkage_rows(linkages:)
            linkages.map { |linkage| linkage_row(linkage:) }
          end

          def linkage_row(linkage:)
            core_columns(linkage:) + empty_column + conjugation_columns(linkage:)
          end

          def core_columns(linkage:)
            [
              linkage.preferred_definition.value,
              linkage.preferred_spelling.value,
              linkage.phonetic_spelling.value,
              linkage.alt_phonetic_spelling&.value,
              linkage.kanji_spelling&.value,
              linkage.vocabulary.word_class_code,
              linkage.vocabulary.conjugation_kind_code,
              linkage.vocabulary.jlpt_level&.to_s,
              lessons_field(linkage.vocabulary),
              tags_field(linkage.vocabulary),
              linkage.vocabulary.id,
              linkage.vocabulary.row_num.to_s,
              linkage.vocabulary.date_added&.iso8601
            ]
          end

          def conjugation_columns(linkage:)
            conjugation_set = linkage.conjugation_set
            CONJUGATION_COLUMN_DATA.map do |col_data|
              find_conjugation(
                conjugation_set:,
                politeness_code: col_data[:politeness],
                charge_code: col_data[:charge],
                form_code: col_data[:form]
              )&.value
            end
          end

          # Returns a single empty column, used to buffer sections in the sheet.
          def empty_column
            [nil]
          end

          def find_conjugation(conjugation_set:, politeness_code:, charge_code:, form_code:)
            conjugation_set&.conjugations&.find_all do |c|
              c.politeness_code == politeness_code && c.charge_code == charge_code && c.form_code == form_code
            end&.min_by(&:sort_rank)
          end

          def tags_field(vocabulary)
            to_array_field(vocabulary.tags.compact.uniq)
          end

          def lessons_field(vocabulary)
            to_array_field(vocabulary.lesson_codes.compact.uniq)
          end

          def to_array_field(array)
            return nil if array.empty?
            '{' + array.join(',') + '}'
          end

        end
      end
    end
  end
end
