# frozen_string_literal: true

module Goi
  module Pipeline
    module Core
      class SafeProcessor

        def initialize(id_getter:, io: $stderr)
          @id_getter = id_getter
          @io = io
        end

        def process(records, &block)
          error_ids = []
          start_t = Time.now
          log("[#{self.class.name}] writing #{records.length} records.")

          records.each do |record|
            begin
              block.call(record)
            rescue => err
              id = get_id(record)
              msg = "[#{self.class.name}] WRITE ERROR: cannot write record ID #{id} due to error: #{err.full_message}"
              log(msg)
              error_ids << id
            end
          end

          delta_t = Time.now - start_t
          rate = records.length.to_f / delta_t
          unless error_ids.empty?
            log("#{self.class.name} Failed to process #{error_ids.length} record for ids:")
            error_ids.each { |id| io.puts "\t#{id}" }
          end
          log("[#{self.class.name}] wrote #{records.length} records with #{error_ids.length} errors in #{delta_t} seconds (#{rate} rec/sec)")
        end

        private

        attr_reader :io

        attr_reader :id_getter

        def get_id(record)
          id_getter.is_a?(Symbol) ? record.send(record) : id_getter.call(record)
        end

        def log(message)
          io&.puts(message)
        end

      end
    end
  end
end
