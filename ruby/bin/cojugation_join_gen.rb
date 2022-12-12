require_relative '../lib/goi/model/vocabulary'

# A REALLY HACKY script to build the conjugation view join
class ConjugationJoinGenApplication

  def initialize(io:)
    @io = io
  end

  def run
    JoinOnAllStrategy.new(with: with_section).write(io)
  end

  private

  attr_reader :io

  def with_section
    <<~WITH_SECTION
      with conjugations as (
      select distinct on (conjugation_set_id, politeness_code, charge_code, form_code)
          conjugation_set_id,
          politeness_code,
          charge_code,
          form_code,
          value
      from vocabulary.conjugation
      order by conjugation_set_id, politeness_code, charge_code, form_code, sort_rank asc
      )
    WITH_SECTION
  end

  class JoinOnAllStrategy

    def initialize(with:)
      @with = with
    end

    def write(io)
      io.write(with)
      io.write(project)
      io.write(joins)
      io.write(';')
    end

    private

    attr_reader :with

    def project
      projections = Goi::Model::Vocabulary::Conjugation::Inflection.all.map do |infl|
        "#{infl.code.downcase}.value as #{infl.code.downcase}"
      end

      separator = ",\n       "

      "select #{base_table}.conjugation_set_id as conjugation_set_id#{separator}" +
        projections.join(separator) + "\n"
    end

    def joins
      table_types, join_types = Goi::Model::Vocabulary::Conjugation::Inflection.all.partition { |infl| infl.code.downcase == base_table }

      separator = "\n    "

      "from conjugations as #{base_table}#{separator}" +
        join_types.map { |infl| join_clause(infl) }.join(separator) + "\n" +
        "where #{table_types.first.sql_filter}"
    end

    def base_table = "positive_plain_present"

    def join_clause(infl)
      "left join conjugations as #{infl.code.downcase} on #{infl.code.downcase}.conjugation_set_id = #{base_table}.conjugation_set_id and #{infl.sql_filter}"
    end

  end

end

# Extension to add sql_filter
class Goi::Model::Vocabulary::Conjugation::Inflection

  def sql_filter(table_name = code)
    filters = to_h.map do |col, code|
      "#{table_name}.#{col} = '#{code}'"
    end
    filters.join(' and ')
  end

end

ConjugationJoinGenApplication.new(io: STDOUT).run
