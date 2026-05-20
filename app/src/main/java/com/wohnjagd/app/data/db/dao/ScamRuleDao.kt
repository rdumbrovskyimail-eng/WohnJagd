package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.ScamRuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScamRuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rule: ScamRuleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rules: List<ScamRuleEntity>)

    @Update
    suspend fun update(rule: ScamRuleEntity): Int

    @Delete
    suspend fun delete(rule: ScamRuleEntity): Int

    @Query("SELECT * FROM scam_rules WHERE enabled = 1")
    suspend fun getEnabled(): List<ScamRuleEntity>

    @Query("SELECT * FROM scam_rules ORDER BY id ASC")
    fun observeAll(): Flow<List<ScamRuleEntity>>
}