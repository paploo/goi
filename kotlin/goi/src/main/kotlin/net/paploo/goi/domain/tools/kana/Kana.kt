package net.paploo.goi.domain.tools.kana

object KanaTable {

    private val table: List<Kana> = listOf(
        KanaChar('あ', 'ア', "a", Coordinate(Row.A, Column.A), listOf("a")),
        KanaChar('い', 'イ', "i", Coordinate(Row.A, Column.I), listOf("i")),
        KanaChar('う', 'ウ', "u", Coordinate(Row.A, Column.U), listOf("u")),
        KanaChar('え', 'エ', "e", Coordinate(Row.A, Column.E), listOf("e")),
        KanaChar('お', 'オ', "o", Coordinate(Row.A, Column.O), listOf("o")),

        KanaChar('か', 'カ', "ka", Coordinate(Row.K, Column.A), listOf("ka")),
        KanaChar('き', 'キ', "ki", Coordinate(Row.K, Column.I), listOf("ki")),
        KanaChar('く', 'ク', "ku", Coordinate(Row.K, Column.U), listOf("ku")),
        KanaChar('け', 'ケ', "ke", Coordinate(Row.K, Column.E), listOf("ke")),
        KanaChar('こ', 'こ', "ko", Coordinate(Row.K, Column.O), listOf("ko")),

        KanaChar('が', 'ガ', "ga", Coordinate(Row.G, Column.A), listOf("ga")),
        KanaChar('ぎ', 'ギ', "gi", Coordinate(Row.G, Column.I), listOf("gi")),
        KanaChar('ぐ', 'グ', "gu", Coordinate(Row.G, Column.U), listOf("gu")),
        KanaChar('げ', 'ゲ', "ge", Coordinate(Row.G, Column.E), listOf("ge")),
        KanaChar('ご', 'ゴ', "go", Coordinate(Row.G, Column.O), listOf("go")),

        KanaChar('さ', 'サ', "sa", Coordinate(Row.S, Column.A), listOf("sa")),
        KanaChar('し', 'シ', "shi", Coordinate(Row.S, Column.I), listOf("shi", "si")),
        KanaChar('す', 'ス', "su", Coordinate(Row.S, Column.U), listOf("su")),
        KanaChar('せ', 'セ', "se", Coordinate(Row.S, Column.E), listOf("se")),
        KanaChar('そ', 'ソ', "so", Coordinate(Row.S, Column.O), listOf("so")),

        KanaChar('ざ', 'ザ', "za", Coordinate(Row.Z, Column.A), listOf("za")),
        KanaChar('じ', 'ジ', "ji", Coordinate(Row.Z, Column.I), listOf("ji", "zi")),
        KanaChar('ず', 'ズ', "zu", Coordinate(Row.Z, Column.U), listOf("zu")),
        KanaChar('ぜ', 'ゼ', "ze", Coordinate(Row.Z, Column.E), listOf("ze")),
        KanaChar('ぞ', 'ぞ', "zo", Coordinate(Row.Z, Column.O), listOf("zo")),

        KanaChar('た', 'タ', "ta", Coordinate(Row.T, Column.A), listOf("ta")),
        KanaChar('ち', 'チ', "chi", Coordinate(Row.T, Column.I), listOf("chi", "ti")),
        KanaChar('つ', 'ツ', "tsu", Coordinate(Row.T, Column.U), listOf("tsu", "tu")),
        KanaChar('て', 'テ', "te", Coordinate(Row.T, Column.E), listOf("te")),
        KanaChar('と', 'ト', "to", Coordinate(Row.T, Column.O), listOf("to")),

        KanaChar('だ', 'ダ', "da", Coordinate(Row.D, Column.A), listOf("da")),
        KanaChar('ぢ', 'ヂ', "ji", Coordinate(Row.D, Column.I), listOf("di")),
        KanaChar('づ', 'ヅ', "zu", Coordinate(Row.D, Column.U), listOf("du")),
        KanaChar('で', 'デ', "de", Coordinate(Row.D, Column.E), listOf("de")),
        KanaChar('ど', 'ド', "do", Coordinate(Row.D, Column.O), listOf("do")),

        KanaChar('な', 'ナ', "na", Coordinate(Row.N, Column.A), listOf("na")),
        KanaChar('に', 'ニ', "ni", Coordinate(Row.N, Column.I), listOf("ni")),
        KanaChar('ぬ', 'ヌ', "nu", Coordinate(Row.N, Column.U), listOf("nu")),
        KanaChar('ね', 'ネ', "ne", Coordinate(Row.N, Column.E), listOf("ne")),
        KanaChar('の', 'ノ', "no", Coordinate(Row.N, Column.O), listOf("no")),

        KanaChar('は', 'ハ', "ha", Coordinate(Row.H, Column.A), listOf("ha")),
        KanaChar('ひ', 'ヒ', "hi", Coordinate(Row.H, Column.I), listOf("hi")),
        KanaChar('ふ', 'フ', "fu", Coordinate(Row.H, Column.U), listOf("fu", "hu")),
        KanaChar('へ', 'ヘ', "he", Coordinate(Row.H, Column.E), listOf("he")),
        KanaChar('ほ', 'ホ', "ho", Coordinate(Row.H, Column.O), listOf("ho")),

        KanaChar('ば', 'バ', "ba", Coordinate(Row.B, Column.A), listOf("ba")),
        KanaChar('び', 'ビ', "bi", Coordinate(Row.B, Column.I), listOf("bi")),
        KanaChar('ぶ', 'ブ', "bu", Coordinate(Row.B, Column.U), listOf("bu")),
        KanaChar('べ', 'ベ', "be", Coordinate(Row.B, Column.E), listOf("be")),
        KanaChar('ぼ', 'ボ', "bo", Coordinate(Row.B, Column.O), listOf("bo")),

        KanaChar('ぱ', 'パ', "pa", Coordinate(Row.P, Column.A), listOf("pa")),
        KanaChar('ぴ', 'ピ', "pi", Coordinate(Row.P, Column.I), listOf("pi")),
        KanaChar('ぷ', 'プ', "pu", Coordinate(Row.P, Column.U), listOf("pu")),
        KanaChar('ぺ', 'ペ', "pe", Coordinate(Row.P, Column.E), listOf("pe")),
        KanaChar('ぽ', 'ポ', "po", Coordinate(Row.P, Column.O), listOf("po")),

        KanaChar('ま', 'マ', "ma", Coordinate(Row.M, Column.A), listOf("ma")),
        KanaChar('み', 'ミ', "mi", Coordinate(Row.M, Column.I), listOf("mi")),
        KanaChar('む', 'ム', "mu", Coordinate(Row.M, Column.U), listOf("mu")),
        KanaChar('め', 'メ', "me", Coordinate(Row.M, Column.E), listOf("me")),
        KanaChar('も', 'モ', "mo", Coordinate(Row.M, Column.O), listOf("mo")),

        KanaChar('や', 'ヤ', "ya", Coordinate(Row.Y, Column.A), listOf("ya")),
        KanaChar('ゆ', 'ユ', "yu", Coordinate(Row.Y, Column.U), listOf("yu")),
        KanaChar('よ', 'ヨ', "yo", Coordinate(Row.Y, Column.O), listOf("yo")),

        KanaChar('ら', 'ラ', "ra", Coordinate(Row.R, Column.A), listOf("ra")),
        KanaChar('り', 'リ', "ri", Coordinate(Row.R, Column.I), listOf("ri")),
        KanaChar('る', 'ル', "ru", Coordinate(Row.R, Column.U), listOf("ru")),
        KanaChar('れ', 'レ', "re", Coordinate(Row.R, Column.E), listOf("re")),
        KanaChar('ろ', 'ロ', "ro", Coordinate(Row.R, Column.O), listOf("ro")),

        KanaChar('わ', 'ワ', "wa", Coordinate(Row.W, Column.A), listOf("wa")),
        KanaChar('ゐ', 'ヰ', "wi", Coordinate(Row.W, Column.I), listOf("wyi"), isObsolete = true),
        KanaChar('ゑ', 'ヱ', "we", Coordinate(Row.W, Column.E), listOf("wyw"), isObsolete = true),
        KanaChar('を', 'ヲ', "wo", Coordinate(Row.W, Column.O), listOf("wo")),

        KanaChar('ん', 'ン', "n", Coordinate(Row.Consonant, Column.Consonant), listOf("nn", "xn")),


        KanaString("きゃ", "キャ", "kya", Coordinate(Row.K, Column.Ya), listOf("kya")),
        KanaString("きゅ", "キュ", "kyu", Coordinate(Row.K, Column.Yu), listOf("kyu")),
        KanaString("きょ", "キョ", "kyo", Coordinate(Row.K, Column.Yo), listOf("kyo")),

        KanaString("ぎゃ", "ギャ", "gya", Coordinate(Row.G, Column.Ya), listOf("gya")),
        KanaString("ぎゅ", "ギュ", "gyu", Coordinate(Row.G, Column.Yu), listOf("gyu")),
        KanaString("ぎょ", "ギョ", "gyo", Coordinate(Row.G, Column.Yo), listOf("gyo")),

        KanaString("しゃ", "シャ", "sha", Coordinate(Row.S, Column.Ya), listOf("sha", "sya")),
        KanaString("しゅ", "シュ", "shu", Coordinate(Row.S, Column.Yu), listOf("shu", "syu")),
        KanaString("しょ", "ショ", "sho", Coordinate(Row.S, Column.Yo), listOf("sho", "syo")),

        KanaString("じゃ", "ジャ", "ja", Coordinate(Row.Z, Column.Ya), listOf("ja", "zya")),
        KanaString("じゅ", "ジュ", "ju", Coordinate(Row.Z, Column.Yu), listOf("ju", "zyu")),
        KanaString("じょ", "ジョ", "jo", Coordinate(Row.Z, Column.Yo), listOf("jo", "zyo")),

        KanaString("ちゃ", "チャ", "cha", Coordinate(Row.T, Column.Ya), listOf("cha", "tya")),
        KanaString("ちゅ", "チュ", "chu", Coordinate(Row.T, Column.Yu), listOf("chu", "tyu")),
        KanaString("ちょ", "チョ", "cho", Coordinate(Row.T, Column.Yo), listOf("cho", "tyo")),

        KanaString("ぢゃ", "ヂャ", "ja", Coordinate(Row.D, Column.Ya), listOf("dya")),
        KanaString("ぢゅ", "ヂュ", "ju", Coordinate(Row.D, Column.Yu), listOf("dyu")),
        KanaString("ぢょ", "ヂョ", "jo", Coordinate(Row.D, Column.Yo), listOf("dyo")),

        KanaString("にゃ", "ニャ", "nya", Coordinate(Row.N, Column.Ya), listOf("nya")),
        KanaString("にゅ", "ニュ", "nyu", Coordinate(Row.N, Column.Yu), listOf("nyu")),
        KanaString("にょ", "ニョ", "nyo", Coordinate(Row.N, Column.Yo), listOf("nyo")),

        KanaString("ひゃ", "ヒャ", "hya", Coordinate(Row.H, Column.Ya), listOf("hya")),
        KanaString("ひゅ", "ヒュ", "hyu", Coordinate(Row.H, Column.Yu), listOf("hyu")),
        KanaString("ひょ", "ヒョ", "hyo", Coordinate(Row.H, Column.Yo), listOf("hyo")),

        KanaString("びゃ", "ビャ", "bya", Coordinate(Row.B, Column.Ya), listOf("bya")),
        KanaString("びゅ", "ビュ", "byu", Coordinate(Row.B, Column.Yu), listOf("byu")),
        KanaString("びょ", "ビョ", "byo", Coordinate(Row.B, Column.Yo), listOf("byo")),

        KanaString("ぴゃ", "ピャ", "pya", Coordinate(Row.P, Column.Ya), listOf("pya")),
        KanaString("ぴゅ", "ピュ", "pyu", Coordinate(Row.P, Column.Yu), listOf("pyu")),
        KanaString("ぴょ", "ピョ", "pyo", Coordinate(Row.P, Column.Yo), listOf("pyo")),

        KanaString("みゃ", "ミャ", "mya", Coordinate(Row.M, Column.Ya), listOf("mya")),
        KanaString("みゅ", "ミュ", "myu", Coordinate(Row.M, Column.Yu), listOf("myu")),
        KanaString("みょ", "ミョ", "myo", Coordinate(Row.M, Column.Yo), listOf("myo")),

        KanaString("りゃ", "リャ", "rya", Coordinate(Row.R, Column.Ya), listOf("rya")),
        KanaString("りゅ", "リュ", "ryu", Coordinate(Row.R, Column.Yu), listOf("ryu")),
        KanaString("りょ", "リョ", "ryo", Coordinate(Row.R, Column.Yo), listOf("ryo")),


        //TODO: How best to handle extended?
        // - They fit nicely in existing rows
        // - They either fill out missing columns, or we need small vowel version of columns.
        // - Some overlap: チェ and ツェ both are T row, SmallE column; so uniqueness isn't guaranteed!!!
        //   - Could maybe make additional rows or columns? (not really manageable)
        //   - Could extract the table interface and make two: one for only tranditional kana, and one for extended.
        //     - The main difference is if a coordinate can have multiple values or not.
        //     - These could share a lot of the same classes, but likely should have their own enums; then again, they
        //     - don't necessarily hurt ecahother and they allow for combining into one big table.

        // https://en.wikipedia.org/wiki/Katakana : General Ones
        // https://en.wikipedia.org/wiki/Katakana : Ministry of Education Additional Ones

    ).also { table ->
        table.groupBy { it.hiragana }.forEach { (value, kana) -> require(kana.size == 1) { "$value has multiple Kana defined: $kana" } }
        table.groupBy { it.katakana }.forEach { (value, kana) -> require(kana.size == 1) { "$value has multiple Kana defined: $kana" } }
        table.groupBy { it.coordinate }.forEach { (value, kana) -> require(kana.size == 1) { "$value has multiple Kana defined: $kana" } }
    }

