package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.ListingSourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingSourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(link: ListingSourceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(links: List<ListingSourceEntity>)

    @Update
    suspend fun update(link: ListingSourceEntity): Int

    @Query("SELECT * FROM listing_sources WHERE listingId = :listingId")
    suspend fun getForListing(listingId: String): List<ListingSourceEntity>

    @Query("SELECT * FROM listing_sources WHERE listingId = :listingId")
    fun observeForListing(listingId: String): Flow<List<ListingSourceEntity>>

    @Query("SELECT * FROM listing_sources WHERE sourceId = :sourceId AND externalId = :externalId LIMIT 1")
    suspend fun findByExternal(sourceId: String, externalId: String): ListingSourceEntity?

    @Query("DELETE FROM listing_sources WHERE listingId = :listingId")
    suspend fun deleteForListing(listingId: String): Int

    @Query("SELECT COUNT(*) FROM listing_sources WHERE sourceId = :sourceId AND firstSeenAt > :since")
    suspend fun countSeenSince(sourceId: String, since: Long): Int
}