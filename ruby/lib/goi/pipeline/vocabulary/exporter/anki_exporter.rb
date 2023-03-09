# frozen_string_literal: true

require_relative '../../../model/vocabulary'
require_relative '../../../pipeline/core/anki_exportable'

module Goi
  module Pipeline
    module Vocabulary
      module Exporter

        class BaseAnkiExporter < Vocabulary::Exporter::Base
          include Pipeline::Core::AnkiExportable

          def initialize(outfile_pathname:)
            super()
            @outfile_pathname = outfile_pathname
          end

          attr_reader :outfile_pathname

          def export(linkages)
            open_path(outfile_pathname) do |io|
              write_file_headers(io)

              rows = linkage_rows(linkages:)
              rows.each do |row|
                io.puts(row.to_csv)
              end
            end
          end

          private

          def linkage_rows(linkages:)
            linkages.map { |linkage| linkage_row(linkage:) }.compact
          end

          def linkage_row(linkage:)
            nil
          end

        end

        class BaseAnkiVocabExporter < BaseAnkiExporter

          NOTE_ID_HEADER = 'id'.freeze

          TAGS_HEADER = 'tags'.freeze

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
        # This is the default one to use in a pipeline, creating the one big deck.
        class AnkiExporter < BaseAnkiVocabExporter

          private

          def header_row = [NOTE_ID_HEADER] + VOCABULARY_HEADERS + CONJUGATION_HEADERS + [TAGS_HEADER]

          def tags_column_index = header_row&.index(TAGS_HEADER)

          def note_id(linkage:) = linkage.vocabulary.id

          def deck = "日本語 Vocab".freeze

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

          private

          # TODO: Switch to config
          def deck = "日本語 Vocab".freeze

          # TODO: Switch to config
          def note_type = "日本語 Vocab".freeze

          def header_row = [NOTE_ID_HEADER] + VOCABULARY_HEADERS + [TAGS_HEADER]

          def tags_column_index = header_row?.index(TAGS_HEADER)

          def note_id(linkage:) = linkage.vocabulary.id

          def linkage_row(linkage:)
            note_id_fields(linkage:) + vocabulary_fields(linkage:) + tags_fields(linkage:)
          end

        end

        # Outputs only vocab with conjugations, along with their conjugations so that we can have a separate deck.
        class AnkiConjugationExporter < BaseAnkiVocabExporter

          private

          # TODO: Switch to config
          def deck = "日本語 Conj".freeze

          # TODO: Switch to config
          def note_type = "日本語 Conj".freeze

          def header_row = [NOTE_ID_HEADER] + ['vocabulary_id'] + VOCABULARY_HEADERS + CONJUGATION_HEADERS + [TAGS_HEADER]

          def tags_column_index = header_row?.index(TAGS_HEADER)

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
  end
end
