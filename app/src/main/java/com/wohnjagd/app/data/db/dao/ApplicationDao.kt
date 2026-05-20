package com.wohnjagd.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wohnjagd.app.data.db.entities.ApplicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(app: ApplicationEntity)

    @Update
    suspend fun update(app: ApplicationEntity): Int

    @Delete
    suspend fun delete(app: ApplicationEntity): Int

    @Query("SELECT * FROM applications WHERE id = :id")
    suspend fun getById(id: String): ApplicationEntity?

    @Query("SELECT * FROM applications WHERE id = :id")
    fun observeById(id: String): Flow<ApplicationEntity?>

    @Query("SELECT * FROM applications WHERE listingId = :listingId LIMIT 1")
    suspend fun findForListing(listingId: String): ApplicationEntity?

    @Query("SELECT * FROM applications ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM applications WHERE status = :status ORDER BY updatedAt DESC")
    fun observeByStatus(status: String): Flow<List<ApplicationEntity>>

    @Query("""
        SELECT * FROM applications
        WHERE nextActionAt IS NOT NULL AND nextActionAt <= :now
        ORDER BY nextActionAt ASC
    """)
    suspend fun findOverdueActions(now: Long): List<ApplicationEntity>

    @Query("""
        SELECT COUNT(*) FROM applications
        WHERE status NOT IN ('CONTRACT_SIGNED', 'REJECTED', 'WITHDREW', 'EXPIRED')
    """)
    fun countActive(): Flow<Int>
}