require 'uuidtools'

class String
  def sqlize = "'#{self}'"
end

class Integer
  def sqlize = to_s
end

class NilClass
  def sqlize = 'null'
end

class Array
  def sqlize = "{#{map(&:sqlize).join(', ')}}"
end

module UUIDTools
  class UUID
    def sqlize = "'#{to_s}'"
  end
end
