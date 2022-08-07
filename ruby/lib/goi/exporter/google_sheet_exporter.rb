module Goi
  module Exporter
    class GoogleSheetExporter < BaseExporter

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
      ].freeze

      def initialize(io: STDOUT)
        super()
        @io = io
      end

      attr_reader :io

      def export(linkages:)
        rows = [header_row] + linkage_rows(linkages:)
        rows.each do |row|
          io.puts(row.to_csv)
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