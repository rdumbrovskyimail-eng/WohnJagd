package com.wohnjagd.app.data.repository

import com.wohnjagd.app.core.di.IoDispatcher
import com.wohnjagd.app.data.db.dao.ListingDao
import com.wohnjagd.app.data.db.dao.ListingSourceDao
import com.wohnjagd.app.data.db.dao.PhotoDao
import com.wohnjagd.app.data.db.mappers.ListingMapper
import com.wohnjagd.app.data.sources.RawListing
import com.wohnjagd.app.data.sources.RawListingNormalizer
import com.wohnjagd.app.domain.model.Listing
import com.wohnjagd.app.domain.model.ListingId
import com.wohnjagd.app.domain.model.UserStatus
import com.wohnjagd.app.domain.repository.ListingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListingRepositoryImpl @Inject constructor(
    private val listingDao: ListingDao,
    private val photoDao: PhotoDao,
    private val listingSourceDao: ListingSourceDao,
    private val normalizer: RawListingNormalizer,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ListingRepository {

    override fun observeFeed(): Flow<List<Listing>> =
        listingDao.observeActiveFeed()
            .map { entities ->
                entities.map { entity ->
                    val photos = photoDao.getForListing(entity.id)
                    val sources = listingSourceDao.getForListing(entity.id)
                    ListingMapper.toDomain(entity, photos, sources)
                }
            }
            .flowOn(ioDispatcher)

    override fun observeById(id: ListingId): Flow<Listing?> =
        listingDao.observeById(id.value)
            .map { entity ->
                entity?.let {
                    val photos = photoDao.getForListing(entity.id)
                    val sources = listingSourceDao.getForListing(entity.id)
                    ListingMapper.toDomain(entity, photos, sources)
                }
            }
            .flowOn(ioDispatcher)

    override suspend fun upsertBatch(rawListings: List<RawListing>): Int =
        withContext(ioDispatcher) {
            var inserted = 0
            val now = Instant.now().toEpochMilli()

            for (raw in rawListings) {
                val existing = listingSourceDao.findByExternal(raw.sourceId.value, raw.externalId)
                if (existing != null) {
                    listingSourceDao.update(existing.copy(lastSeenAt = now))
                    continue
                }
                val normalized = normalizer.normalize(raw)
                listingDao.upsert(normalized.listing)
                photoDao.insertAll(normalized.photos)
                listingSourceDao.insert(normalized.sourceLink)
                inserted++
            }
            inserted
        }

    override suspend fun markSeen(id: ListingId) {
        withContext(ioDispatcher) {
            listingDao.updateUserStatus(
                id = id.value,
                userStatus = UserStatus.SEEN.name,
                now = Instant.now().toEpochMilli()
            )
        }
    }

    override suspend fun hide(id: ListingId) {
        withContext(ioDispatcher) {
            listingDao.updateUserStatus(
                id = id.value,
                userStatus = UserStatus.HIDDEN.name,
                now = Instant.now().toEpochMilli()
            )
        }
    }
}