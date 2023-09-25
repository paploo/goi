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

            Conjugation.Inflection.entries.map { inflection ->
                conjugator.invoke(
                    conjugationKind = conjugationKind,
                    inflection = inflection,
                    dictionaryValue = dictionaryValue
                ).sequenceToResult().flatMap { computedConjugation ->
                    val suppliedConjugation = vocab.conjugations?.find { it.inflection == inflection }?.value

                    chooseConjugation(
                        vocabularyId = vocab.id,
                        inflection = inflection,
                        suppliedConjugation = suppliedConjugation,
                        computedConjugation = computedConjugation
                    ).map { value ->
                        value?.let { Conjugation(inflection, value) }
                    }
                }
            }.sequenceToResult().map { autoConjugations ->
                vocab.copy(conjugations = autoConjugations.filterNotNull())
            }
        } ?: Result.success(vocab) // Don't conjugate if no conjugation kind.

    private fun chooseConjugation(vocabularyId: Vocabulary.Id, inflection: Conjugation.Inflection, computedConjugation: String?, suppliedConjugation: String?): Result<String?> =
        if (suppliedConjugation == computedConjugation) {
            Result.success(suppliedConjugation)
        } else if (suppliedConjugation != null && computedConjugation == null) {
            Result.failure(UnexpectedConjugationException(vocabularyId, inflection, suppliedConjugation, null))
        } else if (suppliedConjugation == null && computedConjugation != null) {
            logger.info("Missing expected conjugation for $vocabularyId; auto-supplying $computedConjugation for $inflection")
            Result.success(computedConjugation)
        } else {  //if (computedConjugation != suppliedConjugation)
            Result.failure(UnexpectedConjugationException(vocabularyId, inflection, suppliedConjugation, computedConjugation))
        }

    data class UnexpectedConjugationException(
        val vocabularyId: Vocabulary.Id,
        val inflection: Conjugation.Inflection,
        val suppliedConjugation: String?,
        val computedConjugation: String?,
    ) : RuntimeException("Unepxected conjugation for $vocabularyId; got $suppliedConjugation but expected $computedConjugation for inflection $inflection")


}