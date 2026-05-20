package com.wohnjagd.app.domain.model

/**
 * Тип арендодателя — влияет на тон Anschreiben и приоритет в скоринге.
 */
enum class LandlordType(val germanLabel: String) {
    PRIVATE("Privatperson"),
    HAUSVERWALTUNG("Hausverwaltung"),
    MAKLER("Makler"),
    GENOSSENSCHAFT("Genossenschaft"),
    BAUGESELLSCHAFT("Wohnungsbaugesellschaft"),
    UNKNOWN("Unbekannt")
}