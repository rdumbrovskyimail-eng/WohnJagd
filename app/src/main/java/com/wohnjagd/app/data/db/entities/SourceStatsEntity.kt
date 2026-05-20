package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Статистика и health-status источника. sourceId одновременно PK и логический ID.
 */
@Entity(tableName = "source_stats")
data class SourceStatsEntity(
    @PrimaryKey val sourceId: String,
    val displayName: String,
    val category: String,
    val isEnabled: Boolean,
    val isHealthy: Boolean,
    val lastSuccessAt: Long?,
    val lastErrorAt: Long?,
    val lastErrorMessage: String?,
    val consecutiveFailures: Int,
    val listingsLast24h: Int,
    val uniqueListingsLast24h: Int,
    val avgResponseTimeMs: Long,
    val nextRunAt: Long?,
    val updatedAt: Long
)