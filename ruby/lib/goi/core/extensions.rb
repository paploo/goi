class String

  def clean
    strip.then do |s|
      s.blank? ? nil : s
    end
  end

  def blank?
    nil? || empty?
  end

  def contains_invisibles?
    # Somewhere I've been copy/pasting from stuck a few ZWSPs in my google doc;
    # so added a way to check for them.
    # TODO: Add other invisibles for completeness

    #zwsp="​" # I have captured one from my output to copy/paste!
    zwsp = "\u200b"
    include?(zwsp)
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
