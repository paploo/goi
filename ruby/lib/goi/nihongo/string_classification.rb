module Goi
  module Nihongo
    module StringClassification

      def self.classify_char(char)
        case char[0]&.ord
        # Latin
        when 0x0000..0x001f, 0x007f then :control
        when 0x0020..0x002f then :latin_punctuation
        when 0x0030..0x0039 then :latin_numeric
        when 0x003a..0x0040 then :latin_punctuation
        when 0x0041..0x005a then :latin_alpha
        when 0x005b..0x0060 then :latin_punctuation
        when 0x0061..0x007a then :latin_alpha
        when 0x007b..0x007e then :latin_punctuation
        # 日本語
        when 0x3040..0x309f then :hiragana
        when 0x30a0..0x30ff then :katakana
        when 0x4e00..0x9faf then :kanji
        when 0x3000..0x303f then :cjk_punctuation
        when 0xff00..0xffef then :half_and_full_width
        # Else/Other
        else :other
        end
      end

      def is_latin_class(classification)
        classification.start_with?('latin')
      end

      def self.classify_chars(string)
        string.chars.map { |c| classify_char(c) }
      end

      # A purpose built string classifier with the following rules:
      # 1. If we only have one classification, use it!
      # 2. It prefers 日本語 to Latin, choosing latin only if there are no japanese characters present.
      # 3. If any character is kanji, we classify as kanji.
      # 4. If it has a mix of kana, we choose the most frequently seen one.
      # 5. If it is only latin categories, we go with :latin
      # 5. Otherwise we aren't sure and give :other
      #
      # These rules allow us to classify some weirder entries such as:
      # - 'あまり + negative' => 'HIRAGANA',
      # - '全然 + negative' => 'KANJI',
      # - 'Ｔシャツ' => 'KATAKANA'
      def self.classify_string(string)
        char_classes = classify_chars(string)
        class_freqs = char_classes.group_by { |c| c }.transform_values(&:length)

        if char_classes.length == 1
          char_classes.first
        elsif class_freqs.keys.include?(:kanji)
          :kanji
        elsif class_freqs.keys.include?(:hiragana) || class_freqs.keys.include?(:katakana)
          class_freqs.fetch(:hiragana, 0) >= class_freqs.fetch(:katakana, 0) ? :hiragana : :katakana
        elsif char_classes.all? {|classification| classification.start_with?('latin')}
          :latin
        else
          :other
        end
      end

    end
  end
end
