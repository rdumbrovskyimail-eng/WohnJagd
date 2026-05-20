package com.wohnjagd.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wohnjagd.app.data.db.dao.AiCacheDao
import com.wohnjagd.app.data.db.dao.ApplicationDao
import com.wohnjagd.app.data.db.dao.BlacklistDao
import com.wohnjagd.app.data.db.dao.ContactDao
import com.wohnjagd.app.data.db.dao.EventLogDao
import com.wohnjagd.app.data.db.dao.ListingDao
import com.wohnjagd.app.data.db.dao.ListingSourceDao
import com.wohnjagd.app.data.db.dao.MessageDao
import com.wohnjagd.app.data.db.dao.PhotoDao
import com.wohnjagd.app.data.db.dao.ScamRuleDao
import com.wohnjagd.app.data.db.dao.SearchQueryDao
import com.wohnjagd.app.data.db.dao.SourceStatsDao
import com.wohnjagd.app.data.db.entities.AiCacheEntity
import com.wohnjagd.app.data.db.entities.ApplicationEntity
import com.wohnjagd.app.data.db.entities.BlacklistEntity
import com.wohnjagd.app.data.db.entities.ContactEntity
import com.wohnjagd.app.data.db.entities.EventLogEntity
import com.wohnjagd.app.data.db.entities.ListingEntity
import com.wohnjagd.app.data.db.entities.ListingSourceEntity
import com.wohnjagd.app.data.db.entities.MessageEntity
import com.wohnjagd.app.data.db.entities.PhotoEntity
import com.wohnjagd.app.data.db.entities.ScamRuleEntity
import com.wohnjagd.app.data.db.entities.SearchQueryEntity
import com.wohnjagd.app.data.db.entities.SourceStatsEntity

@Database(
    entities = [
        ListingEntity::class,
        ListingSourceEntity::class,
        PhotoEntity::class,
        ApplicationEntity::class,
        MessageEntity::class,
        ContactEntity::class,
        BlacklistEntity::class,
        SourceStatsEntity::class,
        SearchQueryEntity::class,
        EventLogEntity::class,
        AiCacheEntity::class,
        ScamRuleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WohnJagdDatabase : RoomDatabase() {

    abstract fun listingDao(): ListingDao
    abstract fun listingSourceDao(): ListingSourceDao
    abstract fun photoDao(): PhotoDao
    abstract fun applicationDao(): ApplicationDao
    abstract fun messageDao(): MessageDao
    abstract fun contactDao(): ContactDao
    abstract fun blacklistDao(): BlacklistDao
    abstract fun sourceStatsDao(): SourceStatsDao
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun eventLogDao(): EventLogDao
    abstract fun aiCacheDao(): AiCacheDao
    abstract fun scamRuleDao(): ScamRuleDao

    companion object {
        const val DATABASE_NAME = "wohnjagd.db"
    }
}