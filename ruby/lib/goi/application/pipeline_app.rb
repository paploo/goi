# frozen_string_literal: true

require_relative '../../../lib/goi/pipeline'

module Goi
  module Application
    class PipelineApp

      def initialize(pipeline_identifier:, pipeline_config:)
        @pipeline_factory = Goi::Pipeline::Factory.new(config: pipeline_config)
        @pipeline_identifier = pipeline_identifier
      end

      attr_reader :pipeline_factory
      attr_reader :pipeline_identifier

      def run
        unless pipeline_factory.respond_to?(pipeline_identifier.to_sym)
          raise ArgumentError, "No pipeline defined for identifier: #{pipeline_identifier}"
        end

        pipeline = pipeline_factory.send(pipeline_identifier.to_sym)
        pipeline.run
      end

    end
  end
end
