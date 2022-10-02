module Goi
  module Kanji

    class KanjiSHDictionary

      def load_data!
        load_jlpt!
        self
      end

      attr_reader :jlpt_kanji_index
      attr_reader :jlpt_kanji_lists

      private

      def load_jlpt!
        @jlpt_kanji_index = {}
        @jlpt_kanji_lists = {}

        # For each level:
        (1..5).each do |jlpt_level|
          # Load from file
          file_path = Goi::Core::Resources.at_path('kanjish', 'jlpt', "n#{jlpt_level}.source")
          kanjis = File.read(file_path.to_s).split("\n").map(&:strip)

          # Assign the list
          @jlpt_kanji_lists[jlpt_level] = kanjis

          # Make the kanji map
          kanjis.each do |kanji|
            @jlpt_kanji_index[kanji] = jlpt_level
          end
        end

        self
      end

    end

  end
end
