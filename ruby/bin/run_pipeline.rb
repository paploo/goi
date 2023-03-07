# frozen_string_literal: true

require 'pathname'

require_relative '../lib/goi/pipeline'

module Goi
  module Bin
    module Pipeline

      class Application

        VOCABULARY_PIPELINE = Goi::Pipeline::Vocabulary::Library.default(db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
                                                                         infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'))

        def run
          VOCABULARY_PIPELINE.run
        end

      end


    end
  end
end

Goi::Bin::Pipeline::Application.new.run