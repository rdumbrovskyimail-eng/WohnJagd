package com.wohnjagd.app.data.sources

import com.wohnjagd.app.core.result.DataResult
import com.wohnjagd.app.domain.model.PollingStrategy
import com.wohnjagd.app.domain.model.SearchCriteria
import com.wohnjagd.app.domain.model.SourceCategory
import com.wohnjagd.app.domain.model.SourceId

/**
 * Контракт коннектора источника. Каждый источник (Kleinanzeigen,
 * Vonovia, Genossenschaft и т.д.) реализует этот интерфейс.
 *
 * Коннектор НЕ обращается к Room. Возвращает только сырые данные
 * (RawListing) — нормализация и upsert делается в ListingRepository.
 */
interface SourceConnector {

    val sourceId: SourceId
    val displayName: String
    val category: SourceCategory
    val defaultPollingStrategy: PollingStrategy

    /**
     * Получить список объявлений по критериям.
     * Возвращает Failure если источник недоступен / заблокирован.
     */
    suspend fun fetch(criteria: SearchCriteria): DataResult<List<RawListing>>

    /**
     * Health-probe без побочных эффектов. Опционально — коннектор может
     * вернуть Success без реальной проверки если probe не имеет смысла.
     */
    suspend fun probe(): DataResult<ProbeResult>
}