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
  def sqlize(cast: nil)
    sql = "ARRAY[#{map(&:sqlize).join(', ')}]"
    puts cast
    cast.nil? ? sql : "#{sql}::#{cast}"
  end
end

module UUIDTools
  class UUID
    def sqlize = "'#{to_s}'"
  end
end
