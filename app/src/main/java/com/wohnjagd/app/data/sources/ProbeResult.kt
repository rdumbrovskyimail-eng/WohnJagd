package com.wohnjagd.app.data.sources

/**
 * Результат health-probe источника. Используется для отображения
 * статуса источника в SourcesScreen без полного fetch.
 */
data class ProbeResult(
    val isReachable: Boolean,
    val responseTimeMs: Long,
    val message: String? = null
)