require 'csv'

require_relative 'base_exporter'
require_relative '../model/vocabulary'

module Goi
  module Exporter

    class BaseAnkiExporter < BaseExporter

      def export(linkages:)
        config_out_open do |io|
          io.puts("#deck: #{deck}") unless deck.nil?
          io.puts("#notetype: #{note_type}") unless note_type.nil?
          io.puts("#columns: " + header_row.to_csv) unless header_row.nil?

          rows = linkage_rows(linkages:)
          rows.each do |row|
            io.puts(row.to_csv)
          end
        end
      end

      private

      def deck
        nil
      end

      def note_type
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

    class BaseAnkiVocabExporter < BaseAnkiExporter

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

    end

    class AnkiVocabExporter < BaseAnkiVocabExporter

      DECK = "日本語 Vocab".freeze

      NOTE_TYPE = "日本語 Vocab".freeze

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

      private

      # TODO: Switch to config
      def deck = DECK

      # TODO: Switch to config
      def note_type = NOTE_TYPE

      def header_row = HEADERS

      def note_id(linkage:) = linkage.vocabulary.id

      def linkage_row(linkage:) = [
        note_id(linkage:),
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

    class AnkiConjugationExporter < BaseAnkiVocabExporter

      DECK = "日本語 Conj".freeze

      NOTE_TYPE = "日本語 Conj".freeze

      CONJUGATION_HEADERS = Goi::Model::Vocabulary::Conjugation.map_dims do |charge_code, politeness_code, form_code|
        [charge_code, politeness_code, form_code].join('_').downcase
      end.freeze

      HEADERS = [
        'id',
        'vocabulary_id',
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
        'tags'
      ].freeze + CONJUGATION_HEADERS

      private

      # TODO: Switch to config
      def deck = DECK

      # TODO: Switch to config
      def note_type = NOTE_TYPE

      def header_row = HEADERS

      # We could use the conjugation set ID, but if we ever do advanced management in the DB, the ID could be transitory,
      # so we use the vocab id itself. Note that the same PK is fine by anki if the note type is different.
      def note_id(linkage:) = linkage.vocabulary.id

      def linkage_row(linkage:)
        conjugation_set = linkage.conjugation_set
        return nil if conjugation_set.nil?

        vocabulary_row(linkage:) + conjugation_row(conjugation_set:)
      end

      def vocabulary_row(linkage:) = [
        note_id(linkage:),
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

      def conjugation_row(conjugation_set:)
        conj_map = conjugation_set.conjugations.group_by do |conj|
          [conj.charge_code, conj.politeness_code, conj.form_code]
        end

        Goi::Model::Vocabulary::Conjugation.map_dims do |charge_code, politeness_code, form_code|
          key = [charge_code, politeness_code, form_code]
          conj_map[key]&.sort_by(&:sort_rank)&.first&.value
        end
      end

    end

  end
end