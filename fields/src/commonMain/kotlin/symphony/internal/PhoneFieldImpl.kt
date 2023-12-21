package symphony.internal

import cinematic.mutableLiveOf
import geo.Country
import kollections.List
import kollections.emptyList
import kollections.listOf
import kollections.filter
import kollections.find
import kollections.map
import kollections.partition
import kollections.plus
import kollections.toList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Changer
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.PhoneField
import symphony.PhoneOutput
import symphony.SearchBy
import symphony.Visibility
import symphony.toErrors
import symphony.toWarnings

@PublishedApi
internal class PhoneFieldImpl(
    private val backer: FieldBacker<PhoneOutput?>,
    label: String,
    private val filter: (Country, key: String) -> Boolean,
    visibility: Visibility,
    hint: String,
    country: Country?,
    private val onChange: Changer<PhoneOutput>?,
    factory: ValidationFactory<PhoneOutput>?
) : AbstractHideable(), PhoneField {
    protected val validator = custom<PhoneOutput>(label).configure(factory)

    override fun validate() = validator.validate(output)

    override fun setCountry(country: Country?) = setValidateAndNotify(state.value.copy(country = country))

    override fun setBody(long: Long?) = setValidateAndNotify(state.value.copy(body = long))

    override fun setBody(value: String?) = setBody(value?.toLongOrNull())

    override fun setBody(value: Int?) = setBody(value?.toLong())

    private fun setValidateAndNotify(s: PhoneFieldStateImpl) {
        backer.asProp?.set(s.output)
        val res = validator.validate(s.output)
        state.value = s.copy(feedbacks = Feedbacks(res.toWarnings()))
        onChange?.invoke(s.output)
    }

    override fun validateToErrors(): Validity<PhoneOutput> {
        val res = validator.validate(output)
        state.value = state.value.copy(feedbacks = Feedbacks(res.toErrors()))
        return res
    }

    override fun finish() {
        state.stopAll()
    }

    override fun reset() = setValidateAndNotify(initial)

    override fun clear() = setValidateAndNotify(initial.copy(country = null, body = null))

    private val mapper = { c: Country -> c.toOption(c == this.state.value.country) }

    private fun Country.toOption(selected: Boolean) = Option(label, code, selected)

    private val initial = PhoneFieldStateImpl(
        name = backer.name,
        label = Label(label, this.validator.required),
        visibility = visibility,
        hint = hint,
        key = "",
        searchBy = SearchBy.Filtering,
        required = this.validator.required,
        country = backer.asProp?.get()?.country ?: country,
        option = (backer.asProp?.get()?.country ?: country)?.toOption(true),
        body = backer.asProp?.get()?.body,
        countries = Country.entries.toList(),
        feedbacks = Feedbacks(emptyList()),
    )


    override val state by lazy { mutableLiveOf(initial) }

    override val option: Option? get() = this.country?.let(mapper)

    override val countries: List<Country> by lazy { Country.entries.toList() }

    override fun setSearchBy(sb: SearchBy) {
        val s = state.value.searchBy
        if (s == sb) return
        state.value = state.value.copy(searchBy = sb)
    }

    override fun setSearchByFiltering() = setSearchBy(SearchBy.Filtering)

    override fun setSearchByOrdering() = setSearchBy(SearchBy.Ordering)

    override fun search() {
        val key = state.value.key
        val found = if (key.isEmpty()) countries else when (state.value.searchBy) {
            SearchBy.Filtering -> countries.filter { filter(it, key) }
            SearchBy.Ordering -> {
                val partitions = countries.partition { filter(it, key) }
                (partitions.first + partitions.second)
            }
        }
        state.value = state.value.copy(countries = found)
    }

    override fun appendSearchKey(key: String?) = setSearchKey(state.value.key + (key ?: ""))

    override fun clearSearchKey() = setSearchKey("")

    override fun backspaceSearchKey(): String {
        val key = state.value.key
        if (key.isEmpty()) return ""
        return setSearchKey(key.dropLast(1))
    }

    override fun setSearchKey(key: String?): String {
        val k = key ?: ""
        state.value = state.value.copy(key = k)
        return k
    }

    override fun setVisibility(v: Visibility) {
        state.value = state.value.copy(visibility = v)
    }

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select ${state.value.label.capitalizedWithoutAstrix()}", ""))
    } else {
        emptyList()
    } + countries.toList().map { mapper(it) })

    override fun selectCountryOption(option: Option) {
        val country = countries.find { mapper(it).value == option.value }
        if (country != null) setCountry(country)
    }

    override fun selectCountryValue(optionValue: String) {
        val item = countries.find { mapper(it).value == optionValue }
        if (item != null) setCountry(item)
    }

    override fun selectCountryLabel(optionLabel: String) {
        val item = countries.find { mapper(it).label == optionLabel }
        if (item != null) setCountry(item)
    }

    override fun unsetCountry() = setValidateAndNotify(state.value.copy(country = null))

    override val output get() = state.value.output
    override val label get() = state.value.label
    override val required get() = state.value.required
    override val hint get() = state.value.hint
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
    override val body get() = state.value.body
    override val country get() = state.value.country
    override val name get() = backer.name

    override val key get() = state.value.key

    override val searchBy get() = state.value.searchBy
}