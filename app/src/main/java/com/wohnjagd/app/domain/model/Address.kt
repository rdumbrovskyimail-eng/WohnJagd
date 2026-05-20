package com.wohnjagd.app.domain.model

/**
 * Адрес квартиры. raw — оригинальная строка из источника.
 * Остальные поля заполняются нормализатором/геокодером.
 */
data class Address(
    val raw: String,
    val street: String? = null,
    val houseNumber: String? = null,
    val postcode: String? = null,
    val city: String? = null,
    val district: String? = null,
    val communeId: CommuneId? = null,
    val lat: Double? = null,
    val lng: Double? = null
) {
    val hasCoordinates: Boolean get() = lat != null && lng != null
    val isInRheinSieg: Boolean get() = communeId != null
}