    operator fun get(string: String): Kana? = table.find {
        it.hiragana == string || it.katakana == string //TODO: Make a cached lookup table for this?
    }

    operator fun get(coordinate: Coordinate): Kana? = table.find {
        it.coordinate == coordinate //TODO: Make a cached lookup table for this?
    }

    operator fun get(row: Row, col: Column): Kana? = get(Coordinate(row, col))

    enum class Column(
        val kind: Kind,
        val hiragana: Char,
        val katakana: Char
    ) {
        A(Kind.Gojuuon, 'あ', 'ア'),
        I(Kind.Gojuuon, 'い', 'イ'),
        U(Kind.Gojuuon, 'う', 'ウ'),
        E(Kind.Gojuuon, 'え', 'エ'),
        O(Kind.Gojuuon, 'お', 'オ'),
        Consonant(Kind.Consonant, 'ん', 'ン'),
        Ya(Kind.Youon, 'ゃ', 'ャ'),
        Yu(Kind.Youon, 'ゅ', 'ュ'),
        Yo(Kind.Youon, 'ょ', 'ョ');

//        SmallA(Kind.Gojuuon, 'ぁ', 'ァ'),
//        SmallI(Kind.Gojuuon, 'ぃ', 'ィ'),
//        SmallU(Kind.Gojuuon, 'ぅ', 'ゥ'),
//        SmallE(Kind.Gojuuon, 'ぇ', 'ェ'),
//        SmallO(Kind.Gojuuon, 'ぉ', 'ォ');

        enum class Kind {
            Gojuuon, Youon, Consonant;
        }

        companion object {
            fun byKind(kind: Kind): List<Column> = Column.entries.filter {
                it.kind == kind
            }
        }
    }

