# frozen_string_literal: true

require 'pp'

module Goi
  module Pipeline
    module Vocabulary
      module Exporter
        class IOExporter < Vocabulary::Exporter::Base

          def initialize(io:)
            super()
            @io = io
          end

          attr_reader :io

          def export(linkages)
            max_width = ENV['MAX_COLUMNS']&.to_i
            width = [PP.width_for(io), max_width].compact.min
            linkages.each { |link| PP.pp(link, io, width) }
          end

        end
      end
    end
  end
end
