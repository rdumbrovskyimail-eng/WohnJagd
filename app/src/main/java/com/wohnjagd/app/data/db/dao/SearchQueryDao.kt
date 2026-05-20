package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.SearchQueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchQueryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(query: SearchQueryEntity)

    @Update
    suspend fun update(query: SearchQueryEntity): Int

    @Delete
    suspend fun delete(query: SearchQueryEntity): Int

    @Query("SELECT * FROM search_queries WHERE id = :id")
    suspend fun getById(id: String): SearchQueryEntity?

    @Query("SELECT * FROM search_queries ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<SearchQueryEntity>>

    @Query("SELECT * FROM search_queries WHERE enabled = 1")
    suspend fun getEnabled(): List<SearchQueryEntity>
}