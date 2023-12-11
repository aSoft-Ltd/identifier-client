@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kotlinx.JsExport

data class PhoneOutput(
    val country: Country,
    val body: Long
) {
    val bodyAsDouble get() = body.toDouble()
    override fun toString() = "${country.dialingCode.toInt()}${body}"

    companion object {
        operator fun invoke(value: String?): PhoneOutput? {
            val v = value ?: return null
            val phone = "+${v.replace("+", "")}"
            val country = Country.values().firstOrNull { phone.startsWith(it.dialingCode) } ?: return null
            return PhoneOutput(country, phone.replaceFirst(country.dialingCode, "").toLong())
        }
    }
}