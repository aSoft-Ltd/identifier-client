package identifier.transformers

import identifier.AdditionalInfo
import identifier.fields.AdditionalInfoOutput
import identifier.fields.CorporateOutput
import identifier.params.CorporateParams
import kase.catching
import kollections.key
import kollections.mapValues
import kollections.toKList
import kollections.toKMap
import kollections.toList
import kollections.toMutableMap
import kollections.value
import kollections.values

fun CorporateOutput.toParams() = catching {
    CorporateParams(
        name = name ?: throw IllegalArgumentException("Name must not be null"),
        contactName = contactName,
        contactEmail = contactEmail,
        contactPhone = contactPhone?.toString(),
        contactRole = contactRole,
        industry = industry,
        registrationNo = registrationNo,
        registrationDate = registrationDate,
        tin = tin,
        vat = vat,
        website = website,
        hqLocation = headquarters,
        address = null, // Lets find a reversible way
        businessType = businessType,
        additionalInfo = additionalInfo.toInfo()
    )
}

fun AdditionalInfoOutput.toInfo() = AdditionalInfo(
    date = date.toKMap(),
    time = time.toKMap(),
    string = string.toKMap(),
    int = int.toKMap(),
    double = double.toKMap()
)

