package com.wohnjagd.app.data.db.mappers

import com.wohnjagd.app.data.db.entities.ListingSourceEntity
import com.wohnjagd.app.domain.model.ListingSourceLink
import com.wohnjagd.app.domain.model.SourceId
import java.time.Instant

object ListingSourceMapper {

    fun toDomain(entity: ListingSourceEntity): ListingSourceLink = ListingSourceLink(
        sourceId = SourceId(entity.sourceId),
        sourceUrl = entity.sourceUrl,
        externalId = entity.externalId,
        firstSeenAt = Instant.ofEpochMilli(entity.firstSeenAt),
        lastSeenAt = Instant.ofEpochMilli(entity.lastSeenAt)
    )
}