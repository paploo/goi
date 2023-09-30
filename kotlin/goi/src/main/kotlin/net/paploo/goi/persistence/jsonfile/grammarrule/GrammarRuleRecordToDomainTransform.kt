package net.paploo.goi.persistence.jsonfile.grammarrule

import net.paploo.goi.domain.data.grammar.GrammarRule

internal class GrammarRuleRecordToDomainTransform : (GrammarRuleDto) -> Result<GrammarRule> {

    override fun invoke(record: GrammarRuleDto): Result<GrammarRule> {
        TODO("Not yet implemented")
    }
}