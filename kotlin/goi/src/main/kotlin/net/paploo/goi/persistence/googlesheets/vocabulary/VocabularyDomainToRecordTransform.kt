package net.paploo.goi.persistence.googlesheets.vocabulary

import net.paploo.goi.common.extensions.camelCase
import net.paploo.goi.common.extensions.constCase
import net.paploo.goi.common.extensions.snakeCase
import net.paploo.goi.common.interfaces.Valued
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
            definition = vocab.preferredDefinition.value,
            preferredSpelling = vocab.preferredWritten.preferredSpelling.value,
            phoneticSpelling = vocab.preferredWritten.phoneticSpelling?.value,
            altPhonSpell = vocab.altPhoneticSpelling?.value,
            kanjiSpelling = vocab.kanjiSpelling?.value,
            wordClassCode = vocab.wordClass.name.constCase(),
            conjugationKindCode = vocab.conjugationKind?.name?.constCase(),
            jlptLevel = vocab.jlptLevel?.levelNumber?.toString(),
            lessonCodes = buildList(vocab.references) { it.constCase() },
            tags = buildList(vocab.tags) { it.snakeCase() },
            id = vocab.id.value.toString(),
            rowNum = vocab.rowNumber.toString(),
            dateAdded = vocab.dateAdded.format(DateTimeFormatter.ISO_LOCAL_DATE),
            conjugations = buildRecordConjugations(vocab),
        )

    private fun buildList(values: Collection<Valued<String>>, transform: (String) -> String): String? =
        if (values.isEmpty()) null
        else values.map { it.value }.sorted().joinToString(
            separator = ",",
            prefix = "{",
            postfix = "}",
            transform = transform
        )

    private fun buildRecordConjugations(vocab: Vocabulary): VocabularyCsvRecord.Conjugations =
        VocabularyCsvRecord.Conjugations(
            dictionaryForm = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Present).buildFrom(vocab),
            pastForm = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Past).buildFrom(vocab),
            teForm = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Te).buildFrom(vocab),
            negativeForm = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Present).buildFrom(vocab),
            negativePastForm = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Past).buildFrom(vocab),
            negativeTeForm = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Te).buildFrom(vocab),
            politeForm = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Present).buildFrom(vocab),
            politePastForm = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Past).buildFrom(vocab),
            politeTeForm = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Te).buildFrom(vocab),
            politeNegativeForm = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Present).buildFrom(vocab),
            politeNegativePastForm = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Past).buildFrom(vocab),
            politeNegativeTeForm = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Te).buildFrom(vocab),
            positivePlainPotential = Conjugation.Inflection(Charge.Positive, Politeness.Plain, Form.Potential).buildFrom(vocab),
            negativePlainPotential = Conjugation.Inflection(Charge.Negative, Politeness.Plain, Form.Potential).buildFrom(vocab),
            positivePolitePotential = Conjugation.Inflection(Charge.Positive, Politeness.Polite, Form.Potential).buildFrom(vocab),
            negativePolitePotential = Conjugation.Inflection(Charge.Negative, Politeness.Polite, Form.Potential).buildFrom(vocab),
        )

    private fun Conjugation.Inflection.buildFrom(vocab: Vocabulary): String? =
        vocab.conjugations?.find {conjugation ->
            conjugation.inflection == this
        }?.value

}