package com.wohnjagd.app.data.sources.portals

import com.wohnjagd.app.core.di.IoDispatcher
import com.wohnjagd.app.core.result.AppError
import com.wohnjagd.app.core.result.DataResult
import com.wohnjagd.app.data.sources.ProbeResult
import com.wohnjagd.app.data.sources.RawListing
import com.wohnjagd.app.data.sources.SourceConnector
import com.wohnjagd.app.domain.model.PollingStrategy
import com.wohnjagd.app.domain.model.SearchCriteria
import com.wohnjagd.app.domain.model.SourceCategory
import com.wohnjagd.app.domain.model.SourceId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import timber.log.Timber
import java.io.IOException
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Коннектор к Kleinanzeigen.de — публичный поиск Mietwohnungen в Rhein-Sieg-Kreis.
 *
 * URL: https://www.kleinanzeigen.de/s-mietwohnung-rhein-sieg-kreis/k0
 *
 * Парсит первую страницу результатов (~25-50 объявлений) через Jsoup.
 * Все ошибки парсинга отдельных карточек проглатываются (логируются),
 * чтобы один кривой DOM не уронил весь fetch.
 *
 * При HTTP-ошибке возвращает Failure — Demo-источник продолжит работать.
 */
@Singleton
class KleinanzeigenConnector @Inject constructor(
    private val httpClient: OkHttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SourceConnector {

    override val sourceId: SourceId = SourceId.KLEINANZEIGEN
    override val displayName: String = "Kleinanzeigen"
    override val category: SourceCategory = SourceCategory.PORTAL
    override val defaultPollingStrategy: PollingStrategy = PollingStrategy.HOT

    override suspend fun fetch(criteria: SearchCriteria): DataResult<List<RawListing>> =
        withContext(ioDispatcher) {
            try {
                val html = fetchHtml(SEARCH_URL)
                    ?: return@withContext DataResult.Failure(
                        AppError.Network("Empty or non-200 response from Kleinanzeigen")
                    )
                val listings = parseHtml(html)
                Timber.i("Kleinanzeigen: parsed ${listings.size} listings")
                DataResult.Success(listings)
            } catch (e: IOException) {
                Timber.w(e, "Kleinanzeigen network error")
                DataResult.Failure(AppError.Network(e.message ?: "Network error", e))
            } catch (e: Exception) {
                Timber.w(e, "Kleinanzeigen parse error")
                DataResult.Failure(AppError.Parse(e.message ?: "Parse error", e))
            }
        }

    override suspend fun probe(): DataResult<ProbeResult> = withContext(ioDispatcher) {
        val start = System.currentTimeMillis()
        try {
            val request = Request.Builder().url(SEARCH_URL).head().build()
            httpClient.newCall(request).execute().use { response ->
                val elapsed = System.currentTimeMillis() - start
                DataResult.Success(
                    ProbeResult(
                        isReachable = response.isSuccessful,
                        responseTimeMs = elapsed,
                        message = "HTTP ${response.code}"
                    )
                )
            }
        } catch (e: Exception) {
            DataResult.Failure(AppError.Network(e.message ?: "Probe failed", e))
        }
    }

    // ─── HTTP ─────────────────────────────────────────────────────────────

    private fun fetchHtml(url: String): String? {
        val request = Request.Builder().url(url).get().build()
        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Timber.w("Kleinanzeigen HTTP ${response.code}: ${response.message}")
                return null
            }
            return response.body?.string()
        }
    }

    // ─── PARSING ──────────────────────────────────────────────────────────

    private fun parseHtml(html: String): List<RawListing> {
        val doc = Jsoup.parse(html)
        val items = doc.select("article.aditem")
        val now = Instant.now()

        return items.mapNotNull { item ->
            try {
                parseItem(item, now)
            } catch (e: Exception) {
                Timber.w(e, "Failed to parse one Kleinanzeigen item")
                null
            }
        }
    }

    private fun parseItem(item: Element, now: Instant): RawListing? {
        val externalId = item.attr("data-adid").takeIf { it.isNotBlank() } ?: return null

        val href = item.selectFirst("a.ellipsis, a[href]")
            ?.attr("href")
            ?.takeIf { it.isNotBlank() }
            ?: return null
        val sourceUrl = if (href.startsWith("http")) href else BASE_URL + href

        val title = item.selectFirst("h2 a.ellipsis")?.text()?.trim()
            ?: item.selectFirst("h2")?.text()?.trim()
            ?: return null

        val description = item.selectFirst("p.aditem-main--middle--description")
            ?.text()
            ?.trim()
            .orEmpty()

        val priceText = item.selectFirst(".aditem-main--middle--price-shipping--price")
            ?.text()
            ?.trim()
        val priceCents = parsePriceCents(priceText)

        val locationText = item.selectFirst(".aditem-main--top--left")
            ?.text()
            ?.trim()
            .orEmpty()
        val (postcode, city) = parseLocation(locationText)

        val tags = item.select(".simpletag, .text-onSurfaceSecondary").map { it.text().trim() }
        val sizeSqm = tags.firstNotNullOfOrNull { parseSize(it) }
        val rooms = tags.firstNotNullOfOrNull { parseRooms(it) }

        val photoUrl = item.selectFirst(".aditem-image img, .imagebox img")?.let { img ->
            sequenceOf(
                img.attr("src"),
                img.attr("data-src"),
                img.attr("data-srcset").substringBefore(' ')
            ).firstOrNull { it.isNotBlank() && it.startsWith("http") }
        }

        return RawListing(
            sourceId = sourceId,
            externalId = externalId,
            sourceUrl = sourceUrl,
            title = title,
            description = description,
            priceCents = priceCents,
            sizeSqm = sizeSqm,
            rooms = rooms,
            addressRaw = locationText,
            postcode = postcode,
            city = city,
            photoUrls = listOfNotNull(photoUrl),
            firstSeenAt = now
        )
    }

    private fun parsePriceCents(text: String?): Long? {
        if (text.isNullOrBlank()) return null
        val digitsOnly = text.filter { it.isDigit() }
        if (digitsOnly.isEmpty()) return null
        val euros = digitsOnly.toLongOrNull() ?: return null
        if (euros <= 0L) return null
        return euros * 100L
    }

    private fun parseLocation(text: String): Pair<String?, String?> {
        if (text.isBlank()) return null to null
        val cleaned = text.split('·').firstOrNull()?.trim() ?: text.trim()
        val match = LOCATION_REGEX.find(cleaned)
        return if (match != null) {
            match.groupValues[1] to match.groupValues[2].trim()
        } else {
            null to cleaned.takeIf { it.isNotBlank() }
        }
    }

    private fun parseSize(text: String): Float? {
        val match = SIZE_REGEX.find(text) ?: return null
        return match.groupValues[1].replace(',', '.').toFloatOrNull()
    }

    private fun parseRooms(text: String): Float? {
        val match = ROOMS_REGEX.find(text) ?: return null
        return match.groupValues[1].replace(',', '.').toFloatOrNull()
    }

    companion object {
        private const val BASE_URL = "https://www.kleinanzeigen.de"
        private const val SEARCH_URL = "$BASE_URL/s-mietwohnung-rhein-sieg-kreis/k0"

        private val LOCATION_REGEX = Regex("""^(\d{5})\s+(.+)$""")
        private val SIZE_REGEX = Regex("""(\d+(?:[.,]\d+)?)\s*m²""")
        private val ROOMS_REGEX = Regex("""(\d+(?:[.,]\d+)?)\s*Zi""")
    }
}