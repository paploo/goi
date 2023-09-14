package net.paploo.goi.domain.tools.conjugator.inflectors.adjective

import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.tools.conjugator.Inflector
import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Type-safe interface for all (supported) adjective inflections.
 */
interface AdjectiveInflector : Inflector {
    val positivePlainPresent: Rewriter
    val positivePlainPast: Rewriter
    val positivePlainTe: Rewriter

    val negativePlainPresent: Rewriter
    val negativePlainPast: Rewriter
    val negativePlainTe: Rewriter
}
