package com.wohnjagd.app.domain.model

/**
 * Категория источника — определяет UI-группировку и default polling strategy.
 */
enum class SourceCategory(val germanLabel: String) {
    PORTAL("Portale"),
    REGIONAL("Regionale Aggregatoren"),
    LANDLORD("Vermietergesellschaften"),
    GENOSSENSCHAFT("Genossenschaften"),
    MUNICIPAL("Kommunen"),
    SOCIAL("Social Media"),
    NEWSLETTER("E-Mail Newsletter"),
    FEED("RSS Feeds"),
    OCR("Aushänge (OCR)"),
    AUCTION("Versteigerungen")
}