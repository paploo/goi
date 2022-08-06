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
    raise RuntimeError, "Expected non-nil value for key #{key}" if value.nil?
    value
  end

end
