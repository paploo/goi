require 'csv'
require 'pathname'
require 'pp'

require_relative '../lib/goi'
require_relative '../lib/goi/sqlize'

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
    @file = file
    @csv_converter = CSVReadConverter.new
    @sql_converter = SQLWriteConverter.new
  end

  attr_reader :file, :csv_converter, :sql_converter

  def run
    puts file
    rows = CSV.read(file, headers: true)
    rows = [0, 6, 9, 19, 31, 37, 38, 61, 156, 211, 212, 224].map { |i| rows[i] }

    data = csv_converter.convert(rows)
    sql_groups = sql_converter.convert(data)

    pairs = data.zip(sql_groups)
    pairs.each do |v, group|
      puts('')
      puts("-- #{v.preferred_spelling.value} /  #{v.definition.value}")
      group.each do |line|
        puts(line)
      end
    end
  end

end

class CSVReadConverter

  JAPANESE_KEY = '日本語'.freeze
  PHONETIC_KEY = '日本語ひらがな'.freeze
  ENGLISH_KEY = 'English'.freeze
  KANA_PREFERRED_KEY = 'Kana Preferred?'.freeze
  WORD_CLASS_KEY = 'Type'.freeze
  TAGS_KEY = 'Tags'.freeze
  SORT_RANK_KEY = 'Index'.freeze
  GENKI_LESSON_KEY = 'Genki'.freeze
  DUO_LESSON_KEY = 'Duolingo'.freeze
  LULILANGUAGE_LESSON_KEY = 'Lulilanguage'.freeze

  WORD_CLASS_MAP = {
    'Noun' => 'NOUN',
    'Proper Noun' => 'PROPER_NOUN',
    'Pronoun' => 'PRONOUN',
    'Adjective' => 'ADJECTIVE',
    'Verb' => 'VERB',
    'Adverb' => 'ADVERB',
    'Interjection' => 'INTERJECTION',
    'Expression' => 'EXPRESSION',
    'Phrase' => 'EXPRESSION' # Original Spreadsheet data was inconsistent
  }.freeze

  CONJUGATION_TYPE_MAP = {
    'u_verb' => 'GODAN_VERB',
    'ru_verb' => 'ICHIDAN_VERB',
    'irregular_verb' => 'IRREGULAR_VERB',
    'mu_verb' => 'GODAN_VERB',
    'su_verb' => 'GODAN_VERB',
    'i_adjective' => 'I_ADJECTIVE'
  }.freeze

  def convert(rows)
    rows.map { |r| convert_row(r) }
  end

  def convert_row(row)
    pref, phon, aux = classify_spellings(row[JAPANESE_KEY].clean, row[PHONETIC_KEY].clean, kana_preferred?(row))
    word_class, conjugation_code = classify_word(row[WORD_CLASS_KEY].clean)
    Vocabulary.new(
      parse_definition(row[ENGLISH_KEY].clean),
      pref,
      phon,
      aux,
      word_class,
      conjugation_code,
      row[SORT_RANK_KEY].clean.to_i,
      parse_tags(row[TAGS_KEY].clean),
      classify_references(row[GENKI_LESSON_KEY].clean, row[DUO_LESSON_KEY].clean, row[LULILANGUAGE_LESSON_KEY].clean)
    )
  end

  private

  def kana_preferred?(row)
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
    raise RuntimeError, 'Expected Japanese value' if japanese.nil?

    # A few phonetic entries used `／` to give multiple kana spellings
    phonetic, *alternates = phonetic && split_multiple_spellings(phonetic)

    alt_sp = alternates.map { |a| parse_spelling(a) }

    if is_kana_preferred
      [parse_spelling(phonetic), parse_spelling(phonetic), [parse_spelling(japanese)].concat(alt_sp)]
    else
      [parse_spelling(japanese), parse_spelling(phonetic), alt_sp]
    end
  end

  def split_multiple_spellings(string)
    string.split('／')
  end

  def classify_word(value)
    raw_type, raw_conj = value.split(';').map(&:clean)
    [parse_word_class(raw_type), raw_conj && parse_conjugation_kind(raw_conj)]
  end

  def parse_word_class(raw_type)
    if WORD_CLASS_MAP.key?(raw_type)
      WORD_CLASS_MAP[raw_type]
    else
      raise RuntimeError, "Do not know how to classify word class for: #{raw_type}"
    end
  end

  def parse_conjugation_kind(raw_conj)
    if CONJUGATION_TYPE_MAP.key?(raw_conj)
      CONJUGATION_TYPE_MAP[raw_conj]
    elsif WORD_CLASS_MAP.key?(raw_conj)
      # Don't do anything; we have, like, two of these and don't care.
    else
      raise RuntimeError, "Do not know how to classify conjugation type for: #{raw_conj}"
    end
  end

  def parse_tags(raw_tags)
    raw_tags&.split(/,?\s+/)&.reject do |tag|
      CONJUGATION_TYPE_MAP.keys.include?(tag)
    end || []
  end

  def classify_references(genki_raw, duo_raw, luli_raw)
    [
      genki_raw && parse_genki_ref(genki_raw),
      duo_raw && parse_duo_ref(duo_raw),
      luli_raw && parse_luli_ref(luli_raw)
    ].compact
  end

  def parse_genki_ref(genki_raw)
    raise RuntimeError, "Unknown genki ref: #{genki_raw}" unless genki_raw =~ /^L\d\d$/
    "GENKI3_#{genki_raw}"
  end

  def parse_duo_ref(duo_raw)
    lesson = duo_raw.split(/\s+/)[0].tr('-', '_')
    raise RuntimeError, "Unknown duo ref: #{duo_raw}" unless lesson =~ /^\d\d_\d\d$/
    "DUO_#{lesson}"
  end

  def parse_luli_ref(luli_raw)
    raise RuntimeError, "Unknown luli ref: #{luli_raw}"
  end

