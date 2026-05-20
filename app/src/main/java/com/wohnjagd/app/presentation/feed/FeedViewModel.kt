package com.wohnjagd.app.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wohnjagd.app.domain.usecase.listings.ObserveFeedUseCase
import com.wohnjagd.app.domain.usecase.listings.RefreshSourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    observeFeed: ObserveFeedUseCase,
    private val refreshSource: RefreshSourceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeFeed().collect { listings ->
                _uiState.update {
                    it.copy(listings = listings, isInitialLoading = false)
                }
            }
        }
    }

    fun refresh() {
        if (_uiState.value.isRefreshing) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(isRefreshing = true, errorMessage = null, lastRefreshNewCount = 0)
            }
            try {
                val result = refreshSource()
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        lastRefreshNewCount = result.totalNew,
                        errorMessage = if (result.hasErrors) {
                            "Ошибки источников: ${result.errors.size}"
                        } else null
                    )
                }
            } catch (t: Throwable) {
                Timber.e(t, "Refresh failed")
                _uiState.update {
                    it.copy(isRefreshing = false, errorMessage = t.message ?: "Unknown error")
                }
            }
        }
    }

    fun clearTransientMessages() {
        _uiState.update { it.copy(errorMessage = null, lastRefreshNewCount = 0) }
    }
}