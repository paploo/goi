require 'csv'

require_relative 'base_exporter'

module Goi
  module Exporter
    class AnkiExporter < BaseExporter

      HEADERS = [
        'id',
        'definition',
        'preferred_spelling',
        'phonetic_spelling',
        'alt_spelling',
        'word_class',
        'conjugation_kind',
        'jlpt_level',
        'date_added',
        'row_num',
        'lessons',
        'tags',
      ].freeze

      def export(linkages:)
        config_out_open do |io|
          io.puts("#columns: " + HEADERS.to_csv)
          rows = linkage_rows(linkages:)
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
        [
          linkage.vocabulary.id,
          linkage.preferred_definition.value,
          linkage.preferred_spelling.value,
          phonetic_spelling_field(linkage),
          alt_spelling_field(linkage),
          linkage.vocabulary.word_class_code.then { |c| humanize_const(c) },
          linkage.vocabulary.conjugation_kind_code&.then { |c| humanize_const(c) },
          linkage.vocabulary.jlpt_level&.to_s,
          linkage.vocabulary.date_added&.iso8601,
          linkage.vocabulary.row_num.to_s,
          to_array_field(linkage.vocabulary.lesson_codes),
          tags_field(linkage.vocabulary)
        ]
      end

      def phonetic_spelling_field(linkage)
        [
          linkage.phonetic_spelling.value,
          linkage.alt_phonetic_spelling&.value
        ].compact.uniq.join('／')
      end

      def alt_spelling_field(linkage)
        kanji_sp = linkage.kanji_spelling&.value
        pref_sp = linkage.preferred_spelling.value
        # Make Anki card only if it differs from the preferred spelling.
        kanji_sp != pref_sp ? kanji_sp : nil
      end

      def tags_field(vocabulary)
        full_tags = (vocabulary.tags + [vocabulary.word_class_code, vocabulary.conjugation_kind_code] + vocabulary.lesson_codes).compact.uniq
        tagized = full_tags.map { |s| tagize(s) }
        to_array_field(tagized)
      end

      def to_array_field(array) = array.join(' ').clean

      def humanize_const(s) = s.downcase.tr('_', ' ')

      def tagize(s) = s.downcase.tr('_', '-')

    end
  end
end