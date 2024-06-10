@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package identifier

import geo.GeoLocation
import kollections.List
import kollections.filterIsInstance
import krono.LocalDate
import kotlinx.JsExport

data class IndividualPresenter(
    override val src: IndividualDto,
    override val uid: String,
    override val name: String,
    override val image: String?,
    override val verified: Boolean = false,
    val title: String?,
    val dob: LocalDate?,
    val gender: Gender?,
    val comms: List<Comm>,
    override val gid: String,
    val idDocumentNumber: String?,
    val idDocumentType: DocumentType?,
    val location: GeoLocation?,
    val address: String?,
    override val creditAmount: Double? = null,
    override val creditUsed: Double? = null,
    override val leadTime: Int? = null,
) : LegalEntityPresenter() {
    val emails get() = comms.filterIsInstance<UserEmail>()
    val phones get() = comms.filterIsInstance<UserPhone>()
}