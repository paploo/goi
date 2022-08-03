require 'uuidtools'

class String
  def sqlize = "'#{gsub("'", "''")}'"
end

class Integer
  def sqlize = to_s
end

class NilClass
  def sqlize = 'null'
end

class Array
  #TODO: Don't put a cast here! Put it in the script!
  def sqlize(cast = 'varchar[]') = "ARRAY[#{map(&:sqlize).join(', ')}]::#{cast}"
end

module UUIDTools
  class UUID
    def sqlize = "'#{to_s}'"
  end
end
