require 'pp'

require_relative 'base_exporter'

module Goi
  module Exporter
    class IOExporter < BaseExporter

      def initialize(io: STDOUT)
        super()
        @io = io
      end

      attr_reader :io

      def export(linkages:)
        linkages.each { |link| PP.pp(link, io) }
      end

    end
  end
end
