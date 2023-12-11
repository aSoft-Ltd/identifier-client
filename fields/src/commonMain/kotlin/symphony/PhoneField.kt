@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kollections.List
import kotlinx.JsExport
import kotlinx.JsExportIgnore
import kotlin.js.JsName

interface PhoneField : Field<PhoneOutput,PhoneFieldState>, PhoneFieldState, Searchable {

    fun options(withSelect: Boolean = false): List<Option>

    fun selectCountryOption(option: Option)

    fun selectCountryLabel(optionLabel: String)

    fun selectCountryValue(optionValue: String)

    fun unsetCountry()

    fun setCountry(country: Country?)

    fun setBody(value: String?)

    @JsExportIgnore
    fun setBody(long: Long?)

    @JsExportIgnore
    fun setBody(value: Int?)
}