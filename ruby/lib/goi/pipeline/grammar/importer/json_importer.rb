# frozen_string_literal: true

require 'json'

require_relative '../../../model/grammar'

module Goi
  module Pipeline
    module Grammar
      module Importer
        class JSONImporter < Grammar::Importer::Base

          def initialize(infile_pathname:)
            super()
            @infile_pathname = infile_pathname
          end

          attr_reader :infile_pathname

          def import
            json = JSON.load_file(infile_pathname.to_s)
            stripped_json = json.reject { |o| o.is_a?(Hash) && o.has_key?("COMMENT") }
            parse_hydrated_rules(stripped_json)
          end

          private

          def parse_hydrated_rules(json) = json.each_with_index.map { |doc, index| parse_hydrated_rule(index, doc) }

          def parse_hydrated_rule(index, json)
            rule = parse_rule(index, json)
            examples = parse_examples(rule.id, json.fetch('examples', []))

            Model::Grammar::HydratedRule.new(rule:, examples:)
          end

          def parse_rule(index, json)
            Model::Grammar::Rule.new(id: json.fetch('id'),
                                     title: parse_stringjp(json.fetch('title')),
                                     meaning: json.fetch('meaning'),
                                     how_to_use: json.fetch('how_to_use'),
                                     jlpt_level: json['jlpt_level']&.to_i,
                                     row_num: index + 1,
                                     date_added: json.fetch('date_added')&.then { |d| Date.parse(d) },
                                     lesson_codes: json['lesson_codes'] || [],
                                     tags: json['tags'] || [])
          end

          def parse_examples(rule_id, json) = json.each_with_index.map { |doc, index| parse_example(rule_id, index, doc) }

          def parse_example(rule_id, index, json)
            Model::Grammar::Example.new(id: json.fetch('id'),
                                        rule_id: rule_id,
                                        meaning: json.fetch('meaning'),
                                        text: parse_stringjp(json.fetch('text')),
                                        sort_rank: index + 1,
                                        lesson_codes: json['lesson_codes'] || [],
                                        tags: json['tags'] || [])
          end

          # The preferred spelling is always derivable from the furigana template,
          # But the template should be omitted if it matches the input.
          def parse_stringjp(json)
            case json
            when String
              furigana_template = Goi::Nihongo::FuriganaString.parse(json)
              preferred_spelling = furigana_template.kanji
              furigana = furigana_template.kanji != furigana_template.to_template ? furigana_template : nil
              phonetic_spelling = nil
              Nihongo::StringJP.new(preferred_spelling:, phonetic_spelling:, furigana:)
            else
              furigana_template = Goi::Nihongo::FuriganaString.parse(json.fetch('spelling'))
              preferred_spelling = furigana_template.kanji
              furigana = furigana_template.kanji != furigana_template.to_template ? furigana_template : nil
              phonetic_spelling = json['phonetic_spelling']
              Nihongo::StringJP.new(preferred_spelling:, phonetic_spelling:, furigana:)
            end
          end

        end
      end
    end
  end
end
