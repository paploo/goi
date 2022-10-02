require 'uuidtools'

module Goi
  module Model
    class Kanji

      UUID5_NAMESPACE = UUIDTools::UUID.parse('6228ddd3-7a0f-47e0-9bea-a15a0f491ca4').to_s

      def self.create_id(kanji:)
        name = [kanji].map(&:to_s).join('|')
        ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
        UUIDTools::UUID.sha1_create(ns, name).to_s
      end

      def initialize(id:,
                     kanji:,
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
        @kanji = kanji
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
      attr_reader :kanji
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
