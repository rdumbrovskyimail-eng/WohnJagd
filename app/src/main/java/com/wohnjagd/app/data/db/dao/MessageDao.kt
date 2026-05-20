package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wohnjagd.app.data.db.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Delete
    suspend fun delete(message: MessageEntity): Int

    @Query("SELECT * FROM messages WHERE applicationId = :applicationId ORDER BY timestamp ASC")
    fun observeForApplication(applicationId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE contactId = :contactId ORDER BY timestamp DESC")
    fun observeForContact(contactId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE direction = 'INCOMING' AND timestamp > :since ORDER BY timestamp DESC")
    suspend fun getIncomingSince(since: Long): List<MessageEntity>
}