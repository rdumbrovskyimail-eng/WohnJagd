package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Денормализованное хранение Listing (master). Все скалярные поля domain-модели
 * Listing разложены в колонки. Сложные структуры (ScoreBreakdown.reasons,
 * ScamRisk.reasons) — JSON-строки, конвертация в Mappers (Phase 3).
 *
 * Связанные данные: PhotoEntity (фото), ListingSourceEntity (источники).
 */
@Entity(
    tableName = "listings",
    indices = [
        Index(value = ["fingerprint"], unique = true),
        Index(value = ["communeId"]),
        Index(value = ["scoreTotal"]),
        Index(value = ["status"]),
        Index(value = ["userStatus"]),
        Index(value = ["firstSeenAt"])
    ]
)
data class ListingEntity(
    @PrimaryKey val id: String,
    val fingerprint: String,
    val title: String,
    val description: String,

    // Price (cents as Long)
    val kaltmieteCents: Long,
    val nebenkostenCents: Long?,
    val heizkostenCents: Long?,
    val heizkostenEstimatedCents: Long?,
    val kautionCents: Long?,
    val provisionCents: Long?,

    // Geometry
    val sizeSqm: Float,
    val rooms: Float,
    val floor: Int?,
    val totalFloors: Int?,
    val hasBalcony: Boolean,
    val hasGarden: Boolean,
    val hasElevator: Boolean,
    val hasBasement: Boolean,
    val parkingType: String?,

    // Features (enums as enum.name())
    val heatingType: String,
    val energyClass: String?,
    val energyValue: Float?,
    val furnishing: String,
    val petsPolicy: String,
    val smokingAllowed: Boolean?,
    val needsWbs: Boolean,
    val barrierFree: Boolean,
    val availableFrom: String?, // LocalDate ISO format
    val minRentMonths: Int?,

    // Address (flattened)
    val addressRaw: String,
    val street: String?,
    val houseNumber: String?,
    val postcode: String?,
    val city: String?,
    val district: String?,
    val communeId: String?,
    val lat: Double?,
    val lng: Double?,

    // Media metadata (photos в отдельной таблице)
    val floorPlanUrl: String?,
    val videoUrl: String?,

    // Contact ref
    val contactId: String?,
    val landlordType: String,

    // Pipeline state
    val geocoded: Boolean,
    val enriched: Boolean,
    val scoredAt: Long?,
    val scamCheckedAt: Long?,

    // Score (total — колонка для сортировки, остальное — JSON)
    val scoreTotal: Float?,
    val scoreBreakdownJson: String?,

    // Scam (score+level — колонки для фильтрации, причины — JSON)
    val scamRiskScore: Float?,
    val scamLevel: String?,
    val scamRiskJson: String?,

    // Status
    val status: String,
    val userStatus: String,

    // Timestamps (Instant as epoch millis)
    val firstSeenAt: Long,
    val lastUpdatedAt: Long
)