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

      attr_reader :id, :kanji, :jis_code_point, :unicode_code_point, :meanings, :on_readings, :kun_readings, :nanori_readings, :jlpt_level, :grade, :stroke_count, :frequency_ranking, :related_

    end
  end
end
