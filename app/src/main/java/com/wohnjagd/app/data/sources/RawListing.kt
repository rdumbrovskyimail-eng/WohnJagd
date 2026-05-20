package com.wohnjagd.app.data.sources

import com.wohnjagd.app.domain.model.SourceId
import java.time.Instant

/**
 * Сырое объявление от коннектора, до нормализации в Entity.
 * Содержит только то, что источник смог извлечь — большинство полей nullable.
 */
data class RawListing(
    val sourceId: SourceId,
    val externalId: String,
    val sourceUrl: String,
    val title: String,
    val description: String,
    val priceCents: Long? = null,
    val sizeSqm: Float? = null,
    val rooms: Float? = null,
    val addressRaw: String,
    val postcode: String? = null,
    val city: String? = null,
    val photoUrls: List<String> = emptyList(),
    val contactName: String? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val firstSeenAt: Instant
)