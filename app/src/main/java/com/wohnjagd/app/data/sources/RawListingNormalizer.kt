package com.wohnjagd.app.data.sources

import com.wohnjagd.app.data.db.entities.ListingEntity
import com.wohnjagd.app.data.db.entities.ListingSourceEntity
import com.wohnjagd.app.data.db.entities.PhotoEntity
import com.wohnjagd.app.domain.model.Furnishing
import com.wohnjagd.app.domain.model.HeatingType
import com.wohnjagd.app.domain.model.LandlordType
import com.wohnjagd.app.domain.model.ListingId
import com.wohnjagd.app.domain.model.ListingStatus
import com.wohnjagd.app.domain.model.PetsPolicy
import com.wohnjagd.app.domain.model.UserStatus
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Конвертирует RawListing → ListingEntity + List<PhotoEntity> + ListingSourceEntity.
 * Fingerprint в Phase 3a — простая комбинация sourceId+externalId.
 * Настоящая дедупликация (photo pHash + address fuzzy) будет в Phase 4.
 */
@Singleton
class RawListingNormalizer @Inject constructor() {

    fun normalize(raw: RawListing): NormalizedListing {
        val now = Instant.now().toEpochMilli()
        val listingId = ListingId.new().value

        val listing = ListingEntity(
            id = listingId,
            fingerprint = computeFingerprint(raw),
            title = raw.title,
            description = raw.description,
            kaltmieteCents = raw.priceCents ?: 0L,
            nebenkostenCents = null,
            heizkostenCents = null,
            heizkostenEstimatedCents = null,
            kautionCents = null,
            provisionCents = null,
            sizeSqm = raw.sizeSqm ?: 0f,
            rooms = raw.rooms ?: 0f,
            floor = null,
            totalFloors = null,
            hasBalcony = false,
            hasGarden = false,
            hasElevator = false,
            hasBasement = false,
            parkingType = null,
            heatingType = HeatingType.UNKNOWN.name,
            energyClass = null,
            energyValue = null,
            furnishing = Furnishing.UNFURNISHED.name,
            petsPolicy = PetsPolicy.UNKNOWN.name,
            smokingAllowed = null,
            needsWbs = false,
            barrierFree = false,
            availableFrom = null,
            minRentMonths = null,
            addressRaw = raw.addressRaw,
            street = null,
            houseNumber = null,
            postcode = raw.postcode,
            city = raw.city,
            district = null,
            communeId = null,
            lat = null,
            lng = null,
            floorPlanUrl = null,
            videoUrl = null,
            contactId = null,
            landlordType = LandlordType.UNKNOWN.name,
            geocoded = false,
            enriched = false,
            scoredAt = null,
            scamCheckedAt = null,
            scoreTotal = null,
            scoreBreakdownJson = null,
            scamRiskScore = null,
            scamLevel = null,
            scamRiskJson = null,
            status = ListingStatus.ACTIVE.name,
            userStatus = UserStatus.NEW.name,
            firstSeenAt = now,
            lastUpdatedAt = now
        )

        val photos = raw.photoUrls.mapIndexed { idx, url ->
            PhotoEntity(
                listingId = listingId,
                url = url,
                pHash = null,
                localPath = null,
                orderIndex = idx
            )
        }

        val sourceLink = ListingSourceEntity(
            listingId = listingId,
            sourceId = raw.sourceId.value,
            sourceUrl = raw.sourceUrl,
            externalId = raw.externalId,
            firstSeenAt = raw.firstSeenAt.toEpochMilli(),
            lastSeenAt = raw.firstSeenAt.toEpochMilli()
        )

        return NormalizedListing(listing, photos, sourceLink)
    }

    private fun computeFingerprint(raw: RawListing): String =
        "${raw.sourceId.value}:${raw.externalId}"
}

data class NormalizedListing(
    val listing: ListingEntity,
    val photos: List<PhotoEntity>,
    val sourceLink: ListingSourceEntity
)