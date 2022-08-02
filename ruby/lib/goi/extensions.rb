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

class NilClass

  def clean
    nil
  end

end
