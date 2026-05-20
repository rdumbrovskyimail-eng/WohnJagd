package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сохранённый именованный поиск. SearchCriteria хранится как JSON.
 */
@Entity(tableName = "search_queries")
data class SearchQueryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val criteriaJson: String,
    val enabled: Boolean,
    val notificationEnabled: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)