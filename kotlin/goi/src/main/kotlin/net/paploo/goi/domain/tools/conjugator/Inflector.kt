package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation


interface Inflector : (Conjugation.Inflection) -> Rewriter? {
    override fun invoke(inflection: Conjugation.Inflection): Rewriter?
}