    enum class Row(
        val dakuten: Dakuten,
        val hiragana: Char,
        val katakana: Char,
        private val associationsBuilder: () -> Map<Dakuten, Row> = { emptyMap() }
    ) {
        A(Dakuten.Plain, 'あ', 'ア'),
        K(Dakuten.Plain, 'か', 'カ', { mapOf(Dakuten.Dakuten to Row.G) }),
        G(Dakuten.Dakuten, 'が', 'ガ', { mapOf(Dakuten.Plain to Row.K) }),
        S(Dakuten.Plain, 'さ', 'サ', { mapOf(Dakuten.Dakuten to Row.Z) }),
        Z(Dakuten.Dakuten, 'ざ', 'ザ', { mapOf(Dakuten.Plain to Row.S) }),
        T(Dakuten.Plain, 'た', 'タ', { mapOf(Dakuten.Dakuten to Row.D) }),
        D(Dakuten.Dakuten, 'だ', 'ダ', { mapOf(Dakuten.Plain to Row.T) }),
        N(Dakuten.Plain, 'な', 'ナ'),
        H(Dakuten.Plain, 'は', 'ハ', { mapOf(Dakuten.Dakuten to Row.B, Dakuten.Handakuten to Row.P) }),
        B(Dakuten.Dakuten, 'ば', 'バ', { mapOf(Dakuten.Plain to Row.H, Dakuten.Handakuten to Row.P) }),
        P(Dakuten.Handakuten, 'ぱ', 'パ', { mapOf(Dakuten.Plain to Row.H, Dakuten.Dakuten to Row.B) }),
        M(Dakuten.Plain, 'ま', 'マ'),
        Y(Dakuten.Plain, 'や', 'ヤ'),
        R(Dakuten.Plain, 'ら', 'ラ'),
        W(Dakuten.Plain, 'わ', 'ワ'),
        Consonant(Dakuten.Plain, 'ん', 'ン');

//        V(Dakuten.Plain, 'ゔ', 'ヴ');

        //Had to build in lambdas to evaluate lazily due to circular referencing.
        private val associations: Map<Dakuten, Row> by lazy { associationsBuilder() }

        fun associated(dakuten: Dakuten): Row? =
            if (dakuten == this.dakuten) this
            else associations[dakuten]

        enum class Dakuten {
            Plain, Dakuten, Handakuten
        }

        companion object {
            fun byDakuten(dakuten: Row.Dakuten): List<Row> = Row.entries.filter {
                it.dakuten == dakuten
            }
        }
    }

