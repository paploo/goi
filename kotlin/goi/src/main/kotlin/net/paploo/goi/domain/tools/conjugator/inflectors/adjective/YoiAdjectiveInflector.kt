package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Inflector for Yoi class adjectives, which end in いい but usually inflect like 良い/よい.
 *
 * Ironically, 良い/よい itself is not a Yoi class, it conjugates as a simple い-adjective
 */
class YoiAdjectiveInflector : StandardAdjectiveInflector() {

    private val validEndingsRegex = "(いい)$".toRegex()

    override val positivePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "いい")
    override val positivePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "よかった")
    override val positivePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "よくて")

    override val negativePlainPresent: Rewriter = Rewriter.replace(validEndingsRegex, "よくない")
    override val negativePlainPast: Rewriter = Rewriter.replace(validEndingsRegex, "よくなかった")
    override val negativePlainTe: Rewriter = Rewriter.replace(validEndingsRegex, "よくなくて")

    companion object {
        val default: YoiAdjectiveInflector = YoiAdjectiveInflector()
    }

}
