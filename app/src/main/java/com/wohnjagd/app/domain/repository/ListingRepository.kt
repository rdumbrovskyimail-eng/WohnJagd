package com.wohnjagd.app.domain.repository

import com.wohnjagd.app.data.sources.RawListing
import com.wohnjagd.app.domain.model.Listing
import com.wohnjagd.app.domain.model.ListingId
import kotlinx.coroutines.flow.Flow

/**
 * Главный репозиторий объявлений. Источник правды для UI и Use Cases.
 */
interface ListingRepository {

    /** Поток активных объявлений (status=ACTIVE, userStatus != HIDDEN), отсортированных по score. */
    fun observeFeed(): Flow<List<Listing>>

    /** Поток одного объявления по id. */
    fun observeById(id: ListingId): Flow<Listing?>

    /**
     * Вставка/обновление пачки сырых объявлений.
     * Если listing с (sourceId, externalId) уже есть — обновляется только lastSeenAt.
     * Возвращает количество ВНОВЬ вставленных записей.
     */
    suspend fun upsertBatch(rawListings: List<RawListing>): Int

    /** Пометить объявление как просмотренное. */
    suspend fun markSeen(id: ListingId)

    /** Скрыть объявление из feed. */
    suspend fun hide(id: ListingId)
}