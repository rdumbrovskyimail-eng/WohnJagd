package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Контакт арендодателя / Hausverwaltung / Makler.
 */
@Entity(
    tableName = "contacts",
    indices = [
        Index(value = ["email"]),
        Index(value = ["phone"]),
        Index(value = ["name"])
    ]
)
data class ContactEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val email: String?,
    val phone: String?,
    val organization: String?,
    val landlordType: String,
    val notes: String?
)