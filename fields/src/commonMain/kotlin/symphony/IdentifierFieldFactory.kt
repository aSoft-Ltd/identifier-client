package symphony

import geo.Country
import geo.matches
import neat.ValidationFactory
import symphony.internal.PhoneFieldImpl
import symphony.validators.email
import kotlin.reflect.KMutableProperty0
import symphony.internal.FieldBacker

fun Fields<*>.email(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = text(name, label, visibility, hint, onChange) {
    email()
    configure(factory)
}

fun Fields<*>.phone(
    name: KMutableProperty0<PhoneOutput?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    country: Country? = name.get()?.country,
    filter: (Country, key: String) -> Boolean = { c, key -> c.matches(key)},
    onChange: Changer<PhoneOutput>? = null,
    factory: ValidationFactory<PhoneOutput>? = null
): PhoneField = getOrCreate(name) {
    PhoneFieldImpl(FieldBacker.Prop(name), label, filter, visibility, hint, country, onChange, factory)
}