package com.wohnjagd.app.domain.model

/**
 * Критерии поиска — применяются при опросе источников и фильтрации feed.
 */
data class SearchCriteria(
    val communeIds: Set<CommuneId> = emptySet(),
    val minSizeSqm: Float? = null,
    val maxSizeSqm: Float? = null,
    val minRooms: Float? = null,
    val maxRooms: Float? = null,
    val maxKaltmiete: Money? = null,
    val acceptWbs: Boolean? = null,
    val petsRequired: Boolean = false,
    val excludeMakler: Boolean = false
) {
    val isAnyCommune: Boolean get() = communeIds.isEmpty()
    val communeCount: Int get() = communeIds.size

    companion object {
        /** Все 19 коммун Rhein-Sieg, без ограничений по размеру/комнатам. */
        fun forAllCommunes(): SearchCriteria = SearchCriteria(
            communeIds = CommuneId.entries.toSet()
        )
    }
}