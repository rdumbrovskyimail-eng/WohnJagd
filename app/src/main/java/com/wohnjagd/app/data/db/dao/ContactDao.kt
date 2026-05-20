package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(contact: ContactEntity)

    @Update
    suspend fun update(contact: ContactEntity): Int

    @Delete
    suspend fun delete(contact: ContactEntity): Int

    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getById(id: String): ContactEntity?

    @Query("SELECT * FROM contacts WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): ContactEntity?

    @Query("SELECT * FROM contacts WHERE phone = :phone LIMIT 1")
    suspend fun findByPhone(phone: String): ContactEntity?

    @Query("SELECT * FROM contacts ORDER BY COALESCE(name, organization) ASC")
    fun observeAll(): Flow<List<ContactEntity>>
}