@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package identifier

import kotlinx.JsExport
import kollections.List
import kollections.filterIsInstance

data class ContactPresenter(
    val src: ContactDto,
    val uid: String,
    /**
     * Can be either
     * - Desk
     * - Support
     * - John
     */
    val name: String,
    val comms: List<Comm>,
    val role: String?,
    val isPrimary: Boolean,
) {
    val emails get() = comms.filterIsInstance<UserEmail>()
    val phones get() = comms.filterIsInstance<UserPhone>()
}