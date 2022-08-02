module Goi
  module StringClassification

    def self.hiragana?(char)
      char.ord >= 0x3040 && char.ord <= 0x309f
    end

    def self.katakana?(char)
      char.ord >= 0x30a0 && char.ord <= 0x30ff
    end

    def self.kanji?(char)
      # Technically this is CJK unified ideographs
      char.ord >= 0x4e00 && char.ord <= 0x9faf
    end

    def self.jp_punct?(char)
      char.ord >= 0x3000 && char.ord <= 0x303f
    end

  end
end

class String

  def hiragana?
    chars.all? do |c|
      Goi::StringClassification.hirigana?(c) || Goi::StringClassification.jp_punct?(c)
    end
  end

  def katakana?
    chars.all? do |c|
      Goi::StringClassification.katakana?(c) || Goi::StringClassification.jp_punct?(c)
    end
  end

  def kanji?
    chars.all? do |c|
      Goi::StringClassification.kanji?(c) || Goi::StringClassification.jp_punct?(c)
    end
  end

end
