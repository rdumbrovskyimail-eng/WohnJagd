package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.SourceStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(stats: SourceStatsEntity)

    @Update
    suspend fun update(stats: SourceStatsEntity): Int

    @Query("SELECT * FROM source_stats WHERE sourceId = :sourceId")
    suspend fun getById(sourceId: String): SourceStatsEntity?

    @Query("SELECT * FROM source_stats WHERE sourceId = :sourceId")
    fun observeById(sourceId: String): Flow<SourceStatsEntity?>

    @Query("SELECT * FROM source_stats ORDER BY category ASC, displayName ASC")
    fun observeAll(): Flow<List<SourceStatsEntity>>

    @Query("SELECT * FROM source_stats WHERE isEnabled = 1 AND isHealthy = 1")
    suspend fun getHealthyEnabled(): List<SourceStatsEntity>

    @Query("SELECT * FROM source_stats WHERE isEnabled = 1")
    suspend fun getAllEnabled(): List<SourceStatsEntity>

    @Query("UPDATE source_stats SET isEnabled = :enabled, updatedAt = :now WHERE sourceId = :sourceId")
    suspend fun setEnabled(sourceId: String, enabled: Boolean, now: Long)
}