require 'nokogiri'
require 'uuidtools'
require 'time'

require_relative '../model/kanji'
require_relative '../core'
require_relative 'kanji_sh_dictionary'

module Goi
  module Kanji

    # Class to laod files from http://www.edrdg.org/wiki/index.php/KANJIDIC_Project
    class KanjiDICImporter

      INPUT_FILE_PATH = Goi::Core::Resources.at_path('kanjidic2.xml')

      def import
        kanjis = import_xml
        overlay_jlpt!(kanjis)
        convert(kanjis)
      end

      private

      def import_xml
        parser = Nokogiri::XML::SAX::Parser.new(KanjiDICImporterSAXParser.new)

        File.open(INPUT_FILE_PATH) do |file|
          parser.parse(file)
        end

        parser.document.kanjis
      end

      def overlay_jlpt!(kanjis)
        kanji_dict = KanjiSHDictionary.new.load_data!
        kanjis.map do |kanji|
          jlpt_level = kanji_dict.jlpt_kanji_index[kanji[:kanji]]
          kanji[:jlpt_level] = jlpt_level unless jlpt_level.nil?
        end
      end

      def convert(kanjis)
        #TODO: Convert to kanji model
        kanjis
      end


    end

    # See http://www.edrdg.org/kanjidic/kanjidic2_dtdh.html
    class KanjiDICImporterSAXParser < Nokogiri::XML::SAX::Document

      def initialize
        super
        @paths = Set.new
        @stack = []
        @kanjis = []
      end

      attr_reader :kanjis

      def start_document
        @start_at = Time.now
      end

      def end_document
        delta_t = Time.now - @start_at
        STDERR.puts "PARSED FILE IN #{delta_t} seconds"
      end

      def start_element(name, attributes = [])
        @stack.push(name)
        @paths << @stack.dup

        # Stores the current stack frame attributes where we can get at them.
        @attributes = attributes.to_h

        # Warning! Don't cache rules if using local variables!
        # Perf tanks if you don't cache though.
        @start_element_frame_rules ||= [
          FrameRule.new(['kanjidic2', 'character']) do
            @kanji = { rmgroup: [] }
          end,
          FrameRule.new(['kanjidic2', 'character', 'reading_meaning', 'rmgroup']) do
            @target = {}
            @kanji[:rmgroup] ||= []
            @kanji[:rmgroup] << @target
          end
        ]

        FrameRule.evaluate(@stack, @start_element_frame_rules)
      end

      def end_element(name)
        if @stack == ['kanjidic2', 'character']
          @kanjis << @kanji
          @kanji = nil
        end

        elem = @stack.pop
        raise "POP FAILED: Expected #{name} but got #{elem}" if elem != name
      end

      def characters(string)
        @characters_value = string

        # Don't cache rules if using local variables!
        # So instead we use an instance variable; otherwise our perf goes through the floor.
        @characters_frame_rules ||= [
          FrameRule.new(['kanjidic2', 'character', 'literal']) do
            @kanji[:kanji] = @characters_value
          end,
          FrameRule.new(['kanjidic2', 'character', 'codepoint', 'cp_value']) do
            key = "#{@attributes['cp_type']}_codepoint".to_sym
            @kanji[key] = @characters_value
          end,
          FrameRule.new(['kanjidic2', 'character', 'misc', 'grade']) do
            @kanji[:grade] = @characters_value.to_i
          end,
          FrameRule.new(['kanjidic2', 'character', 'misc', 'stroke_count']) do
            @kanji[:stroke_count] = @characters_value.to_i
          end,
          FrameRule.new(['kanjidic2', 'character', 'misc', 'freq']) do
            @kanji[:frequency] = @characters_value.to_i
          end,
          FrameRule.new(['kanjidic2', 'character', 'misc', 'jlpt']) do
            # The pre-2010 level of the Japanese Language Proficiency Test (JLPT) in which the kanji occurs (1-4). Note
            # that the JLPT test levels changed in 2010, with a new 5-level system (N1 to N5) being introduced. No
            # official kanji lists are available for the new levels. The new levels are regarded as being similar to the
            # old levels except that the old level 2 is now divided between N2 and N3, and the old levels 3 and 4 are
            # now N4 and N5.
            @kanji[:old_jlpt_level] = @characters_value.to_i
          end,
          FrameRule.new(['kanjidic2', 'character', 'misc', 'variant']) do
            key = "variant_#{@attributes['var_type']}".to_sym
            @kanji[key] ||= []
            @kanji[key] << @characters_value
          end,
          FrameRule.new(['kanjidic2', 'character', 'reading_meaning', 'rmgroup', 'meaning']) do
            @target[:meaning] ||= []
            @target[:meaning] << @characters_value if @attributes["m_lang"].nil? # Select English defs
          end,
          FrameRule.new(['kanjidic2', 'character', 'reading_meaning', 'rmgroup', 'reading']) do
            reading_type = @attributes['r_type']
            if ['ja_on', 'ja_kun'].include?(reading_type)
              key = "#{reading_type}_readings}".to_sym
              @target[key] ||= []
              @target[key] << @characters_value
            end
          end,
          FrameRule.new(['kanjidic2', 'character', 'reading_meaning', 'nanori']) do
            @kanji[:nanori_readings] ||= []
            @kanji[:nanori_readings] << @characters_value
          end
        ]

        FrameRule.evaluate(@stack, @characters_frame_rules)
      end

      class FrameRule

        def self.evaluate(stack_path, frame_rules)
          match = frame_rules.find { |rule| rule.match?(stack_path) }
          match&.call
          match
        end

        def initialize(stack_path, &block)
          @stack_path = stack_path
          @block = block
        end

        def match?(stack_path) = @stack_path == stack_path

        def call = @block.call

      end

    end

  end
end
