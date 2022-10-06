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
      io.write(select)
      io.write(joins)
      io.write(';')
    end

    private

    attr_reader :with

    def select
      projections = ConjugationType.all.map do |tpe|
        "#{tpe.type_name}.value as #{tpe.type_name}"
      end

      separator = ",\n       "

      "select #{base_table}.conjugation_set_id as conjugation_set_id#{separator}" +
        projections.join(separator) + "\n"
    end

    def joins
      table_types, join_types = ConjugationType.all.partition { |tpe| tpe.type_name == base_table }

      separator = "\n    "

      "from conjugations as #{base_table}#{separator}" +
        join_types.map { |tpe| join_clause(tpe) }.join(separator) + "\n" +
        "where #{table_types.first.sql_filter}"
    end

    def base_table = "positive_plain_present"

    def join_clause(tpe)
      "left join conjugations as #{tpe.type_name} on #{tpe.type_name}.conjugation_set_id = #{base_table}.conjugation_set_id and #{tpe.sql_filter}"
    end

  end

  class ConjugationType

    def self.all
      Goi::Model::Vocabulary::Conjugation.map_dims do |charge_code, politeness_code, form_code|
        self.new(charge_code:, politeness_code:, form_code:)
      end
    end

    def initialize(charge_code:, politeness_code:, form_code:)
      @charge_code = charge_code
      @politeness_code = politeness_code
      @form_code = form_code
    end

    attr_reader :charge_code, :politeness_code, :form_code

    def to_a = [charge_code, politeness_code, form_code]

    def to_h = {charge_code: charge_code, politeness_code: politeness_code, form_code: form_code}

    def type_name = to_a.join('_').downcase

    def sql_filter(table_name = type_name)
      filters = to_h.map do |col, code|
        "#{table_name}.#{col} = '#{code}'"
      end
      filters.join(' and ')
    end

  end

end

ConjugationJoinGenApplication.new(io: STDOUT).run
