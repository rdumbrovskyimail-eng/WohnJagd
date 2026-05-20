package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Кэш ответов Gemini для экономии токенов.
 * promptHash — sha256 от (prompt + model + version).
 */
@Entity(
    tableName = "ai_cache",
    indices = [Index(value = ["promptHash"], unique = true)]
)
data class AiCacheEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val promptHash: String,
    val model: String,
    val response: String,
    val tokenCount: Int?,
    val createdAt: Long,
    val expiresAt: Long?
)