package com.wohnjagd.app.domain.model

/**
 * Тип отопления — критично для оценки Heizkosten.
 */
enum class HeatingType(val germanLabel: String) {
    GAS("Gas"),
    OIL("Öl"),
    DISTRICT_HEATING("Fernwärme"),
    PELLETS("Pellets"),
    HEAT_PUMP("Wärmepumpe"),
    ELECTRIC("Elektro"),
    SOLAR("Solar"),
    OTHER("Sonstige"),
    UNKNOWN("Unbekannt")
}

/**
 * Меблировка.
 */
enum class Furnishing(val germanLabel: String) {
    UNFURNISHED("Unmöbliert"),
    PARTIALLY_FURNISHED("Teilmöbliert"),
    FULLY_FURNISHED("Möbliert")
}

/**
 * Политика по домашним животным.
 */
enum class PetsPolicy(val germanLabel: String) {
    NOT_ALLOWED("Nicht erlaubt"),
    AFTER_AGREEMENT("Nach Absprache"),
    SMALL_ONLY("Nur Kleintiere"),
    ALLOWED("Erlaubt"),
    UNKNOWN("Unbekannt")
}

/**
 * Тип парковки.
 */
enum class ParkingType(val germanLabel: String) {
    NONE("Keine"),
    STREET("Straße"),
    GARAGE("Garage"),
    UNDERGROUND("Tiefgarage"),
    CARPORT("Carport")
}