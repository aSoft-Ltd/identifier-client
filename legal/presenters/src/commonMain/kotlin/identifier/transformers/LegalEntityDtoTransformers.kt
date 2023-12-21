package identifier.transformers

import identifier.CorporateDto
import identifier.IndividualDto
import identifier.LegalEntityDto
import kollections.Collection
import kollections.map

fun LegalEntityDto.toPresenter() = when (this) {
    is CorporateDto -> toPresenter()
    is IndividualDto -> toPresenter()
}

fun Collection<LegalEntityDto>.toPresenters() = map { it.toPresenter() }