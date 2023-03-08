# frozen_string_literal: true

require 'pathname'

require_relative '../lib/goi/pipeline'

module Goi
  module Bin
    module Pipeline

      class Application

        def initialize(config:)
          @pipeline_factory = Goi::Pipeline::Factory.new(config:)
        end

        attr_reader :pipeline_factory

        def run
          default_vocabulary_pipeline.run
        end

        private

        def default_vocabulary_pipeline
          pipeline_factory.vocabulary_pipeline
        end

      end

    end
  end
end

config = Goi::Pipeline::Factory::Config.new(
  db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
  infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
  output_dir_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'vocabulary')
)

Goi::Bin::Pipeline::Application.new(config:).run
