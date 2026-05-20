package com.wohnjagd.app.domain.model

import java.time.Instant
import java.util.UUID

/**
 * Идентификатор сохранённого поиска.
 */
@JvmInline
value class SavedSearchId(val value: String) {
    companion object {
        fun new(): SavedSearchId = SavedSearchId(UUID.randomUUID().toString())
    }
}

/**
 * Сохранённый именованный поиск.
 * Включённый поиск опрашивается фоном; notificationEnabled определяет push.
 */
data class SavedSearch(
    val id: SavedSearchId,
    val name: String,
    val criteria: SearchCriteria,
    val enabled: Boolean = true,
    val notificationEnabled: Boolean = true,
    val createdAt: Instant,
    val updatedAt: Instant
)