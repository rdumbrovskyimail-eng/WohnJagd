package com.wohnjagd.app.domain.model

/**
 * Стиль сгенерированного Anschreiben.
 * WARM — для частных арендодателей, человечный тон.
 * FORMAL — для Hausverwaltungen, формальный немецкий.
 * PROFESSIONAL — для крупных сетей (Vonovia/LEG), короткий и по делу.
 */
enum class AnschreibenStyle(val germanLabel: String) {
    WARM("Persönlich"),
    FORMAL("Förmlich"),
    PROFESSIONAL("Professionell")
}