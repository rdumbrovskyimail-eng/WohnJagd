package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wohnjagd.app.data.db.entities.EventLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventLogDao {

    @Insert
    suspend fun insert(event: EventLogEntity): Long

    @Insert
    suspend fun insertAll(events: List<EventLogEntity>)

    @Query("SELECT * FROM event_log ORDER BY timestamp DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<EventLogEntity>>

    @Query("SELECT * FROM event_log WHERE entityId = :entityId ORDER BY timestamp DESC")
    fun observeForEntity(entityId: String): Flow<List<EventLogEntity>>

    @Query("SELECT * FROM event_log WHERE type = :type ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getByType(type: String, limit: Int): List<EventLogEntity>

    @Query("DELETE FROM event_log WHERE timestamp < :before")
    suspend fun deleteOlderThan(before: Long): Int

    @Query("SELECT COUNT(*) FROM event_log")
    suspend fun count(): Int
}