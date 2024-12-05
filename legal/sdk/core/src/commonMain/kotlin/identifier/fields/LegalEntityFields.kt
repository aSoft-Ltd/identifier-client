@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package identifier.fields

import identifier.LegalEntityPresenter
import kollections.List
import symphony.Fields
import kollections.listOf
import kotlinx.JsExport
import symphony.Field

sealed class LegalEntityFields<O : Any>(initial: O) : Fields<O>(initial) {
    abstract val entity: LegalEntityPresenter?
    val asIndividual get() = this as? IndividualFields
    val asCorporate get() = this as? CorporateFields

    abstract val additional: List<AdditionalField>

    companion object {
        val titles = listOf("Mr", "Mrs", "Ms", "Prof", "Sir", "Madam")
    }
}