package com.wohnjagd.app.data.sources

import com.wohnjagd.app.core.result.DataResult
import com.wohnjagd.app.domain.model.PollingStrategy
import com.wohnjagd.app.domain.model.SearchCriteria
import com.wohnjagd.app.domain.model.SourceCategory
import com.wohnjagd.app.domain.model.SourceId
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Демо-коннектор с фиксированными тестовыми объявлениями из разных коммун
 * Rhein-Sieg. Используется для проверки end-to-end pipeline до подключения
 * реальных источников (Phase 3b и далее).
 *
 * Фото — picsum.photos с stable seeds, чтобы каждое объявление имело
 * одно и то же случайное изображение.
 */
@Singleton
class DemoConnector @Inject constructor() : SourceConnector {

    override val sourceId: SourceId = SourceId("demo")
    override val displayName: String = "Demo (тестовые данные)"
    override val category: SourceCategory = SourceCategory.PORTAL
    override val defaultPollingStrategy: PollingStrategy = PollingStrategy.MANUAL

    override suspend fun fetch(criteria: SearchCriteria): DataResult<List<RawListing>> {
        val now = Instant.now()
        val listings = listOf(
            RawListing(
                sourceId = sourceId,
                externalId = "demo-1",
                sourceUrl = "https://example.com/demo-1",
                title = "Helle 2-Zimmer-Wohnung im Zentrum",
                description = "Frisch renovierte Wohnung mit Balkon, EBK und Stellplatz. Ruhige Seitenstraße.",
                priceCents = 42000L,
                sizeSqm = 55f,
                rooms = 2f,
                addressRaw = "Markt 5, 53783 Eitorf",
                postcode = "53783",
                city = "Eitorf",
                photoUrls = listOf("https://picsum.photos/seed/wohnjagd-1/800/500"),
                contactName = "Familie Müller",
                firstSeenAt = now
            ),
            RawListing(
                sourceId = sourceId,
                externalId = "demo-2",
                sourceUrl = "https://example.com/demo-2",
                title = "Gemütliche Single-Wohnung mit Garten",
                description = "Erdgeschoss-Wohnung mit Gartenanteil. Ideal für ruhige Mieter ohne Haustiere.",
                priceCents = 39000L,
                sizeSqm = 42f,
                rooms = 1.5f,
                addressRaw = "Lindenweg 12, 53840 Troisdorf",
                postcode = "53840",
                city = "Troisdorf",
                photoUrls = listOf("https://picsum.photos/seed/wohnjagd-2/800/500"),
                contactName = "Hausverwaltung Schmitz",
                firstSeenAt = now
            ),
            RawListing(
                sourceId = sourceId,
                externalId = "demo-3",
                sourceUrl = "https://example.com/demo-3",
                title = "Modernisierte 3-Zimmer-Wohnung",
                description = "Komplett saniert, hochwertige Ausstattung, Aufzug, Tiefgaragenstellplatz.",
                priceCents = 54500L,
                sizeSqm = 68f,
                rooms = 3f,
                addressRaw = "Bahnhofstraße 23, 53721 Siegburg",
                postcode = "53721",
                city = "Siegburg",
                photoUrls = listOf("https://picsum.photos/seed/wohnjagd-3/800/500"),
                contactName = "GWG Siegburg",
                firstSeenAt = now
            ),
            RawListing(
                sourceId = sourceId,
                externalId = "demo-4",
                sourceUrl = "https://example.com/demo-4",
                title = "Dachgeschosswohnung mit Blick ins Grüne",
                description = "Charmante DG-Wohnung mit Dachschrägen. Vorzugsweise an Berufstätige.",
                priceCents = 48000L,
                sizeSqm = 50f,
                rooms = 2f,
                addressRaw = "Hauptstraße 78, 53773 Hennef",
                postcode = "53773",
                city = "Hennef",
                photoUrls = listOf("https://picsum.photos/seed/wohnjagd-4/800/500"),
                contactName = "Herr Becker",
                firstSeenAt = now
            ),
            RawListing(
                sourceId = sourceId,
                externalId = "demo-5",
                sourceUrl = "https://example.com/demo-5",
                title = "WBS-Wohnung in ruhiger Lage",
                description = "Sozialwohnung mit Wohnberechtigungsschein. Frisch gestrichen, Kunststofffenster.",
                priceCents = 35000L,
                sizeSqm = 48f,
                rooms = 2f,
                addressRaw = "Am Park 9, 53757 Sankt Augustin",
                postcode = "53757",
                city = "Sankt Augustin",
                photoUrls = listOf("https://picsum.photos/seed/wohnjagd-5/800/500"),
                contactName = "Wohnungsamt Sankt Augustin",
                firstSeenAt = now
            ),
            RawListing(
                sourceId = sourceId,
                externalId = "demo-6",
                sourceUrl = "https://example.com/demo-6",
                title = "Kleine Wohnung in zentraler Lage",
                description = "Kompakte 1-Zimmer-Wohnung, ideal für Singles. Nahe Bahnhof.",
                priceCents = 41000L,
                sizeSqm = 35f,
                rooms = 1f,
                addressRaw = "Schulstraße 4, 53797 Lohmar",
                postcode = "53797",
                city = "Lohmar",
                photoUrls = listOf("https://picsum.photos/seed/wohnjagd-6/800/500"),
                contactName = "Frau Wagner",
                firstSeenAt = now
            )
        )
        return DataResult.Success(listings)
    }

    override suspend fun probe(): DataResult<ProbeResult> =
        DataResult.Success(ProbeResult(isReachable = true, responseTimeMs = 0L))
}