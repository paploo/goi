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
            linkages.each { |link| PP.pp(link, io) }
          end

        end
      end
    end
  end
end
