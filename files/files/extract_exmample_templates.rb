#!/usr/bin/env ruby
# frozen_string_literal: true
# encoding: UTF-8

# Extract unique furigana template mappings from lines containing a JSON
# key named "spelling". Read from STDIN and write pairs to STDOUT as
#   key -> value
# One line per pair, sorted alphabetically by key then value.

require 'set'

input = STDIN.read

pairs = Set.new

input.each_line do |line|
  # Only process lines that contain the JSON spelling field
  next unless line.include?('"spelling"')

  # Find all templates of the form {kanji|reading}
  # Left side may be kanji/kana; right side may include middle dots, etc.
  line.scan(/\{([^|{}]+)\|([^{}]+)\}/) do |key, value|
    key = key.strip
    value = value.strip
    pairs.add([key, value])
  end
end

pairs.to_a
     .sort_by { |k, v| [k, v] }
     .each { |k, v| puts "#{k} -> #{v}" }
