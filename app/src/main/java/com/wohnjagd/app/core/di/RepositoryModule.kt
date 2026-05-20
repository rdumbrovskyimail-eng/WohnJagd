package com.wohnjagd.app.core.di

import com.wohnjagd.app.data.repository.ListingRepositoryImpl
import com.wohnjagd.app.domain.repository.ListingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindListingRepository(impl: ListingRepositoryImpl): ListingRepository
}