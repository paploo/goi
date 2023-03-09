# frozen_string_literal: true

require_relative '../../../../lib/goi/nihongo/string_jp'
require 'rspec'

RSpec.describe Goi::Nihongo::StringJP do

  it('should parse a string as a simple value') do
    input = '京都に行きました'
    sjp = Goi::Nihongo::StringJP.from(input)
    expect(sjp.to_s).to eq input
    expect(sjp.preferred_spelling).to eq input
    expect(sjp.phonetic_spelling).to be nil
    expect(sjp.furigana).to be nil
  end

  it('should convert furigana to a meaningful reading') do
    text = '神社|じん・じゃ}に{行|い}きました'
    input = Goi::Nihongo::FuriganaString.parse(text)

    sjp = Goi::Nihongo::StringJP.from(input)

    expect(sjp.to_s).to eq '神社に行きました'
    expect(sjp.preferred_spelling).to eq '神社に行きました'
    expect(sjp.phonetic_spelling).to be nil
    expect(sjp.furigana).to be input
  end

  it('should pass through a string jp') do

  end

end

RSpec.describe Goi::Nihongo::FuriganaString do

  it('should parse an empty string as empty') do
    fs = Goi::Nihongo::FuriganaString.parse('')
    expect(fs.to_s).to eq ''
    expect(fs.to_html).to eq ''
    expect(fs.to_template).to eq ''
  end

  it('should parse a simple string as itself in a single token of self') do
    input = 'りんご'
    fs = Goi::Nihongo::FuriganaString.parse(input)
    expect(fs.to_s).to eq input
    expect(fs.to_html).to eq input
    expect(fs.to_template).to eq input
  end

  it('should parse a mixed example') do
    input = '{神社|じん・じゃ}に{行|い}きました'
    fs = Goi::Nihongo::FuriganaString.parse(input)
    expect(fs.to_s).to eq '神社に行きました'
    expect(fs.to_html).to eq '<ruby>神<rt>じん</rt></ruby><ruby>社<rt>じゃ</rt></ruby>に<ruby>行<rt>い</rt></ruby>きました'
    expect(fs.to_template).to eq '{神|じん}{社|じゃ}に{行|い}きました'
  end

  it('should parse itself as itself') do
    input = Goi::Nihongo::FuriganaString.parse('日本語')
    fs = Goi::Nihongo::FuriganaString.parse(input)
    expect(fs).to be input
  end

end
