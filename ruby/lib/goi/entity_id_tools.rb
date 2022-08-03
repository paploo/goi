require 'uuidtools'

module Goi
  module EntityIDTools

    VOCABULARY_NAMESPACE = UUIDTools::UUID.parse('b0d2f0ca-000d-4332-8547-5f31f8595c92')
    DEFINITION_NAMESPACE = UUIDTools::UUID.parse('c7812647-678a-4bf5-bed3-b33fe499469c')
    SPELLING_NAMESPACE = UUIDTools::UUID.parse('546a4b2c-6b83-4fe9-902e-7c7ade990930')

    # Matches Postgres' uuid_generate_v5(ns, string)
    def self.uuid5(namespace, value)
      UUIDTools::UUID.sha1_create(namespace, value)
    end

    def self.vocabulary_uuid(vocab_word)
      uuid5(VOCABULARY_NAMESPACE, vocab_word)
    end

    def self.definition_uuid(definition_value, namespace)
      uuid5(DEFINITION_NAMESPACE, [namespace, definition_value].join('|'))
    end

    def self.spelling_uuid(spelling_value, namespace)
      uuid5(SPELLING_NAMESPACE, [namespace, spelling_value].join('|'))
    end

  end
end

