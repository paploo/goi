package net.paploo.goi.pipeline.vocabulary.transformer

import net.paploo.goi.common.extensions.flatMap
import net.paploo.goi.common.extensions.sequenceToResult
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import net.paploo.goi.domain.tools.conjugator.Conjugator
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
            conjugate(vocab).onFailure { th ->
                logger.warn("Could not conjugate vocabulary ${vocab.id} - ${vocab.preferredWritten.value}", th)
            }
        }.sequenceToResult()


    //TODO: This algorithm works only if we have a unique inflections, and it does not preserve ordering.
    // To solve this:
    //   1. Make a list of the derived inflections.
    //   2. Group the given conjugations by inflection.
    //   3. For each inflection group, compare the FIRST against the computed inflection to see if it is legal.
    //   4. In any case, always pass through all the given conjugations for the inflection.
    //   5. At the end, discover any unseen inflections (Set subtract), warn about each, and append them.
    private suspend fun conjugate(vocab: Vocabulary): Result<Vocabulary> =
        vocab.conjugationKind?.let { conjugationKind ->
            val dictionaryValue = vocab.preferredWritten.value

            // Make a list of the derived inflections.
            val computedMap: Result<Map<Conjugation.Inflection, String?>> =
                computedMap(conjugationKind, dictionaryValue)

            // Group the given conjugations by inflection
            val suppliedGroups: Result<Map<Conjugation.Inflection, List<Conjugation>>> = vocab.conjugations?.let {
                suppliedGroups(vocab.conjugations)
            } ?: Result.success(emptyMap())

            // For each inflection in the inflection group, compare the FIRST against the computed to see if it is legal.
            // In any case, always pass through ALL the given conjugations for the inflection.
            val updatedConjugations: Result<List<Conjugation>> =
                computedMap.flatMap { computed ->
                    suppliedGroups.flatMap { supplied ->
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

    private fun autoConjugations(
        vocabularyId: Vocabulary.Id,
        computedMap: Map<Conjugation.Inflection, String?>,
        suppliedInflections: Set<Conjugation.Inflection>,
    ): Result<List<Conjugation>> =
        computedMap.filterNot { (inflection, value) ->
            inflection in suppliedInflections
        }.flatMap { (inflection, conjugatedValue) ->
            conjugatedValue?.let {
                logger.warn("Auto-conjugating $vocabularyId: value = $conjugatedValue for $inflection")
                listOf(Conjugation(inflection = inflection, value = conjugatedValue))
            } ?: emptyList()
        }.let { Result.success(it) }

    private fun validatedSuppliedConjugations(
        vocabularyId: Vocabulary.Id,
        suppliedGroups: Map<Conjugation.Inflection, List<Conjugation>>,
        computedMap: Map<Conjugation.Inflection, String?>
    ): Result<List<Conjugation>> =
        suppliedGroups.map { (inflection, allSupplied) ->
            //Validate the primary supplied against the computed.
            allSupplied.firstOrNull()?.let { primarySupplied ->
                val computedConjugation = computedMap[inflection]
                if (primarySupplied.value != computedConjugation) {
                    //If primary is invalid, error!
                    Result.failure(
                        UnexpectedConjugationException(
                            vocabularyId = vocabularyId,
                            inflection = inflection,
                            suppliedConjugation = primarySupplied.value,
                            computedConjugation = computedConjugation
                        )
                    )
                } else {
                    //If primary is valid, pass through the conjugations.
                    Result.success(allSupplied)
                }
            } ?: Result.success(emptyList())
        }.sequenceToResult().map { it.flatten() }


    private fun computedMap(
        conjugationKind: Vocabulary.ConjugationKind,
        dictionaryValue: String
    ): Result<Map<Conjugation.Inflection, String?>> =
        Conjugation.Inflection.entries.map { inflection ->
            conjugator.invoke(
                conjugationKind = conjugationKind,
                inflection = inflection,
                dictionaryValue = dictionaryValue
            ).sequenceToResult().map {
                inflection to it
            }
        }.sequenceToResult().map {
            it.toMap()
        }

    private fun suppliedGroups(conjugations: Collection<Conjugation>): Result<Map<Conjugation.Inflection, List<Conjugation>>> =
        //Note: groupBy guarantees iteration order preservation!
        Result.runCatching {
            conjugations.groupBy { it.inflection }
        }

    data class UnexpectedConjugationException(
        val vocabularyId: Vocabulary.Id,
        val inflection: Conjugation.Inflection,
        val suppliedConjugation: String?,
        val computedConjugation: String?,
    ) : RuntimeException("Unepxected conjugation for $vocabularyId; got $suppliedConjugation but expected $computedConjugation for inflection $inflection")


}
