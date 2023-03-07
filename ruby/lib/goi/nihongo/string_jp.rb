# frozen_string_literal: true

require_relative '../nihongo/string_jp'

module Goi
  module Nihongo

    class StringJP

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

      def to_s = @tokens.map { |t| t[0] }.join

      def to_html
        @tokens.map do |t|
          if t[1].nil? || t[0] == t[1]
            t[0]
          else
            "<ruby>#{t[0]}<rt>#{t[1]}</rt></ruby>"
          end
        end.join
      end

    end

  end
end
