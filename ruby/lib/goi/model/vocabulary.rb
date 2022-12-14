require 'uuidtools'

module Goi
  module Model
    module Vocabulary

      # Linkages
      #
      # This ties together the various stand-alone
      class Linkages

        ATTRIBUTES = [
          :vocabulary,
          :preferred_definition,
          :preferred_spelling,
          :phonetic_spelling,
          :alt_phonetic_spelling,
          :kanji_spelling,
          :conjugation_set
        ].freeze

        def self.attributes = ATTRIBUTES

        def initialize(vocabulary:,
                       preferred_definition:,
                       preferred_spelling:,
                       phonetic_spelling:,
                       alt_phonetic_spelling: nil,
                       kanji_spelling: nil,
                       conjugation_set: nil)
          @vocabulary = vocabulary
          @preferred_definition = preferred_definition
          @preferred_spelling = preferred_spelling
          @phonetic_spelling = phonetic_spelling
          @alt_phonetic_spelling = alt_phonetic_spelling
          @kanji_spelling = kanji_spelling
          @conjugation_set = conjugation_set

          raise ArgumentError, "One or more links don't go with the given vocabulary" unless links_valid?
        end

        attr_reader :vocabulary
        attr_reader :preferred_definition
        attr_reader :preferred_spelling
        attr_reader :phonetic_spelling
        attr_reader :alt_phonetic_spelling
        attr_reader :kanji_spelling
        attr_reader :conjugation_set

        def copy(**props)
          args = self.class.attributes.map { |p| [p, props.fetch(p, send(p))] }.to_h
          self.class.new(**args)
        end

        private

        def links_valid?
          required_validity = [
            preferred_definition&.vocabulary_id,
            preferred_spelling&.vocabulary_id,
            phonetic_spelling&.vocabulary_id
          ].all? { |id| !id.nil? && id == vocabulary.id }

          optional_validity = [
            alt_phonetic_spelling&.vocabulary_id,
            kanji_spelling&.vocabulary_id
          ].all? { |id| id.nil? || id == vocabulary.id }

          required_validity && optional_validity
        end

      end

      # Vocabulary
      class Vocabulary

        UUID5_NAMESPACE = UUIDTools::UUID.parse('6228ddd3-7a0f-47e0-9bea-a15a0f491ca4').to_s

        ATTRIBUTES = [
          :id,
          :word_class_code,
          :conjugation_kind_code,
          :jlpt_level,
          :row_num,
          :date_added,
          :tags,
          :lesson_codes
        ].freeze

        # If no arguments are given, a Random UUID is assigned.
        # If argumetns are given, they are combined to make a predictable ID.
        def self.create_id(*data)
          if data.nil? || data.empty?
            UUIDTools::UUID.random_create.to_s
          else
            name = data.map(&:to_s).join('|')
            ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
            UUIDTools::UUID.sha1_create(ns, name).to_s
          end
        end

        def self.attributes = ATTRIBUTES

        def initialize(id: nil,
                       word_class_code:,
                       conjugation_kind_code: nil,
                       jlpt_level: nil,
                       row_num:,
                       date_added:,
                       tags: [],
                       lesson_codes: [])
          @id = id || self.class.create_id
          @word_class_code = word_class_code
          @conjugation_kind_code = conjugation_kind_code
          @jlpt_level = jlpt_level
          @row_num = row_num
          @date_added = date_added
          @tags = (tags || []).sort
          @lesson_codes = (lesson_codes || []).sort
        end

        attr_reader :id
        attr_reader :word_class_code
        attr_reader :conjugation_kind_code
        attr_reader :jlpt_level
        attr_reader :row_num
        attr_reader :date_added
        attr_reader :tags
        attr_reader :lesson_codes

        def copy(**props)
          args = self.class.attributes.map { |p| [p, props.fetch(p, send(p))] }.to_h
          self.class.new(**args)
        end

      end

      # Definition
      class Definition

        UUID5_NAMESPACE = UUIDTools::UUID.parse('c7812647-678a-4bf5-bed3-b33fe499469c').to_s

        ATTRIBUTES = [
          :id,
          :vocabulary_id,
          :value,
          :sort_rank
        ].freeze

        def self.create_id(vocabulary_id:, linkage_field:)
          name = [vocabulary_id, linkage_field].map(&:to_s).join('|')
          ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
          UUIDTools::UUID.sha1_create(ns, name).to_s
        end

        def self.attributes = ATTRIBUTES

        def initialize(id:, vocabulary_id:, value:, sort_rank: 1)
          @id = id || raise(ArgumentError, 'ID required')
          @vocabulary_id = vocabulary_id
          @value = value
          @sort_rank = sort_rank
        end

        attr_reader :id, :vocabulary_id, :value, :sort_rank

        def copy(**props)
          args = self.class.attributes.map { |p| [p, props.fetch(p, send(p))] }.to_h
          self.class.new(**args)
        end

      end

      # Spelling
      class Spelling

        UUID5_NAMESPACE = UUIDTools::UUID.parse('546a4b2c-6b83-4fe9-902e-7c7ade990930').to_s

        ATTRIBUTES = [
          :id,
          :vocabulary_id,
          :spelling_kind_code,
          :value
        ].freeze

        # Create an ID that is consistent for the linkage position.
        # This makes database syncing scripts a LOT easier to manage.
        def self.create_id(vocabulary_id:, linkage_field:)
          name = [vocabulary_id, linkage_field].map(&:to_s).join('|')
          ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
          UUIDTools::UUID.sha1_create(ns, name).to_s
        end

        def self.attributes = ATTRIBUTES

        def initialize(id:, vocabulary_id:, spelling_kind_code:, value:)
          @id = id || raise(ArgumentError, 'ID required')
          @vocabulary_id = vocabulary_id
          @spelling_kind_code = spelling_kind_code
          @value = value
        end

        attr_reader :id
        attr_reader :vocabulary_id
        attr_reader :spelling_kind_code
        attr_reader :value

        def copy(**props)
          args = self.class.attributes.map { |p| [p, props.fetch(p, send(p))] }.to_h
          self.class.new(**args)
        end

      end

      class ConjugationSet

        UUID5_NAMESPACE = UUIDTools::UUID.parse('8724ca34-1e4a-4e78-8474-b359cdf33b66').to_s

        ATTRIBUTES = [
          :id,
          :vocabulary_id,
          :conjugations
        ].freeze

        def self.create_id(vocabulary_id:)
          name = [vocabulary_id].map(&:to_s).join('|')
          ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
          UUIDTools::UUID.sha1_create(ns, name).to_s
        end

        def initialize(id:, vocabulary_id:, conjugations: [])
          @id = id || raise(ArgumentError, 'ID required')
          @vocabulary_id = vocabulary_id
          @conjugations = conjugations
        end

        attr_reader :id
        attr_reader :vocabulary_id
        attr_reader :conjugations

        def copy(**props)
          args = self.class.attributes.map { |p| [p, props.fetch(p, send(p))] }.to_h
          self.class.new(**args)
        end

      end

      class Conjugation

        class Inflection

          CHARGE_CODES = ['POSITIVE', 'NEGATIVE'].freeze

          POLITENESS_CODES = ['PLAIN', 'POLITE'].freeze

          FORM_CODES = [
            'PRESENT',
            'PAST',
            'TE',
            'CONDITIONAL_EBA',
            'CONDITIONAL_TARA',
            'POTENTIAL',
            'PASSIVE',
            'CAUSATIVE',
            'IMPERATIVE'
          ].freeze

          def self.all
            CHARGE_CODES.flat_map do |charge_code|
              POLITENESS_CODES.flat_map do |politeness_code|
                FORM_CODES.map do |form_code|
                  new(charge_code:, politeness_code:, form_code:)
                end
              end
            end
          end

          def initialize(charge_code:, politeness_code:, form_code:)
            @charge_code = charge_code
            @politeness_code = politeness_code
            @form_code = form_code
          end

          attr_reader :charge_code
          attr_reader :politeness_code
          attr_reader :form_code

          def to_a = [charge_code, politeness_code, form_code]

          def to_h = {charge_code: charge_code, politeness_code: politeness_code, form_code: form_code}

          def code = to_a.join('_')

          def to_s = to_a.join('-')

          def ==(other)
            if other.is_a?(self.class)
              other.to_a == to_a
            else
              false
            end
          end

          def eql?(other) = self == other
          def hash = to_a.hash

        end

        UUID5_NAMESPACE = UUIDTools::UUID.parse('a55893fe-f4fd-4e84-a9f0-6a6d6495b53b').to_s

        ATTRIBUTES = [
          :id,
          :conjugation_set_id,
          :politeness_code,
          :charge_code,
          :form_code,
          :sort_rank,
          :value
        ].freeze

        def self.create_id(conjugation_set_id:, politeness_code:, charge_code:, form_code:, sort_rank:)
          name = [conjugation_set_id, politeness_code, charge_code, form_code, sort_rank].map(&:to_s).join('|')
          ns = UUIDTools::UUID.parse(UUID5_NAMESPACE)
          UUIDTools::UUID.sha1_create(ns, name).to_s
        end

        def initialize(id:, conjugation_set_id:, politeness_code:, charge_code:, form_code:, sort_rank:, value:)
          @id = id || raise(ArgumentError, 'ID required')
          @conjugation_set_id = conjugation_set_id
          @politeness_code = politeness_code
          @charge_code = charge_code
          @form_code = form_code
          @sort_rank = sort_rank
          @value = value
        end

        attr_reader :id
        attr_reader :conjugation_set_id
        attr_reader :politeness_code
        attr_reader :charge_code
        attr_reader :form_code
        attr_reader :sort_rank
        attr_reader :value

        def inflection = Inflection.new(charge_code:, politeness_code:, form_code:)

        def copy(**props)
          args = self.class.attributes.map { |p| [p, props.fetch(p, send(p))] }.to_h
          self.class.new(**args)
        end

      end

    end

  end
end
