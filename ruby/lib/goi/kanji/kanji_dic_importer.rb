require 'nokogiri'
require 'uuidtools'
require 'time'

require_relative '../model/kanji'

module Goi
  module Kanji

    # Class to laod files from http://www.edrdg.org/wiki/index.php/KANJIDIC_Project
    class KanjiDICImporter

      # TODO: THis should be config!
      INPUT_FILE_PATH = Pathname(__FILE__).expand_path.join('..', '..', '..', '..', '..', 'files', 'kanjidic2.xml')

      def import
        parser = Nokogiri::XML::SAX::Parser.new(KanjiDICImporterSAXParser.new)

        File.open(INPUT_FILE_PATH) do |file|
          parser.parse(file)
        end
      end


    end

    # See http://www.edrdg.org/kanjidic/kanjidic2_dtdh.html
    class KanjiDICImporterSAXParser < Nokogiri::XML::SAX::Document

      PATH_HANDLERS = {
        ['kanjidlc2', 'character'] => :kanjidlc2_character_handler
      }

      # TODO:
      #  - How best to handle filtering a value based on attr, not just tag?
      #    - Maybe store in stack frame (which would be a formalized structure)?
      #  - Instead of a bunch of inline if statements, use dispatching and/or rules lists?
      def initialize
        super
        @paths = Set.new
        @stack = []
        @kanjis = []
      end

      def start_document
        @start_at = Time.now
      end

      def end_document
        delta_t = Time.now - @start_at
        STDERR.puts "PARSED FILE IN #{delta_t} seconds"
        puts @paths.inspect
      end

      def start_element(name, attributes = [])
        @stack.push(name)
        @paths << @stack.dup
        #puts @stack.inspect

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
          puts @kanji.inspect if @kanji[:kanji] == '亜'
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
            @kanji[:old_jlpt] = @characters_value.to_i
          end,
          FrameRule.new(['kanjidic2', 'character', 'misc', 'variant']) do
            key = "variant_#{@attributes['var_type']}".to_sym
            @kanji[key] ||= []
            @kanji[key] << @characters_value
          end,
          FrameRule.new(['kanjidic2', 'character', 'reading_meaning', 'rmgroup', 'reading']) do
            key = "reading_#{@attributes['r_type']}".to_sym
            @target[key] ||= []
            @target[key] << @characters_value
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

        def initialize(path, &block)
          @path = path
          @block = block
        end

        def match?(stack_path) = @path == stack_path

        def call = @block.call

      end

    end

  end
end


# TODO: FOR BOOTSTRAPPING; REMOVE THIS!!!
Goi::Kanji::KanjiDICImporter.new.import
