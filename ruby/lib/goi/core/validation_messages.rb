module Goi
  module Core

    module ValidationReportable

      LEVELS = [:error, :warn, :info, :debug].freeze
      LEVEL_ICONS = {error: "🛑", warn: "⚠️", info: "ℹ️", debug: "🐛"}.freeze

      def count(level:) = 0

      def empty? = LEVELS.map { |level| count(level:) }.sum.zero?

      def summary = LEVELS.map { |level| [level, count(level:)] }.to_h

      def formatted_summary
        ValidationMessage::LEVELS.map do |level|
          "#{count(level:)} #{ValidationMessage::LEVEL_ICONS[level]}"
        end.join("  ")
      end

      def formatted(indent_level = 0) = indent(formatted_summary, indent_level:)

      def to_s = "#{self.class.name}(#{summary.inspect})"

      private

      def indent(string, indent_level:)
        '    ' * indent_level + string
      end

    end

    class ValidationMessage
      include ValidationReportable

      def self.error(message) = new(level: :error, message: message)

      def self.warn(message) = new(level: :warn, message: message)

      def self.info(message) = new(level: :info, message: message)

      def self.debug(message) = new(level: :debug, message: message)

      def initialize(level:, message:)
        @level = level
        @message = message
      end

      attr_reader :level
      attr_reader :message

      def count(level:)
        level == self.level ? 1 : 0
      end

      def formatted(indent_level = 0)
        msg = "#{LEVEL_ICONS[level]} #{message}"
        indent(msg, indent_level:)
      end

    end

    class ValidationReport
      include ValidationReportable

      def initialize(title:, messages:)
        @title = title
        @messages = messages
      end

      attr_reader :title
      attr_reader :messages

      def count(level:)
        messages.inject(0) { |count,msg|  count + msg.count(level:) }
      end

      def formatted(indent_level = 0)
        header = indent(title, indent_level:)
        lines = [header] + messages.map { |m| m.formatted(indent_level+1) }
        lines.join("\n")
      end

    end

  end
end
