package identifier.fields

import identifier.FieldInfo
import kollections.MutableMap
import kollections.put
import kollections.remove
import krono.LocalDate
import krono.LocalTime

data class AdditionalInfoOutput(
    val date: MutableMap<String, LocalDate?> = kollections.mutableMapOf(),
    val time: MutableMap<String, LocalDate?> = kollections.mutableMapOf(),
    val string: MutableMap<String, String?> = kollections.mutableMapOf(),
    val int: MutableMap<String, Int?> = kollections.mutableMapOf(),
    val double: MutableMap<String, Double?> = kollections.mutableMapOf()
) {
    fun set(field:String, value:Any?) {
        date.remove(field)
        time.remove(field)
        string.remove(field)
        int.remove(field)
        double.remove(field)
        when (value) {
            is LocalDate -> date.put(field, value)
            is LocalTime -> TODO("Custom time inputs are not supported yet")
            is String -> string.put(field, value)
            is Int -> int.put(field, value)
            is Number -> double.put(field, value.toDouble())
            is FieldInfo.Option -> string.put(field, value.value)
        }
    }
}