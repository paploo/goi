# frozen_string_literal: true

module Goi
  module Pipeline
    module Grammar

      class RecordBuilder

        def build_record_group(hydrated_rule)
          {
            rule: build_rule_record(hydrated_rule.rule),
            examples: hydrated_rule.examples.map { |example| build_example_record(example) },

            rule_references: build_rule_lesson_codes(hydrated_rule.rule),
            example_references: build_examples_lesson_codes(hydrated_rule.examples),
          }
        end

        private

        def build_rule_record(rule)
          {
            id: rule.id,
            meaning: rule.meaning,
            title_preferred_spelling: rule.title.preferred_spelling,
            title_phonetic_spelling: rule.title.phonetic_spelling,
            title_furigana_template: rule.title.furigana&.to_template,
            how_to_use: build_how_to_use(rule.how_to_use),
            jlpt_level: rule.jlpt_level,
            row_num: rule.row_num,
            date_added: rule.date_added,
            tags: build_tags(rule.tags)
          }
        end

        def build_example_record(example)
          {
            id: example.id,
            rule_id: example.rule_id,
            meaning: example.meaning,
            text_preferred_spelling: example.text.preferred_spelling,
            text_phonetic_spelling: example.text.phonetic_spelling,
            text_furigana_template: example.text.furigana&.to_template,
            sort_rank: example.sort_rank,
            tags: build_tags(example.tags)
          }
        end

        def build_rule_lesson_codes(rule)
          build_reference_records(:rule_id, rule.id, rule.lesson_codes)
        end

        def build_examples_lesson_codes(examples)
          examples.flat_map do |example|
            build_reference_records(:example_id, example.id, example.lesson_codes)
          end
        end

        def build_reference_records(kind, id, lesson_codes)
          lesson_codes.map { |lesson_code| build_reference_record(kind, id, lesson_code) }
        end

        def build_reference_record(kind, id, lesson_code)
          {
            kind => id,
            lesson_code:
          }
        end

        def build_string_array(strings)
          strings.join(',').then { |s| "{#{s}}" }
        end

        alias_method :build_tags, :build_string_array
        alias_method :build_how_to_use, :build_string_array

      end

    end
  end
end
