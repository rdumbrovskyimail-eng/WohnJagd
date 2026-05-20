package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.ListingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(listing: ListingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(listings: List<ListingEntity>)

    @Update
    suspend fun update(listing: ListingEntity): Int

    @Delete
    suspend fun delete(listing: ListingEntity): Int

    @Query("SELECT * FROM listings WHERE id = :id")
    suspend fun getById(id: String): ListingEntity?

    @Query("SELECT * FROM listings WHERE id = :id")
    fun observeById(id: String): Flow<ListingEntity?>

    @Query("SELECT * FROM listings WHERE fingerprint = :fingerprint LIMIT 1")
    suspend fun findByFingerprint(fingerprint: String): ListingEntity?

    @Query("""
        SELECT * FROM listings
        WHERE status = 'ACTIVE' AND userStatus != 'HIDDEN'
        ORDER BY scoreTotal DESC, firstSeenAt DESC
    """)
    fun observeActiveFeed(): Flow<List<ListingEntity>>

    @Query("SELECT * FROM listings WHERE status = :status ORDER BY firstSeenAt DESC")
    fun observeByStatus(status: String): Flow<List<ListingEntity>>

    @Query("""
        SELECT * FROM listings
        WHERE communeId = :communeId AND status = 'ACTIVE'
        ORDER BY scoreTotal DESC, firstSeenAt DESC
    """)
    fun observeByCommune(communeId: String): Flow<List<ListingEntity>>

    @Query("UPDATE listings SET userStatus = :userStatus, lastUpdatedAt = :now WHERE id = :id")
    suspend fun updateUserStatus(id: String, userStatus: String, now: Long)

    @Query("UPDATE listings SET status = :status, lastUpdatedAt = :now WHERE id = :id")
    suspend fun updateStatus(id: String, status: String, now: Long)

    @Query("SELECT COUNT(*) FROM listings WHERE status = 'ACTIVE'")
    fun countActive(): Flow<Int>

    @Query("SELECT COUNT(*) FROM listings WHERE status = 'ACTIVE' AND userStatus = 'NEW'")
    fun countNew(): Flow<Int>
}