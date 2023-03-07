module Goi
  module Exporter
    module Core
      class BaseExporter

        def initialize(config:)
          @config = config
        end

        attr_reader :config

        def export(linkages:)
          # Do nothing
        end

        private

        def config_out_open(&block)
          if @config.file_pathname.nil?
            block.call(STDOUT)
          else
            File.open(@config.file_pathname, 'w') do |file|
              block.call(file)
            end
          end
        end

      end
    end
  end
end