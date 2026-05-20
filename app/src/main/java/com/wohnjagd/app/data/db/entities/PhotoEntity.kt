package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Фотография listing'а с perceptual hash для дедупликации.
 * orderIndex используется вместо 'order' (SQL reserved keyword).
 */
@Entity(
    tableName = "photos",
    indices = [
        Index(value = ["listingId"]),
        Index(value = ["pHash"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ListingEntity::class,
            parentColumns = ["id"],
            childColumns = ["listingId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val listingId: String,
    val url: String,
    val pHash: Long?,
    val localPath: String?,
    val orderIndex: Int
)