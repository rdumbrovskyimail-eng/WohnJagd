package com.wohnjagd.app.domain.usecase.listings

import com.wohnjagd.app.core.result.AppError
import com.wohnjagd.app.core.result.DataResult
import com.wohnjagd.app.data.sources.SourceConnector
import com.wohnjagd.app.domain.model.SearchCriteria
import com.wohnjagd.app.domain.model.SourceId
import com.wohnjagd.app.domain.repository.ListingRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Запускает fetch у всех включённых коннекторов (или конкретного) и
 * передаёт результат в Repository для upsert.
 */
class RefreshSourceUseCase @Inject constructor(
    private val repository: ListingRepository,
    private val connectors: Set<@JvmSuppressWildcards SourceConnector>
) {
    suspend operator fun invoke(sourceId: SourceId? = null): RefreshResult {
        val toRefresh = if (sourceId != null) {
            connectors.filter { it.sourceId == sourceId }
        } else {
            connectors
        }

        var totalFetched = 0
        var totalNew = 0
        val errors = mutableListOf<Pair<SourceId, AppError>>()

        for (connector in toRefresh) {
            Timber.i("Refreshing source: ${connector.displayName}")
            when (val result = connector.fetch(SearchCriteria.forAllCommunes())) {
                is DataResult.Success -> {
                    totalFetched += result.data.size
                    val newCount = repository.upsertBatch(result.data)
                    totalNew += newCount
                    Timber.i("${connector.displayName}: ${result.data.size} fetched, $newCount new")
                }
                is DataResult.Failure -> {
                    errors += connector.sourceId to result.error
                    Timber.w("${connector.displayName} failed: ${result.error.message}")
                }
            }
        }

        return RefreshResult(totalFetched, totalNew, errors)
    }
}

data class RefreshResult(
    val totalFetched: Int,
    val totalNew: Int,
    val errors: List<Pair<SourceId, AppError>>
) {
    val hasErrors: Boolean get() = errors.isNotEmpty()
}