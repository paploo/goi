require 'uuidtools'

module Goi
  module Model
    class Kanji

      UUID5_NAMESPACE = UUIDTools::UUID.parse('6228ddd3-7a0f-47e0-9bea-a15a0f491ca4').to_s

      def self.create_id(character:)
        name = [character].map(&:to_s).join('|')
        ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
        UUIDTools::UUID.sha1_create(ns, name).to_s
      end

      def initialize(id:,
                     character:,
                     unicode_code_point:,
                     meanings:,
                     on_readings:,
                     kun_readings:,
                     nanori_readings:,
                     stroke_count:,
                     jlpt_level:,
                     grade:,
                     frequency_ranking:)
        @id = id || raise(ArgumentError, 'ID required')
        @character = character
        @unicode_code_point = unicode_code_point
        @meanings = meanings
        @on_readings = on_readings
        @kun_readings = kun_readings
        @nanori_readings = nanori_readings
        @stroke_count = stroke_count
        @jlpt_level = jlpt_level
        @grade = grade
        @frequency_ranking = frequency_ranking
      end

      attr_reader :id
      attr_reader :character
      attr_reader :unicode_code_point
      attr_reader :meanings
      attr_reader :on_readings
      attr_reader :kun_readings
      attr_reader :nanori_readings
      attr_reader :stroke_count
      attr_reader :jlpt_level
      attr_reader :grade
      attr_reader :frequency_ranking

    end
  end
end
