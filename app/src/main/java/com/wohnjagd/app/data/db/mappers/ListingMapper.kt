package com.wohnjagd.app.data.db.mappers

import com.wohnjagd.app.data.db.entities.ListingEntity
import com.wohnjagd.app.data.db.entities.ListingSourceEntity
import com.wohnjagd.app.data.db.entities.PhotoEntity
import com.wohnjagd.app.domain.model.Address
import com.wohnjagd.app.domain.model.CommuneId
import com.wohnjagd.app.domain.model.ContactId
import com.wohnjagd.app.domain.model.Furnishing
import com.wohnjagd.app.domain.model.HeatingType
import com.wohnjagd.app.domain.model.LandlordType
import com.wohnjagd.app.domain.model.Listing
import com.wohnjagd.app.domain.model.ListingFeatures
import com.wohnjagd.app.domain.model.ListingGeometry
import com.wohnjagd.app.domain.model.ListingId
import com.wohnjagd.app.domain.model.ListingMedia
import com.wohnjagd.app.domain.model.ListingPrice
import com.wohnjagd.app.domain.model.ListingStatus
import com.wohnjagd.app.domain.model.Money
import com.wohnjagd.app.domain.model.ParkingType
import com.wohnjagd.app.domain.model.PetsPolicy
import com.wohnjagd.app.domain.model.Photo
import com.wohnjagd.app.domain.model.PipelineState
import com.wohnjagd.app.domain.model.UserStatus
import java.time.Instant
import java.time.LocalDate

/**
 * Маппер ListingEntity (+ связанные таблицы) → доменная модель Listing.
 * toEntity не реализован в Phase 3a — записи в БД делает Normalizer
 * напрямую из RawListing. Парсинг score/scamRisk из JSON отложен до Phase 4.
 */
object ListingMapper {

    fun toDomain(
        entity: ListingEntity,
        photos: List<PhotoEntity>,
        sources: List<ListingSourceEntity>
    ): Listing = Listing(
        id = ListingId(entity.id),
        fingerprint = entity.fingerprint,
        title = entity.title,
        description = entity.description,
        price = ListingPrice(
            kaltmiete = Money(entity.kaltmieteCents),
            nebenkosten = entity.nebenkostenCents?.let(::Money),
            heizkosten = entity.heizkostenCents?.let(::Money),
            heizkostenEstimated = entity.heizkostenEstimatedCents?.let(::Money),
            kaution = entity.kautionCents?.let(::Money),
            provision = entity.provisionCents?.let(::Money)
        ),
        geometry = ListingGeometry(
            sizeSqm = entity.sizeSqm,
            rooms = entity.rooms,
            floor = entity.floor,
            totalFloors = entity.totalFloors,
            hasBalcony = entity.hasBalcony,
            hasGarden = entity.hasGarden,
            hasElevator = entity.hasElevator,
            hasBasement = entity.hasBasement,
            parkingType = entity.parkingType?.let { safeEnumValueOf<ParkingType>(it) }
        ),
        features = ListingFeatures(
            heatingType = safeEnumValueOf<HeatingType>(entity.heatingType) ?: HeatingType.UNKNOWN,
            energyClass = entity.energyClass,
            energyValue = entity.energyValue,
            furnishing = safeEnumValueOf<Furnishing>(entity.furnishing) ?: Furnishing.UNFURNISHED,
            petsPolicy = safeEnumValueOf<PetsPolicy>(entity.petsPolicy) ?: PetsPolicy.UNKNOWN,
            smokingAllowed = entity.smokingAllowed,
            needsWbs = entity.needsWbs,
            barrierFree = entity.barrierFree,
            availableFrom = entity.availableFrom?.let { runCatching { LocalDate.parse(it) }.getOrNull() },
            minRentMonths = entity.minRentMonths
        ),
        location = Address(
            raw = entity.addressRaw,
            street = entity.street,
            houseNumber = entity.houseNumber,
            postcode = entity.postcode,
            city = entity.city,
            district = entity.district,
            communeId = entity.communeId?.let { safeEnumValueOf<CommuneId>(it) },
            lat = entity.lat,
            lng = entity.lng
        ),
        contactId = entity.contactId?.let(::ContactId),
        landlordType = safeEnumValueOf<LandlordType>(entity.landlordType) ?: LandlordType.UNKNOWN,
        media = ListingMedia(
            photos = photos.map(PhotoMapper::toDomain).sortedBy { it.order },
            floorPlan = entity.floorPlanUrl?.let { Photo(url = it) },
            videoUrl = entity.videoUrl
        ),
        sources = sources.map(ListingSourceMapper::toDomain),
        pipeline = PipelineState(
            geocoded = entity.geocoded,
            enriched = entity.enriched,
            scoredAt = entity.scoredAt?.let(Instant::ofEpochMilli),
            scamCheckedAt = entity.scamCheckedAt?.let(Instant::ofEpochMilli)
        ),
        score = null,
        scamRisk = null,
        status = safeEnumValueOf<ListingStatus>(entity.status) ?: ListingStatus.ACTIVE,
        userStatus = safeEnumValueOf<UserStatus>(entity.userStatus) ?: UserStatus.NEW,
        firstSeenAt = Instant.ofEpochMilli(entity.firstSeenAt),
        lastUpdatedAt = Instant.ofEpochMilli(entity.lastUpdatedAt)
    )

    private inline fun <reified T : Enum<T>> safeEnumValueOf(name: String): T? =
        runCatching { enumValueOf<T>(name) }.getOrNull()
}