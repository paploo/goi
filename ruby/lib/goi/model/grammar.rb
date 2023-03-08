# frozen_string_literal: true

module Goi
  module Model
    module Grammar

      class HydratedRule
        def initialize(rule:, examples: [])
          @rule = rule
          @examples = examples
        end

        attr_reader :rule
        attr_reader :examples
      end

      class Rule

        def initialize(id:,
                       title:,
                       meaning:,
                       how_to_use:,
                       jlpt_level: nil,
                       row_num:,
                       date_added:,
                       lesson_codes: [],
                       tags: []
        )
          @id = id || raise(ArgumentError, 'ID required')
          @title = title
          @meaning = meaning
          @how_to_use = how_to_use
          @jlpt_level = jlpt_level
          @row_num = row_num
          @date_added = date_added
          @lesson_codes = lesson_codes
          @tags = tags
        end

        attr_reader :id
        attr_reader :title
        attr_reader :meaning
        attr_reader :how_to_use
        attr_reader :jlpt_level
        attr_reader :row_num
        attr_reader :date_added
        attr_reader :lesson_codes
        attr_reader :tags

      end


      class Example

        def initialize(id:,
                       rule_id:,
                       text:,
                       meaning:,
                       rank: nil,
                       lesson_codes: [],
                       tags: []
        )
          @id = id || raise(ArgumentError, 'ID required')
          @rule_id = rule_id || raise(ArgumentError, 'Rule ID required')
          @text = text
          @meaning = meaning
          @rank = rank
          @lesson_codes = lesson_codes
          @tags = tags
        end

        attr_reader :id
        attr_reader :rule_id
        attr_reader :text
        attr_reader :meaning
        attr_reader :rank
        attr_reader :lesson_codes
        attr_reader :tags

      end

    end
  end
end
