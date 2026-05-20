package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * CRM-запись: один отклик на одну квартиру.
 * notesJson — список TimestampedNote сериализован в JSON.
 */
@Entity(
    tableName = "applications",
    indices = [
        Index(value = ["listingId"]),
        Index(value = ["status"]),
        Index(value = ["nextActionAt"])
    ]
)
data class ApplicationEntity(
    @PrimaryKey val id: String,
    val listingId: String,
    val status: String,
    val appliedAt: Long?,

    // Anschreiben snapshot
    val anschreibenStyle: String?,
    val anschreibenText: String?,
    val anschreibenSentChannel: String?,
    val anschreibenSentAt: Long?,

    val bewerbungsmappePath: String?,
    val notesJson: String,

    // Next action
    val nextActionType: String?,
    val nextActionAt: Long?,
    val nextActionDescription: String?,

    val createdAt: Long,
    val updatedAt: Long
)