require_relative '../model/vocabulary'

module Goi
  module Transformer
    class DuoLessonCodeTransformer < BaseTransformer

      LEGACY_DUO_LESSON_CODE_REGEX = /^DUO_(\d+)_(\d+)$/

      def transform(linkages:)
        linkages.map do |ln|
          lesson_codes = flat_map_lesson_codes(ln.vocabulary.lesson_codes)
          vocabulary = ln.vocabulary.copy(lesson_codes:)
          ln.copy(vocabulary:)
        end
      end

      private

      def flat_map_lesson_codes(lesson_codes)
        lesson_codes.flat_map do |lesson_code|
          duo_lesson_code?(lesson_code) ? [map_legacy_code(lesson_code), lesson_code] : [lesson_code]
        end.uniq
      end

      def duo_lesson_code?(lesson_code) = lesson_code =~ LEGACY_DUO_LESSON_CODE_REGEX

      def make_new_code(unit_number) = "DUO_U#{ '%02d' % unit_number}"

      def map_legacy_code(duo_lesson_code)
        match_data = duo_lesson_code.match(LEGACY_DUO_LESSON_CODE_REGEX)
        _, section, subsection = match_data.to_a

        # Each unit is 2 of the old lessons, giving us a nice mathematical mapping.
        # Since Lesson 02-01 is the start of Unit 6, this makes it extra nice, since we can use an offest.
        # I never got far enough to need to map anything Lesson 02-xx so I don't even know how to map it if I wanted to.
        case(section.to_i)
        when 1
          make_new_code((subsection.to_i / 2.0).ceil)
        when 2
          make_new_code((subsection.to_i / 2.0).ceil + 5)
        else
          raise RuntimeError, "Don't know how to map legacy code to new code: #{duo_lesson_code.inspect}"
        end

      end

    end
  end
end

