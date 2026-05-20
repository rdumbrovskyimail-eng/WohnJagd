package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Связь Listing ↔ источник. У одного master-listing может быть N источников
 * (одна квартира на 5 порталах одновременно).
 */
@Entity(
    tableName = "listing_sources",
    indices = [
        Index(value = ["listingId"]),
        Index(value = ["sourceId", "externalId"], unique = true)
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
data class ListingSourceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val listingId: String,
    val sourceId: String,
    val sourceUrl: String,
    val externalId: String,
    val firstSeenAt: Long,
    val lastSeenAt: Long
)