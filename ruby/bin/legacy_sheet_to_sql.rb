require 'csv'
require 'pathname'
require 'pp'

require File.expand_path( File.join( File.dirname(__FILE__), '..', 'lib', 'goi' ) )

#
# This application is a one-off for parsing my existing vocabulary spreadsheet
# into INSERT statements for the standardized data store.
#
# The plan is to replace the spreadsheet with one whsoe schema more trivially
# matches, allowing for a script that can easily read the file to idempotently
# create or update the appropriate table rows.
#
class Application

    def initialize(file)
        self.file = file
    end

    attr_accessor :file

    def run
        puts file
        rows = CSV.read(file, headers: true)
        rows = [0, 6, 9, 19, 31, 37, 38, 61, 211, 212].map { |i| rows[i] }
        data = CSVReadConverter.new().convert(rows)
        PP.pp(data)
    end

end

class CSVReadConverter

    JAPANESE_KEY = '日本語'
    PHONETIC_KEY = '日本語ひらがな'
    ENGLISH_KEY = 'English'
    KANA_PREFERRED_KEY = 'Kana Preferred?'
    WORD_CLASS_KEY = 'Type'
    TAGS_KEY = 'Tags'
    SORT_RANK_KEY = 'Index'
    GENKI_LESSON_KEY = 'Genki'
    DUO_LESSON_KEY = 'Duolingo'
    LULILANGUAGE_LESSON_KEY = 'Lulilanguage'

    WORD_CLASS_MAP = {
        'Noun' => 'NOUN',
        'Proper Noun' => 'PROPER_NOUN',
        'Adjective' => 'ADJECTIVE',
        'Verb' => 'VERB',
        'Adverb' => 'ADVERB',
        'Interjection' => 'INTERJECTION',
        'Expression' => 'EXPRESSION',
        'Phrase' => 'EXPRESSION' #Original Spreadsheet data was inconsistent
    }

    CONJUGATION_TYPE_MAP = {
        'u_verb' => 'GODAN_VERB',
        'ru_verb' => 'ICHIDAN_VERB',
        'irregular_verb' => 'IRREGULAR_VERB',
        'mu_verb' => 'GODAN_VERB',
        'su_verb' => 'GODAN_VERB',
        'i_adjective' => 'I_ADJECTIVE'
    }

    def convert(rows)
        rows.map { |r| convert_row(r) }
    end

    def convert_row(row)
        pref, phon, aux = classify_spellings(row[JAPANESE_KEY].clean, row[PHONETIC_KEY].clean, is_kana_preferred(row))
        word_class, conjugation_code = classify_word(row[WORD_CLASS_KEY].clean)
        Vocabulary.new(
            definition = parse_definition(row[ENGLISH_KEY].clean),
            preferred_spelling = pref,
            phonetic_spelling = phon,
            auxiliary_spellings = aux,
            word_class_code = word_class,
            conjugation_kind_code = conjugation_code,
            sort_rank = row[SORT_RANK_KEY].clean.to_i,
            tags = parse_tags(row[TAGS_KEY].clean),
            references = nil
        )
    end

    private

    def is_kana_preferred(row)
        row[KANA_PREFERRED_KEY].clean.then { |s| s && s[0].downcase == 'y' }
    end

    def parse_definition(value)
        value && Definition.new(value)
    end

    def parse_spelling(value)
        value && Spelling.new(value)
    end

    # Rules for returning (preferrred, phonetic, [aux1, aux2, ...]):
    # jp is never nil/empty!
    # (jp, phon, true) => (phon, phon, [jp])
    # (jp, phon, false) => (jp, phon, [])
    # (jp, nil, true) => !!doesn't happen!!
    # (jp, nil, false) =>  (jp, phon, [])
    #
    # Also, we have a phonetic spelling with two variations with a slash, where we need
    # To have one be phonetic and the other an alternate
    def classify_spellings(japanese, phonetic, is_kana_preferred)
        raise RuntimeError.new("Expected Japanese value") if japanese.nil?
        if (is_kana_preferred)
            [parse_spelling(phonetic), parse_spelling(phonetic), [parse_spelling(japanese)]]
        else
            [parse_spelling(japanese), parse_spelling(phonetic), []]
        end
    end

    def classify_word(value)
        raw_type, raw_conj = value.split(';').map(&:clean)
        [parse_word_class(raw_type), raw_conj && parse_conjugation_kind(raw_conj)]
    end

    def parse_word_class(raw_type)
        if(WORD_CLASS_MAP.has_key?(raw_type))
            WORD_CLASS_MAP[raw_type]
        else
            raise RuntimeError.new("Do not know how to classify word class for: #{raw_type}")
        end
    end

    def parse_conjugation_kind(raw_conj)
        if(CONJUGATION_TYPE_MAP.has_key?(raw_conj))
            CONJUGATION_TYPE_MAP[raw_conj]
        elsif(WORD_CLASS_MAP.has_key?(raw_conj))
            #Don't do anything; we have, like, two of these and don't care.
        else
            raise RuntimeError.new("Do not know how to classify conjugation type for: #{raw_conj}")
        end
    end

    def parse_tags(raw_tags)
        raw_tags && raw_tags.split(/,?\s+/).reject do |tag|
            CONJUGATION_TYPE_MAP.keys.include?(tag)
        end || []
    end

end

class Vocabulary

    def initialize(definition, preferred_spelling, phonetic_spelling, auxiliary_spellings, word_class_code, conjugation_kind_code, sort_rank, tags, references)
        @definition = definition
        @preferred_spelling = preferred_spelling
        @phonetic_spelling = phonetic_spelling
        @auxiliary_spellings = auxiliary_spellings
        @word_class_code = word_class_code
        @conjugation_kind_code = conjugation_kind_code,
        @sort_rank = sort_rank
        @tags = tags
        @references = references
    end

    attr_reader :definition, :preferred_spelling, :phonetic_spelling, :auxiliary_spellings
    attr_reader :word_class_code, :conjugation_kind_code, :sort_rank, :tags, :references

    def id
        Goi::EntityIDTools.vocabulary_uuid(pre)
    end

    def spellings
        [preferred_spelling, phonetic_spelling].concat(auxiliary_spellings).uniq
    end

end

class Definition

    def initialize(value)
        @value = value
    end

    attr_reader :value

    def id
        Goi::EntityIDTools.definition_uuid(value)
    end

end

class Spelling

    KIND_EXCEPTIONS = {
        'あまり + negative' => 'HIRIGANA',
        '全然 + negative' => 'KANJI',
        'ぜんぜん + negative' => 'HIRIGANA'
    }

    def self.determine_kind(string)
        if(string.hirigana?)
            'HIRIGANA'
        elsif(string.katakana?)
            'KATAKANA'
        elsif(string.chars.any?(&:kanji?) )
            'KANJI'
        elsif(KIND_EXCEPTIONS.has_key?(string))
            KIND_EXCEPTIONS[string]
        else
            raise RuntimeError.new("Could not determine spelling kind for value: #{string}")
        end
    end

    def initialize(value, kind = nil)
        @value = value
        @kind = kind.nil? ? self.class.determine_kind(value) : kind
    end

    attr_reader :value, :kind

    def id
        Goi::EntityIdTools.spelling_uuid(value)
    end

end

file = File.expand_path( File.join( File.dirname(__FILE__), '..', '..', 'tmp', 'vocab.csv' ) )
Application.new(file).run