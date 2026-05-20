package com.wohnjagd.app.domain.model

import java.time.Instant
import java.time.LocalDate
import java.util.UUID

/**
 * Идентификатор объявления (master-listing после дедупа).
 */
@JvmInline
value class ListingId(val value: String) {
    companion object {
        fun new(): ListingId = ListingId(UUID.randomUUID().toString())
    }
}

/**
 * Главная сущность приложения — нормализованное жильё (master-listing).
 * Один Listing может иметь несколько источников (см. sources).
 */
data class Listing(
    val id: ListingId,
    val fingerprint: String,
    val title: String,
    val description: String,

    val price: ListingPrice,
    val geometry: ListingGeometry,
    val features: ListingFeatures,
    val location: Address,

    val contactId: ContactId? = null,
    val landlordType: LandlordType = LandlordType.UNKNOWN,

    val media: ListingMedia = ListingMedia(),
    val sources: List<ListingSourceLink> = emptyList(),

    val pipeline: PipelineState = PipelineState(),
    val score: ScoreBreakdown? = null,
    val scamRisk: ScamRisk? = null,

    val status: ListingStatus = ListingStatus.ACTIVE,
    val userStatus: UserStatus = UserStatus.NEW,

    val firstSeenAt: Instant,
    val lastUpdatedAt: Instant
) {
    val sourceCount: Int get() = sources.size
    val hasMultipleSources: Boolean get() = sources.size > 1
}

/**
 * Денежные характеристики объявления.
 */
data class ListingPrice(
    val kaltmiete: Money,
    val nebenkosten: Money? = null,
    val heizkosten: Money? = null,
    val heizkostenEstimated: Money? = null,
    val kaution: Money? = null,
    val provision: Money? = null
) {
    /** Сумма Kalt + Neben + (Heiz или Heiz-Estimated). */
    val warmmiete: Money
        get() = kaltmiete +
                (nebenkosten ?: Money.ZERO) +
                (heizkosten ?: heizkostenEstimated ?: Money.ZERO)
}

/**
 * Физические параметры жилья.
 */
data class ListingGeometry(
    val sizeSqm: Float,
    val rooms: Float,
    val floor: Int? = null,
    val totalFloors: Int? = null,
    val hasBalcony: Boolean = false,
    val hasGarden: Boolean = false,
    val hasElevator: Boolean = false,
    val hasBasement: Boolean = false,
    val parkingType: ParkingType? = null
) {
    val pricePerSqm: Float get() = sizeSqm.takeIf { it > 0f } ?: 1f
}

/**
 * Дополнительные характеристики.
 */
data class ListingFeatures(
    val heatingType: HeatingType = HeatingType.UNKNOWN,
    val energyClass: String? = null,
    val energyValue: Float? = null,
    val furnishing: Furnishing = Furnishing.UNFURNISHED,
    val petsPolicy: PetsPolicy = PetsPolicy.UNKNOWN,
    val smokingAllowed: Boolean? = null,
    val needsWbs: Boolean = false,
    val barrierFree: Boolean = false,
    val availableFrom: LocalDate? = null,
    val minRentMonths: Int? = null
)

/**
 * Медиафайлы.
 */
data class ListingMedia(
    val photos: List<Photo> = emptyList(),
    val floorPlan: Photo? = null,
    val videoUrl: String? = null
) {
    val hasPhotos: Boolean get() = photos.isNotEmpty()
    val mainPhoto: Photo? get() = photos.minByOrNull { it.order }
}

/**
 * Одно фото с perceptual-hash для дедупликации.
 */
data class Photo(
    val url: String,
    val pHash: Long? = null,
    val localPath: String? = null,
    val order: Int = 0
)

/**
 * Ссылка на конкретный источник, в котором это объявление было найдено.
 * У одного Listing может быть N таких связей.
 */
data class ListingSourceLink(
    val sourceId: SourceId,
    val sourceUrl: String,
    val externalId: String,
    val firstSeenAt: Instant,
    val lastSeenAt: Instant
)

/**
 * Состояние обработки в pipeline.
 */
data class PipelineState(
    val geocoded: Boolean = false,
    val enriched: Boolean = false,
    val scoredAt: Instant? = null,
    val scamCheckedAt: Instant? = null
)

/**
 * Глобальный статус listing'а.
 */
enum class ListingStatus {
    ACTIVE,
    RENTED,
    ARCHIVED,
    REMOVED
}

/**
 * Состояние со стороны пользователя.
 */
enum class UserStatus {
    NEW,
    SEEN,
    INTERESTED,
    HIDDEN,
    PINNED
}