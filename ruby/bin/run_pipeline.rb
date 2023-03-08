# frozen_string_literal: true

require 'pathname'

require_relative '../lib/goi/pipeline'

# TODO: Make general for any pipeline and put in goi library; then we can have multiple CLI apps if we want with minimal code.
module Goi
  module Bin
    module Pipeline

      class Application

        def initialize(config:)
          @pipeline_factory = Goi::Pipeline::Factory.new(config:)
        end

        attr_reader :pipeline_factory

        def run
          #pipeline_factory.vocabulary_pipeline.run
          pipeline_factory.grammar_pipeline.run
        end

      end

    end
  end
end

# config = Goi::Pipeline::Factory::Config.new(
#   db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
#   infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
#   output_dir_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'vocabulary')
# )

config = Goi::Pipeline::Factory::Config.new(
  db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
  infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Grammar.json'),
  output_dir_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'grammar')
)

Goi::Bin::Pipeline::Application.new(config:).run
