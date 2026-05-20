package com.wohnjagd.app.core.di

import android.content.Context
import androidx.room.Room
import com.wohnjagd.app.data.db.WohnJagdDatabase
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WohnJagdDatabase =
        Room.databaseBuilder(
            context,
            WohnJagdDatabase::class.java,
            WohnJagdDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides fun provideListingDao(db: WohnJagdDatabase): ListingDao = db.listingDao()
    @Provides fun provideListingSourceDao(db: WohnJagdDatabase): ListingSourceDao = db.listingSourceDao()
    @Provides fun providePhotoDao(db: WohnJagdDatabase): PhotoDao = db.photoDao()
    @Provides fun provideApplicationDao(db: WohnJagdDatabase): ApplicationDao = db.applicationDao()
    @Provides fun provideMessageDao(db: WohnJagdDatabase): MessageDao = db.messageDao()
    @Provides fun provideContactDao(db: WohnJagdDatabase): ContactDao = db.contactDao()
    @Provides fun provideBlacklistDao(db: WohnJagdDatabase): BlacklistDao = db.blacklistDao()
    @Provides fun provideSourceStatsDao(db: WohnJagdDatabase): SourceStatsDao = db.sourceStatsDao()
    @Provides fun provideSearchQueryDao(db: WohnJagdDatabase): SearchQueryDao = db.searchQueryDao()
    @Provides fun provideEventLogDao(db: WohnJagdDatabase): EventLogDao = db.eventLogDao()
    @Provides fun provideAiCacheDao(db: WohnJagdDatabase): AiCacheDao = db.aiCacheDao()
    @Provides fun provideScamRuleDao(db: WohnJagdDatabase): ScamRuleDao = db.scamRuleDao()
}