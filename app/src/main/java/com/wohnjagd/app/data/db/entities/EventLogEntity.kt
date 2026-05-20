package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Запись лога событий для timeline и аналитики. dataJson — Map<String,String>.
 */
@Entity(
    tableName = "event_log",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["entityId"]),
        Index(value = ["type"])
    ]
)
data class EventLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val type: String,
    val entityId: String?,
    val description: String,
    val timestamp: Long,
    val dataJson: String
)