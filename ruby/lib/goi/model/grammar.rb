# frozen_string_literal: true

module Goi
  module Model
    module Grammar

      class Rule

        def initialize(id:,
                       title:,
                       meaning:,
                       how_to_use:,
                       jlpt_level: nil,
                       lesson_codes: [],
                       tags: []
        )
          @id = id || raise(ArgumentError, 'ID required')
          @title = title
          @meaning = meaning
          @how_to_use = how_to_use
          @jltp_level = jltp_level
          @lesson_codes = lesson_codes
          @tags = tags
        end

        attr_reader :title
        attr_reader :meaning
        attr_reader :how_to_use
        attr_reader :jltp_level
        attr_reader :lesson_codes
        attr_reader :tags

      end


      class Example

        def initialize(id:,
                       rule_id:,
                       text:,
                       meaning:,
                       rank: nil,
                       lessson_codes: [],
                       tags: []
        )
          @id = id || raise(ArgumentError, 'ID required')
          @rule_id = rule_id || raise(ArgumentError, 'Rule ID required')
          @text = text
          @meaning = meaning
          @rank = rank
          @lessson_codes = lessson_codes
          @tags = tags
        end

        attr_reader :id
        attr_reader :rule_id
        attr_reader :text
        attr_reader :meaning
        attr_reader :rank
        attr_reader :lessson_codes
        attr_reader :tags

      end

    end
  end
end
