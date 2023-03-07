require 'pp'

require_relative '../core'

module Goi
  module Exporter
    module Vocabulary

      class IOExporter < Goi::Exporter::Core::BaseExporter

        def export(linkages:)
          config_out_open do |io|
            linkages.each { |link| PP.pp(link, io) }
          end
        end

      end

    end
  end
end
