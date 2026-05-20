package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Одно сообщение переписки. Может быть привязано к Application или Contact.
 */
@Entity(
    tableName = "messages",
    indices = [
        Index(value = ["applicationId", "timestamp"]),
        Index(value = ["contactId"])
    ]
)
data class MessageEntity(
    @PrimaryKey val id: String,
    val applicationId: String?,
    val contactId: String?,
    val direction: String,
    val channel: String,
    val subject: String?,
    val body: String,
    val attachmentsJson: String,
    val timestamp: Long,
    val parsedActionsJson: String
)