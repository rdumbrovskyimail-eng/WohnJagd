package com.wohnjagd.app.domain.model

import com.wohnjagd.app.core.result.AppError
import java.time.Instant

/**
 * Состояние источника — для отображения в SourcesScreen и принятия
 * решений о приостановке (после N последовательных ошибок).
 */
data class SourceHealth(
    val sourceId: SourceId,
    val category: SourceCategory,
    val displayName: String,
    val isEnabled: Boolean,
    val isHealthy: Boolean,
    val lastSuccessAt: Instant? = null,
    val lastErrorAt: Instant? = null,
    val lastError: AppError? = null,
    val consecutiveFailures: Int = 0,
    val listingsLast24h: Int = 0,
    val uniqueListingsLast24h: Int = 0,
    val avgResponseTimeMs: Long = 0L,
    val nextRunAt: Instant? = null
) {
    val hasBeenSuccessful: Boolean get() = lastSuccessAt != null
    val shouldAutoDisable: Boolean get() = consecutiveFailures >= 5
}