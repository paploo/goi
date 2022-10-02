require 'pathname'

require_relative '../lib/goi'
require_relative '../lib/goi/kanji'


kanji = Goi::Kanji::KanjiDICImporter.new.import

puts kanji[0].inspect
puts kanji.find { |it| it.kanji == '人' }.inspect