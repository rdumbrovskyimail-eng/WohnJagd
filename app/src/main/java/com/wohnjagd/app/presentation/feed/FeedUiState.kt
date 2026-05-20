package com.wohnjagd.app.presentation.feed

import com.wohnjagd.app.domain.model.Listing

data class FeedUiState(
    val listings: List<Listing> = emptyList(),
    val isInitialLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val lastRefreshNewCount: Int = 0,
    val errorMessage: String? = null
)