import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.kana.KanaTable

fun main(args: Array<String>) {
    println("Hello World!")

    println(KanaTable["き"])

//    Conjugation.Inflection.entries.forEach {
//        println(it)
//        val name = it.charge.toString() + it.politeness + it.form
//        println("val $name = Inflection(Charge.${it.charge}, Politeness.${it.politeness}, Form.${it.form})")
//    }
}