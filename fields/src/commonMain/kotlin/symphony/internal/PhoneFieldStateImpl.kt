package symphony.internal

import geo.Country
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.PhoneFieldState
import symphony.Visibility
import kollections.List
import symphony.PhoneOutput
import symphony.SearchBy

data class PhoneFieldStateImpl(
    override val name: String,
    override val label: Label,
    override val visibility: Visibility,
    override val hint: String,
    override val required: Boolean,
    override val countries: List<Country>,
    override val option: Option?,
    override val key: String,
    override val searchBy: SearchBy,
    override val country: Country?,
    override val body: Long?,
    override val feedbacks: Feedbacks
) : PhoneFieldState {
    override val output get() = if (country != null && body != null) PhoneOutput(country, body) else null
}