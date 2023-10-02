package net.paploo.goi.pipeline.vocabulary.transformer

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.conjugator.Conjugator
import net.paploo.goi.domain.tools.conjugator.conjugate
import net.paploo.goi.domain.tools.conjugator.invoke
import net.paploo.goi.pipeline.core.Context
import net.paploo.goi.pipeline.core.Transformer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AutoConjugationTransformer : Transformer<List<Vocabulary>> {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private val conjugator: Conjugator = Conjugator.default

    override suspend fun invoke(vocabularyList: List<Vocabulary>, context: Context): Result<List<Vocabulary>> =
        vocabularyList.map { vocab ->
            autoConjugate(vocab).onFailure { th ->
                logger.warn("Could not conjugate vocabulary ${vocab.id} - ${vocab.preferredWritten.value}", th)
            }
        }.sequenceToResult()


    /**
     * Auto-conjugates the verb.
     *
     * Right now this:
     * 1. Makes a list of the computed inflections.
     * 2. Groups the vocab's supplied conjugations by inflection.
     * 3. For each group, validate the any entry meets the rules, and then pass through the conjugations.
     * 4. Add any missing conjugations.
     *
     * The use cases supported are:
     * - If we have a unique list of inflections, make sure they are all valid.
     * - Add any missing ones we can compute.
     * - If we have multiples for a given inflection, we can only validate that at least one is the desired one.
     *
     * Note that it is tempting to change this to just replace all the conjugations with the computed ones, but
     * this breaks down if we have alternate inflections.
     */
    private suspend fun autoConjugate(vocab: Vocabulary): Result<Vocabulary> =
        vocab.conjugationKind?.let { conjugationKind ->
            val dictionaryValue = vocab.preferredWritten.value

            // Make a list of the derived inflections.
            val computedMap: Result<Map<Conjugation.Inflection, Conjugation?>> =
                computedMap(conjugationKind, dictionaryValue)

            // Group the given conjugations by inflection
            val suppliedGroups: Result<Map<Conjugation.Inflection, List<Conjugation>>> = vocab.conjugations?.let {
                suppliedGroups(vocab.conjugations)
            } ?: Result.success(emptyMap())

            val updatedConjugations: Result<List<Conjugation>> =
                computedMap.flatMap { computed ->
                    suppliedGroups.flatMap { supplied ->
                        // For each inflection in the inflection group, compare the FIRST against the computed to see if it is legal.
                        // In any case, always pass through ALL the given conjugations for the inflection.
                        validatedSuppliedConjugations(vocab.id, supplied, computed).flatMap { validatedSupplied ->
                            // At the end, discover any unseen inflections (Set subtract), warn about each, and append them.
                            autoConjugations(vocab.id, computed, supplied.keys).map { auto ->
                                validatedSupplied + auto
                            }
                        }
                    }
                }

            updatedConjugations.map {
                vocab.copy(conjugations = it)
            }
        } ?: Result.success(vocab)

    /**
     * Makes a list of the derived inflections.
     *
     * For each possible Inflection, calculate the returned inflection value, and place it in a map.
     */
    private fun computedMap(
        conjugationKind: Vocabulary.ConjugationKind,
        dictionaryValue: String
    ): Result<Map<Conjugation.Inflection, Conjugation?>> =
        Conjugation.Inflection.entries.map { inflection ->
            conjugator.conjugate(
                conjugationKind = conjugationKind,
                inflection = inflection,
                dictionaryValue = dictionaryValue
            ).sequenceToResult().map {
                inflection to it
            }
        }.sequenceToResult().map {
            it.toMap() //Assumes (rightly) that the conjugator only makes one per inflection.
        }

    /**
     * Groups the given conjugations by inflection.
     */
    private fun suppliedGroups(conjugations: Collection<Conjugation>): Result<Map<Conjugation.Inflection, List<Conjugation>>> =
        //Note: groupBy guarantees iteration order preservation!
        Result.runCatching {
            conjugations.groupBy { it.inflection }
        }

    /**
     * For each inflection group, compare the FIRST against the computed inflection to see if it is legal
     *
     * if there isn't at least one entry for an inflection that matches the computed computed, return a failure.
     * If they are all matching, be a success of all the conjugations (effectively unaltering them).
     *
     * Note that this code is set-up so that we don't remove any missing values. This way, if we have
     * multiple alternatives, we have those in lower positions in the array.
     */
    private fun validatedSuppliedConjugations(
        vocabularyId: Vocabulary.Id,
        suppliedGroups: Map<Conjugation.Inflection, List<Conjugation>>,
        computedMap: Map<Conjugation.Inflection, Conjugation?>
    ): Result<List<Conjugation>> =
        suppliedGroups.map { (inflection, allSupplied) ->
            val computedConjugation = computedMap[inflection]
            if (computedConjugation !in allSupplied) {
                //If it isn't in there, then error!
                //NOTE: If we wanted these as warnings, just be sure to pass through `allSupplied`.
                Result.failure(
                    UnexpectedConjugationException(
                        vocabularyId = vocabularyId,
                        inflection = inflection,
                        suppliedConjugation = allSupplied.firstOrNull()?.value,
                        computedConjugation = computedConjugation?.value
                    )
                )
            } else {
                //If is valid, pass through the conjugations.
                Result.success(allSupplied)
            }
        }.sequenceToResult().map { it.flatten() }

    /**
     * Discovers any missing inflection, warns about each, and returns them.
     *
     * This won't replace anything we already have, just
     */
    private fun autoConjugations(
        vocabularyId: Vocabulary.Id,
        computedMap: Map<Conjugation.Inflection, Conjugation?>,
        suppliedInflections: Set<Conjugation.Inflection>,
    ): Result<List<Conjugation>> =
        computedMap.filterNot { (inflection, value) ->
            inflection in suppliedInflections
        }.flatMap { (inflection, conjugation) ->
            conjugation?.let {
                logger.debug("Auto-conjugating {}: value = {} for {}", vocabularyId, conjugation.value, inflection)
                listOf(conjugation)
            } ?: emptyList()
        }.let { Result.success(it) }

    data class UnexpectedConjugationException(
        val vocabularyId: Vocabulary.Id,
        val inflection: Conjugation.Inflection,
        val suppliedConjugation: String?,
        val computedConjugation: String?,
    ) : RuntimeException("Unepxected conjugation for $vocabularyId; got $suppliedConjugation but expected $computedConjugation for inflection $inflection")


}
