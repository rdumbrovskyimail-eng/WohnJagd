package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wohnjagd.app.data.db.entities.BlacklistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlacklistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BlacklistEntity): Long

    @Delete
    suspend fun delete(entry: BlacklistEntity): Int

    @Query("SELECT * FROM blacklist WHERE contactValue = :value LIMIT 1")
    suspend fun findByValue(value: String): BlacklistEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM blacklist WHERE contactValue = :value)")
    suspend fun contains(value: String): Boolean

    @Query("SELECT * FROM blacklist ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<BlacklistEntity>>

    @Query("DELETE FROM blacklist WHERE contactValue = :value")
    suspend fun removeByValue(value: String): Int
}