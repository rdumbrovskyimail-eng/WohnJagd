package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Чёрный список контактов для scam-detector'а.
 * type: "EMAIL" / "PHONE" / "NAME" / "DOMAIN".
 */
@Entity(
    tableName = "blacklist",
    indices = [Index(value = ["contactValue"], unique = true)]
)
data class BlacklistEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val contactValue: String,
    val type: String,
    val reason: String,
    val sourceLabel: String?,
    val addedAt: Long
)