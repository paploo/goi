module Goi
  module Exporter
    class Config

      def initialize(file_pathname: nil, db_config: nil)
        @file_pathname = file_pathname
        @db_config = db_config
      end

      attr_reader :file_pathname, :db_config

    end
  end
end
