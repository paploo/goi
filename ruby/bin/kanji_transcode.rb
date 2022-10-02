require 'pathname'
require 'json'

require 'pg'
require 'sequel'

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
          @db ||= Sequel.postres(config.db_config)
        end

        def run
          kanji = @importer.import
          ordered_kanji = kanji.sort_by { |k| [-k.jlpt_level.to_i, k.frequency_ranking || 99_999, k.grade || 99, k.character] }
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
              io.puts("-- #{k.character} jlpt: #{k.jlpt_level}, grade: #{k.grade}, freq: #{k.frequency_ranking}")
              io.puts('')
            end
          end
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