require 'date'
require 'sequel'

require_relative '../model/vocabulary'
require_relative '../nihongo'
require_relative 'base_importer'

module Goi
  module Importer

    class SequelImporter < BaseImporter

      def initialize
        super
        @db = Sequel.postgres('goi', user: 'postgres', password: 'postgres', host: 'localhost')
      end

      attr_reader :db

      def import(obj)
        library = Library.new(db:).load_all

        #TODO
        # - For each vocabulary:
        # - Instantiate the vocab object
        # - Using the linkages record data, build the defs and spellings objects
        # - Instantiate the linkages object
        # - Return the linkages object with everything attached.
        raise "EOF"
      end

      class Library

        def initialize(db:)
          @db = db
        end

        attr_reader :vocabulary, :definitions, :spellings, :linkages

        def load_all
          tap {
            @vocabulary = load_vocabulary
            @definitions = load_definitions.group_by { |d| d[:vocabulary_id] }
            @spellings = load_spellings.group_by { |d| d[:vocabulary_id] }
            @linkages = load_linkages.group_by { |d| d[:vocabulary_id] }.transform_values { |ls| ls.first }
          }
        end

        private

        attr_reader :db

        def load_vocabulary
          db[Sequel[:vocabulary][:vocabulary]].order(:row_num).all
        end

        def load_definitions
          db[Sequel[:vocabulary][:definition]].all
        end

        def load_spellings
          db[Sequel[:vocabulary][:spelling]].all
        end

        def load_linkages
          db[Sequel[:vocabulary][:linkages]].all
        end
      end

    end

  end
end
