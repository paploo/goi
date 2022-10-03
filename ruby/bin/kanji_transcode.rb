require 'pathname'
require 'json'

require 'pg'
require 'sequel'
require 'sequel/extensions/pg_array'

require_relative '../lib/goi'
require_relative '../lib/goi/kanji'

module Goi
  module Bin
    module KanjiTranscode

      class Application

        def initialize(config:)
          @config = config
          @importer = Goi::Kanji::KanjiDICImporter.new
          @db = nil
        end

        attr_reader :config

        def db
          Sequel.extension :pg_array
          @db ||= Sequel.postgres(config.db_config).tap do |db|
            # Not needed for export, but part of proper setup for pg_array support.
            db.extension(:pg_array)
          end
        end

        def run
          kanji = @importer.import
          ordered_kanji = kanji.sort_by { |k| [-k.jlpt_level.to_i, k.frequency_ranking || 99_999, k.grade_level || 99, k.character] }
          export_to_kanji_gen(ordered_kanji)
          export_to_sql(ordered_kanji)
        end

        private

        # Outputs to a format that works with https://github.com/jensechu/kanji
        # Specifically used in my fork at https://github.com/paploo/kanji-gen
        def export_to_kanji_gen(kanji)
          json_data = {
            "kanji" => kanji.map do |k|
              {
                "category": "jlptn#{k.jlpt_level.to_i}",
                "character": k.character,
                "onyomi": k.on_readings&.join(', ') || '',
                "kunyomi": k.kun_readings&.join(', ') || '',
                "meaning": k.meanings.join(', ')
              }
            end
          }

          json = JSON.pretty_generate(json_data, indent: '  ')

          File.open(config.kanji_gen_file, 'w') do |io|
            io.write(json)
          end

        end

        def export_to_sql(kanji)
          File.open(config.kanji_sql_file, 'w') do |io|
            kanji.each do |k|
              sql = db[Sequel[:kanji][:kanji_character]].insert_sql(to_record(k))
              raise if sql.nil? || sql.empty?
              io.puts("-- #{k.character} (U+#{k.unicode_code_point_hex}) jlpt: #{k.jlpt_level}, grade_level: #{k.grade_level}, freq: #{k.frequency_ranking}")
              io.puts("#{sql};")
            end
          end
        end

        def to_record(kanji)
          {
            id: kanji.id,
            character: kanji.character,
            unicode_code_point: kanji.unicode_code_point_int,
            meanings: sql_str_array(kanji.meanings),
            on_readings: sql_str_array(kanji.on_readings),
            kun_readings: sql_str_array(kanji.kun_readings),
            nanori_readings: sql_str_array(kanji.nanori_readings),
            stroke_count: kanji.stroke_count,
            jlpt_level: kanji.jlpt_level,
            grade_level: kanji.grade_level,
            frequency_ranking: kanji.frequency_ranking
          }
        end

        def sql_str_array(string_array)
          Sequel.pg_array(string_array, :text)
        end

        class Config

          def initialize(output_dir:, db_config:)
            @output_dir = output_dir
            @db_config = db_config
          end

          attr_reader :db_config

          def kanji_gen_file = @output_dir + 'kanji_gen.json'

          def kanji_sql_file = @output_dir + 'kanji.sql'

        end

      end

    end
  end
end

##### SCRIPT #####

config = Goi::Bin::KanjiTranscode::Application::Config.new(
  output_dir: Goi::Core::Resources::FILES_DIR,
  db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
)

Goi::Bin::KanjiTranscode::Application.new(config:).run