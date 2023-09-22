package net.paploo.goi.persistence.anki.vocabulary

import net.paploo.goi.common.extensions.constCase
import net.paploo.goi.common.extensions.kebabCase
import net.paploo.goi.common.extensions.snakeCase
import net.paploo.goi.common.interfaces.Valued
import net.paploo.goi.common.interfaces.valued
import net.paploo.goi.domain.data.vocabulary.Conjugation
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Charge
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Form
import net.paploo.goi.domain.data.vocabulary.Conjugation.Inflection.Politeness
import net.paploo.goi.domain.data.vocabulary.Vocabulary
import java.time.format.DateTimeFormatter

internal class VocabularyDomainToRecordTransform : (Vocabulary) -> Result<VocabularyCsvRecord> {

    override fun invoke(vocab: Vocabulary): Result<VocabularyCsvRecord> =
        Result.runCatching {
            buildRecord(vocab)
        }

    private fun buildRecord(vocab: Vocabulary): VocabularyCsvRecord =
        VocabularyCsvRecord(
            id = vocab.id.value.toString(),
            definition = vocab.preferredDefinition.value,
            preferredSpelling = vocab.preferredWritten.preferredSpelling.value,
            phoneticSpelling = phoneticSpelling(vocab),
            altSpelling = altSpelling(vocab),
            wordClass = vocab.wordClass.name.snakeCase().replace('_', ' '),
            conjugationKind = vocab.conjugationKind?.name?.snakeCase()?.replace('_', ' '),
            jlptLevel = vocab.jlptLevel?.levelNumber?.toString(),
            dateAdded = vocab.dateAdded.format(DateTimeFormatter.ISO_LOCAL_DATE),
            rowNum = vocab.rowNumber.toString(),
            lessons = buildLessons(vocab).joinToString(" "),
            conjugations = buildRecordConjugations(vocab),
            tags = buildTags(vocab).joinToString(" "),
        )

    private fun phoneticSpelling(vocab: Vocabulary): String? =
        listOfNotNull(
            vocab.preferredWritten.phoneticSpelling,
            vocab.altPhoneticSpelling
        ).joinToString("／") { it.value }

    private fun altSpelling(vocab: Vocabulary): String? =
        vocab.kanjiSpelling?.let { kanji ->
            if (kanji != vocab.preferredWritten.preferredSpelling) kanji.value else null
        }

    private fun buildLessons(vocab: Vocabulary): List<String> =
        vocab.references.map { it.value.constCase() }.sorted()
    private fun buildTags(vocab: Vocabulary): List<String> =
        listOf<List<Valued<String>>>(
            vocab.tags.sorted(),
            listOf(vocab.wordClass.name.valued()),
            listOfNotNull(vocab.conjugationKind?.name?.valued()),
            vocab.references.sorted()
        ).flatten().map { it.value.kebabCase() }

    private fun buildRecordConjugations(vocab: Vocabulary): VocabularyCsvRecord.Conjugations =
        VocabularyCsvRecord.Conjugations(
            positivePlainPresent = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present).buildFrom(vocab),
            positivePlainPast = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past).buildFrom(vocab),
            positivePlainTe = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te).buildFrom(vocab),
            positivePlainConditionalEba = null,
            positivePlainConditionalTara = null,
            positivePlainPotential = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential).buildFrom(vocab),
            positivePlainPassive = null,
            positivePlainCausative = null,
            positivePlainImperative = null,
            positivePolitePresent = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present).buildFrom(vocab),
            positivePolitePast = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past).buildFrom(vocab),
            positivePoliteTe = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Te).buildFrom(vocab),
            positivePoliteConditionalEba = null,
            positivePoliteConditionalTara = null,
            positivePolitePotential = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential).buildFrom(vocab),
            positivePolitePassive = null,
            positivePoliteCausative = null,
            positivePoliteImperative = null,
            negativePlainPresent = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present).buildFrom(vocab),
            negativePlainPast = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past).buildFrom(vocab),
            negativePlainTe = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te).buildFrom(vocab),
            negativePlainConditionalEba = null,
            negativePlainConditionalTara = null,
            negativePlainPotential = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential).buildFrom(vocab),
            negativePlainPassive = null,
            negativePlainCausative = null,
            negativePlainImperative = null,
            negativePolitePresent = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present).buildFrom(vocab),
            negativePolitePast = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past).buildFrom(vocab),
            negativePoliteTe = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Te).buildFrom(vocab),
            negativePoliteConditionalEba = null,
            negativePoliteConditionalTara = null,
            negativePolitePotential = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential).buildFrom(vocab),
            negativePolitePassive = null,
            negativePoliteCausative = null,
            negativePoliteImperative = null,
        )

    private fun Conjugation.Inflection.buildFrom(vocab: Vocabulary): String? =
        vocab.conjugations?.find {conjugation ->
            conjugation.inflection == this
        }?.value

}