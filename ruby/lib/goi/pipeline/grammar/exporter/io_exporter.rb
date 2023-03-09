# frozen_string_literal: true

require 'pp'

module Goi
  module Pipeline
    module Grammar
      module Exporter
        class IOExporter < Grammar::Exporter::Base

          def initialize(io:)
            super()
            @io = io
          end

          attr_reader :io

          def export(hydrated_rules)
            max_width = ENV['MAX_COLUMNS']&.to_i
            width = [PP.width_for(io), max_width].compact.min
            hydrated_rules.each { |rule| PP.pp(rule, io, width) }
          end

        end
      end
    end
  end
end
