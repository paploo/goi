# frozen_string_literal: true

require 'pathname'

require_relative '../lib/goi/pipeline'

module Goi
  module Bin
    module Pipeline

      class Application

        def run
          default_vocabulary_pipeline.run
        end

        private

        CONFIG = {
          db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
          infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
          output_dir_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'vocabulary')
        }.freeze

        def default_vocabulary_pipeline
          Goi::Pipeline::Vocabulary::Library.default(**CONFIG)
        end

      end


    end
  end
end

Goi::Bin::Pipeline::Application.new.run
