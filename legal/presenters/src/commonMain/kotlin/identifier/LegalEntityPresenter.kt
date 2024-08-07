@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package identifier

import geo.GeoLocation
import kotlinx.JsExport

sealed class LegalEntityPresenter {
    abstract val src: LegalEntityDto
    abstract val uid: String
    abstract val gid: String
    abstract val name: String
    abstract val image: String?
    abstract val verified: Boolean
    abstract val creditAmount: Double?
    abstract val creditUsed: Double?
    abstract val leadTime: Int?

    val asIndividual get() = this as? IndividualPresenter

    val asCorporate get() = this as? CorporatePresenter

    val loc: GeoLocation?
        get() = when (this) {
            is CorporatePresenter -> headQuarters.location
            is IndividualPresenter -> location
        }
}