package com.wohnjagd.app.domain.usecase.listings

import com.wohnjagd.app.domain.model.Listing
import com.wohnjagd.app.domain.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFeedUseCase @Inject constructor(
    private val repository: ListingRepository
) {
    operator fun invoke(): Flow<List<Listing>> = repository.observeFeed()
}