package com.wohnjagd.app.domain.model

/**
 * Частота опроса источника.
 * intervalMinutes = 0 для MANUAL (только по запросу) и STREAM (push-источники).
 */
enum class PollingStrategy(val intervalMinutes: Int, val germanLabel: String) {
    HOT(3, "Sehr häufig (3 Min)"),
    WARM(15, "Häufig (15 Min)"),
    COLD(60, "Normal (1 Std)"),
    DAILY(1440, "Täglich"),
    MANUAL(0, "Manuell"),
    STREAM(0, "Streaming")
}