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

# TODO: Use OptionParser lib instead of hacky custom impl.
def select_mode(mode_arg)
  mode_arg_sym = mode_arg&.strip&.downcase&.to_sym
  if RUN_CONFIGURATIONS.has_key?(mode_arg_sym)
    mode_arg_sym
  else
    $stderr.puts("Unknown mode #{mode_arg_sym&.to_s.inspect}; provide a valid mode from: #{RUN_CONFIGURATIONS.keys.map(&:to_s).inspect}")
    exit(1)
  end
end

mode = select_mode(ARGV[0])
$stderr.puts("RUN MODE: #{mode.inspect}")
run_configuration = RUN_CONFIGURATIONS[mode]
$stderr.puts("RUN CONFIGURATION: #{run_configuration.inspect}")

Goi::Application::PipelineApp.new(**run_configuration).run
