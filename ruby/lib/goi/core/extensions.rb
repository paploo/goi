class String

  def clean
    strip.then do |s|
      s.blank? ? nil : s
    end
  end

  def blank?
    nil? || empty?
  end

end

class Hash
  def fetch_required(key)
    value = self[key]
    raise ArgumentError, "Expected non-nil value for key #{key.inspect}" if value.nil?
    value
  end

end

class Array

  def find_duplicates
    counts = {}
    each do |elem|
      counts[elem] ||= 0
      counts[elem] += 1
    end
    counts.select { |_, count| count > 1 }.keys
  end

end
