package com.wohnjagd.app.domain.model

import java.time.Instant

/**
 * Запись лога событий — для timeline в UI и аналитики.
 */
data class EventLogEntry(
    val id: Long,
    val type: EventType,
    val entityId: String? = null,
    val description: String,
    val timestamp: Instant,
    val data: Map<String, String> = emptyMap()
)

enum class EventType {
    LISTING_FOUND,
    LISTING_UPDATED,
    LISTING_REMOVED,
    APPLICATION_CREATED,
    APPLICATION_STATUS_CHANGED,
    MESSAGE_SENT,
    MESSAGE_RECEIVED,
    SCAM_FLAGGED,
    SOURCE_ERROR,
    SOURCE_SUCCESS,
    USER_ACTION
}