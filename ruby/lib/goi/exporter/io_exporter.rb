require 'pp'

require_relative 'base_exporter'

module Goi
  module Exporter
    class IOExporter < BaseExporter

      def export(linkages:)
        config_out_open do |io|
          linkages.each { |link| PP.pp(link, io) }
        end
      end

    end
  end
end
