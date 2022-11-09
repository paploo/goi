require 'csv'

require_relative 'base_exporter'
require_relative '../model/vocabulary'

module Goi
  module Exporter

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
        io.puts("#columns: " + header_row.to_csv) unless header_row.nil?
      end

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

      NOTE_ID_HEADERS = ['id'].freeze

      TAGS_HEADERS = ['tags'].freeze

      VOCABULARY_HEADERS = [
        'definition',
        'preferred_spelling',
        'phonetic_spelling',
        'alt_spelling',
        'word_class',
        'conjugation_kind',
        'jlpt_level',
        'date_added',
        'row_num',
        'lessons'
      ].freeze

      CONJUGATION_HEADERS = Goi::Model::Vocabulary::Conjugation::Inflection.all.map do |infl|
        infl.code.downcase
      end

      private

      def note_id_fields(linkage:) = [note_id(linkage:)]

      def tags_fields(linkage:) = [tags_field(linkage.vocabulary)]

      def vocabulary_fields(linkage:) = [
        linkage.preferred_definition.value,
        linkage.preferred_spelling.value,
        phonetic_spelling_field(linkage),
        alt_spelling_field(linkage),
        linkage.vocabulary.word_class_code.then { |c| humanize_const(c) },
        linkage.vocabulary.conjugation_kind_code&.then { |c| humanize_const(c) },
        linkage.vocabulary.jlpt_level&.to_s,
        linkage.vocabulary.date_added&.iso8601,
        linkage.vocabulary.row_num.to_s,
        to_array_field(linkage.vocabulary.lesson_codes)
      ]

      def conjugation_fields(conjugation_set:)
        conj_map = conjugation_set.conjugations.group_by do |conj|
          [conj.charge_code, conj.politeness_code, conj.form_code]
        end

        Goi::Model::Vocabulary::Conjugation::Inflection.all.map do |infl|
          key = infl.to_a
          conj_map[key]&.sort_by(&:sort_rank)&.first&.value
        end
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
        #STDERR.puts("ALT SPELLING FOR #{pref_sp}: #{kanji_sp}") if (kanji_sp != pref_sp) && !kanji_sp.nil?
        kanji_sp != pref_sp ? kanji_sp : nil
      end

      def tags_field(vocabulary)
        full_tags = (vocabulary.tags + [vocabulary.word_class_code, vocabulary.conjugation_kind_code] + vocabulary.lesson_codes).compact.uniq
        tagized = full_tags.map { |s| tagize(s) }
        to_array_field(tagized)
      end

    end

    # Combined exporter that puts out all the data in one row, including all conjugations, for a single deck.
    class AnkiExporter < BaseAnkiVocabExporter

      private

      def header_row = NOTE_ID_HEADERS + VOCABULARY_HEADERS + CONJUGATION_HEADERS + TAGS_HEADERS

      def note_id(linkage:) = linkage.vocabulary.id

      def linkage_row(linkage:)
        note_id_fields(linkage:) + vocabulary_fields(linkage:) + conjugation_fields_or_empty(linkage:) + tags_fields(linkage:)
      end

      def conjugation_fields_or_empty(linkage:)
        conjugation_set = linkage.conjugation_set
        if conjugation_set.nil?
          Array.new(CONJUGATION_HEADERS.length)
        else
          conjugation_fields(conjugation_set:)
        end
      end

    end

    # Outputs only vocabulary, not the conjugations, so that we can have conjugations managed in a separate deck
    class AnkiVocabExporter < BaseAnkiVocabExporter

      DECK = "日本語 Vocab".freeze

      NOTE_TYPE = "日本語 Vocab".freeze

      private

      # TODO: Switch to config
      def deck = DECK

      # TODO: Switch to config
      def note_type = NOTE_TYPE

      def header_row = NOTE_ID_HEADERS + VOCABULARY_HEADERS + TAGS_HEADERS

      def note_id(linkage:) = linkage.vocabulary.id

      def linkage_row(linkage:)
        note_id_fields(linkage:) + vocabulary_fields(linkage:) + tags_fields(linkage:)
      end

    end

    # Outputs only vocab with conjugations, along with their conjugations so that we can have a separate deck.
    class AnkiConjugationExporter < BaseAnkiVocabExporter

      # TODO: Switch to config
      def deck = "日本語 Conj".freeze

      # TODO: Switch to config
      def note_type = "日本語 Conj".freeze

      def header_row = NOTE_ID_HEADERS + ['vocabulary_id'] + VOCABULARY_HEADERS + CONJUGATION_HEADERS + TAGS_HEADERS

      # We could use the conjugation set ID, but if we ever do advanced management in the DB, the ID could be transitory,
      # so we use the vocab id itself. Note that the same PK is fine by anki if the note type is different.
      def note_id(linkage:) = linkage.vocabulary.id

      def linkage_row(linkage:)
        conjugation_set = linkage.conjugation_set
        return nil if conjugation_set.nil?

        note_id_fields(linkage:) + [linkage.vocabulary.id] + vocabulary_fields(linkage:) + conjugation_fields(conjugation_set:) + tags_fields(linkage:)
      end

    end

  end
end
