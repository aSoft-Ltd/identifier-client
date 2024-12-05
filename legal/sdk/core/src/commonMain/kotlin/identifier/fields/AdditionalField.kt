@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")
package identifier.fields

import identifier.FieldInfo
import kotlinx.JsExport
import krono.LocalDateField
import symphony.BaseField
import symphony.BooleanField
import symphony.Field
import symphony.NumberField
import symphony.SingleChoiceField

data class AdditionalField(
    val field:Field<*, *>,
    private val info: FieldInfo
) {
    val asFreeText get() = when (this.info.category.isFreeText) {
        true -> field as? BaseField<String>
        false -> null
    }
    val asDate get() = field as? LocalDateField
    val asNumber get() = field as? NumberField
    val asChoice get() = field as? SingleChoiceField
    val asBoolean get() = field as? BooleanField
}