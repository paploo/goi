# frozen_string_literal: true

require_relative '../nihongo/string_jp'

module Goi
  module Nihongo

    # Encapsulates all spellings of a Japanese string in one class.
    #
    # The simplest form is to just use the preferred spelling; however phonetic and furigana spellings *may* be
    # attached, as needed.
    class StringJP

      # Parses the given input.
      # If this is a StringJP or FuriganaString, it applies best practices automatically.
      # Else it interprets the input as a String and returns the value.
      def self.from(input)
        case input
        when StringJP
          input
        when FuriganaString
          new(preferred_spelling: input.to_s, furigana: input)
        else
          new(preferred_spelling: input.to_s)
        end
      end

      def initialize(preferred_spelling:,
                     phonetic_spelling: nil,
                     furigana: nil)
        @preferred_spelling = preferred_spelling
        @phonetic_spelling = phonetic_spelling
        @furigana = furigana && Goi::Nihongo::FuriganaString.parse(furigana)
      end

      attr_reader :preferred_spelling
      attr_reader :phonetic_spelling
      attr_reader :furigana

      def to_s = preferred_spelling

    end

    class FuriganaString

      def self.parse(input)
        # If it's a FuriganaString, just pass through!
        return input if input.is_a?(self)

        # TODO: Refactor to use StringScanner, which will be more maintainable but needs an internal class to manage states.

        # First tokenize
        raw_tokens = input.to_s.strip.split(/[{}]/).reject(&:empty?).map do |a|
          a.split(/[|｜]/).map do |b| # ASCII and 日本語 pipe character
            b.split('・') # 日本語 dot character
          end
        end

        # Now arrange into the format needed for output
        tokens = raw_tokens.flat_map do |group|
          case group.length
          when 1
            group
          when 2
            chars = group[0][0].split('')
            raise ArgumentError "Group size mismatch for group: #{group.inspect}" if chars.length != group[1].length
            chars.zip(group[1])
          else
            raise ArgumentError "Unexpected group length encountered for group: #{group.inspect}"
          end
        end

        # Now create the instance
        new(tokens)
      end

      def initialize(tokens)
        @tokens = tokens
      end

      def kanji = @tokens.map { |t| t[0] }.join

      def kana = @tokens.map { |t| !t[1].nil? ? t[1] : t[0] }.join

      def to_s = kanji

      def to_template
        @tokens.map do |t|
          if t[1].nil? || t[0] == t[1]
            t[0]
          else
            "{#{t[0]}|#{t[1]}}"
          end
        end.join
      end

      def to_html
        @tokens.map do |t|
          if t[1].nil? || t[0] == t[1]
            t[0]
          else
            "<ruby>#{t[0]}<rt>#{t[1]}</rt></ruby>"
          end
        end.join
      end

      def to_anki
        @tokens.map do |t|
          if t[1].nil? || t[0] == t[1]
            t[0]
          else
            # Uses space at front to disambiguate which character(s) to put furigana over.
            " #{t[0]}[#{t[1]}]"
          end
        end.join.strip
      end

    end

  end
end
