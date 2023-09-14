package net.paploo.goi.domain.tools.conjugator.inflectors.verb

import net.paploo.goi.domain.tools.conjugator.Inflector
import net.paploo.goi.domain.tools.conjugator.Rewriter

/**
 * Type-safe interface for all (supported) verb inflections.
 */
interface VerbInflector : Inflector {
    val positivePlainPresent: Rewriter
    val positivePlainPast: Rewriter
    val positivePlainTe: Rewriter

    val negativePlainPresent: Rewriter
    val negativePlainPast: Rewriter
    val negativePlainTe: Rewriter

    val positivePolitePresent: Rewriter
    val positivePolitePast: Rewriter

    val negativePolitePresent: Rewriter
    val negativePolitePast: Rewriter

    val positivePlainPotential: Rewriter
    val negativePlainPotential: Rewriter
    val positivePolitePotential: Rewriter
    val negativePolitePotential: Rewriter
}
