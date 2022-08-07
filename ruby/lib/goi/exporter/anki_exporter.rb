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
        'word_class',
        'conjugation_kind',
        'jlpt_level',
        'date_added',
        'lessons',
        'tags',
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
          linkage.vocabulary.id,
          linkage.preferred_definition.value,
          linkage.preferred_spelling.value,
          phonetic_spelling_field(linkage),
          linkage.vocabulary.word_class_code,
          linkage.vocabulary.conjugation_kind_code,
          linkage.vocabulary.jlpt_level&.to_s,
          linkage.vocabulary.date_added.iso8601,
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

      def tags_field(vocabulary)
        full_tags = (vocabulary.tags + [vocabulary.word_class_code, vocabulary.conjugation_kind_code] + vocabulary.lesson_codes).compact.uniq
        to_array_field(full_tags)
      end

      def to_array_field(array) = array.join(' ').clean

    end
  end
end