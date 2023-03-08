module Goi
  module Pipeline
    module Vocabulary
      module Exporter
        class Base < Pipeline::Core::Exporter
          def export(linkages) = raise NotImplementedError

          private

          def open_path(outfile_pathname, &block)
            if outfile_pathname.nil?
              # If nil, just dump to standard out.
              block.call($stdout)
            else
              # Ensure the directory exists
              outfile_pathname.dirname.mkpath unless outfile_pathname.exist?

              # Now write!
              File.open(outfile_pathname, 'w') do |file|
                block.call(file)
              end
            end
          end

        end
      end
    end
  end
end
