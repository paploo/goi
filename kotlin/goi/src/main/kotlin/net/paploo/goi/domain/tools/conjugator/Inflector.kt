package net.paploo.goi.domain.tools.conjugator

import net.paploo.goi.domain.data.vocabulary.Conjugation


interface Inflector : (Conjugation.Inflection) -> Rewriter? {
    override fun invoke(inflection: Conjugation.Inflection): Rewriter?
}

/**
 * Internal method; this is useful when building inflectors from other inflectors, where there is
 * knowledge that a [Rewriter] exists but no good way to tell the compiler (without a lot of boilerplate).
 */
internal fun Inflector.getOrThrow(inflection: Conjugation.Inflection): Rewriter =
    invoke(inflection) ?: throw IllegalArgumentException("No Rewriter for inflection $inflection")

