package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wohnjagd.app.data.db.entities.AiCacheEntity

@Dao
interface AiCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: AiCacheEntity): Long

    @Query("SELECT * FROM ai_cache WHERE promptHash = :hash LIMIT 1")
    suspend fun findByHash(hash: String): AiCacheEntity?

    @Query("DELETE FROM ai_cache WHERE expiresAt IS NOT NULL AND expiresAt < :now")
    suspend fun deleteExpired(now: Long): Int

    @Query("DELETE FROM ai_cache")
    suspend fun clear(): Int

    @Query("SELECT COUNT(*) FROM ai_cache")
    suspend fun count(): Int
}