    data class Coordinate(
        val row: Row,
        val column: Column
    )

    interface Kana {
        val hiragana: String
        val katakana: String
        val romaji: String
        val coordinate: Coordinate
        val imeInputs: List<String>
        val isObsolete: Boolean
    }

    @ConsistentCopyVisibility
    data class KanaChar internal constructor(
        val hiraganaChar: Char,
        val katakanaChar: Char,
        override val romaji: String,
        override val coordinate: Coordinate,
        override val imeInputs: List<String>, //https://support.apple.com/zh-cn/guide/japanese-input-method/jpim10277/6.3/mac/13.0
        override val isObsolete: Boolean = false,
    ) : Kana {
        override val hiragana: String = hiraganaChar.toString()
        override val katakana: String = katakanaChar.toString()
    }

    @ConsistentCopyVisibility
    data class KanaString internal constructor(
        override val hiragana: String,
        override val katakana: String,
        override val romaji: String,
        override val coordinate: Coordinate,
        override val imeInputs: List<String>,
        override val isObsolete: Boolean = false,
    ) : Kana

}

/**
 * Shifts a kana to a different column, returning null if it doesn't exist.
 *
 * For example, shifts き to か, く, or きゅ, if choosing the A, U, or Yu column respectively.
 */
fun KanaTable.Kana.shiftTo(column: KanaTable.Column): KanaTable.Kana? =
    KanaTable[coordinate.row, column]

/**
 * Changes to kana to the associated row, if it exists.
 *
 * For example, can shift び to ひ or ぴ.
 */
fun KanaTable.Kana.changeDakuten(dakuten: KanaTable.Row.Dakuten): KanaTable.Kana? =
    coordinate.row.associated(dakuten)?.let { newRow ->
        KanaTable[newRow, coordinate.column]
    }