end

class SQLWriteConverter

  def convert(vocab)
    vocab.map { |v| convert_vocab(v) }
  end

  def convert_vocab(vocab)
    [
      vocab_sql(vocab),
      definition_sql(vocab.definition)
    ].concat(
      all_spellings_sql(vocab.spellings)
    ).concat(
      [linkages_sql(vocab)]
    ).concat(
      references_sql(vocab.references)
    )
  end

  private

  def vocab_sql(vocab)
    id = vocab.id.sqlize
    word_class_code = vocab.word_class_code.sqlize
    conjugation_type_code = vocab.conjugation_kind_code.sqlize
    rank = vocab.sort_rank.sqlize
    tags = vocab.tags.sqlize  # TODO: Need a formatter for arrays of strings that has single quotes on it so can be used with nullable
    "insert into vocabulary.vocabulary (id, word_class_code, conjugation_kind_code, jlpt_level, tags) values (#{id}, #{word_class_code}, #{conjugation_type_code}, #{rank}, #{tags});"
  end

  def definition_sql(definition)
    definition && 'def'
  end

  def all_spellings_sql(spellings)
    spellings.map { |sp| 'sp' }
  end

  def linkages_sql(link)
    'links'
  end

  def references_sql(refs)
    refs.map { |ref| 'ref' }
  end

end

class Vocabulary

  def initialize(definition, preferred_spelling, phonetic_spelling, auxiliary_spellings, word_class_code, conjugation_kind_code, sort_rank, tags, references)
    @definition = definition
    @preferred_spelling = preferred_spelling
    @phonetic_spelling = phonetic_spelling
    @auxiliary_spellings = auxiliary_spellings
    @word_class_code = word_class_code
    @conjugation_kind_code = conjugation_kind_code
    @sort_rank = sort_rank
    @tags = tags
    @references = references
  end

  attr_reader :definition, :preferred_spelling, :phonetic_spelling, :auxiliary_spellings, :word_class_code, :conjugation_kind_code, :sort_rank, :tags, :references

  def id
    @id ||= Goi::EntityIDTools.vocabulary_uuid(preferred_spelling.value)
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
    @id ||= Goi::EntityIDTools.definition_uuid(value)
  end

end

class Spelling

  KIND_EXCEPTIONS = {
    'あまり + negative' => 'HIRAGANA',
    '全然 + negative' => 'KANJI',
    'ぜんぜん + negative' => 'HIRAGANA',
    'Ｔシャツ' => 'KATAKANA'
  }.freeze

  def self.determine_kind(string)
    if string.hiragana?
      'HIRAGANA'
    elsif string.katakana?
      'KATAKANA'
    elsif string.chars.any?(&:kanji?)
      'KANJI'
    elsif KIND_EXCEPTIONS.key?(string)
      KIND_EXCEPTIONS[string]
    else
      raise RuntimeError, "Could not determine spelling kind for value: #{string}"
    end
  end

  def initialize(value, kind = nil)
    @value = value
    @kind = kind.nil? ? self.class.determine_kind(value) : kind
  end

  attr_reader :value, :kind

  def id
    @id ||= Goi::EntityIDTools.spelling_uuid(value)
  end

end

file = File.expand_path(File.join(File.dirname(__FILE__), '..', '..', 'tmp', 'vocab.csv'))
Application.new(file).run
