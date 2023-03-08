# frozen_string_literal: true

require 'pathname'

require_relative '../lib/goi/application/pipeline_app'

RUN_CONFIGURATIONS = {
  vocabulary: {
    pipeline_identifier: :vocabulary_pipeline,
    pipeline_config: Goi::Pipeline::Factory::Config.new(
      db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
      infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Vocab.csv'),
      output_dir_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'vocabulary')
    )
  },
  grammar: {
    pipeline_identifier: :grammar_pipeline,
    pipeline_config: Goi::Pipeline::Factory::Config.new(
      db_config: { database: 'goi', user: 'postgres', password: 'postgres', host: 'localhost' },
      infile_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', '日本語 Vocab - Grammar.json'),
      output_dir_pathname: Pathname(__FILE__).expand_path.join('..', '..', '..', 'files', 'grammar')
    )
  }
}.freeze

#TODO: Select the configuration based on an argument
#TODO: Have an :all mode that runs through all pipeline run configurations.
run_configuration = RUN_CONFIGURATIONS[:grammar]

Goi::Application::PipelineApp.new(**run_configuration).run
