package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wohnjagd.app.data.db.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: PhotoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photos WHERE listingId = :listingId ORDER BY orderIndex ASC")
    suspend fun getForListing(listingId: String): List<PhotoEntity>

    @Query("SELECT * FROM photos WHERE listingId = :listingId ORDER BY orderIndex ASC")
    fun observeForListing(listingId: String): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photos WHERE pHash = :pHash")
    suspend fun findByHash(pHash: Long): List<PhotoEntity>

    @Query("DELETE FROM photos WHERE listingId = :listingId")
    suspend fun deleteForListing(listingId: String): Int
}