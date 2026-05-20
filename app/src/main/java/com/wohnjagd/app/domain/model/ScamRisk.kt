package com.wohnjagd.app.domain.model

import java.time.Instant

/**
 * Оценка риска скама для listing'а.
 */
data class ScamRisk(
    val score: Float,
    val level: ScamLevel,
    val reasons: List<ScamReason> = emptyList(),
    val checkedAt: Instant
)

enum class ScamLevel {
    SAFE,
    SUSPICIOUS,
    HIGH_RISK
}

/**
 * Конкретные причины пометки как scam.
 */
sealed class ScamReason {

    data class PriceAnomaly(
        val expectedMin: Money,
        val actual: Money
    ) : ScamReason()

    data class ReusedPhoto(
        val matchingUrl: String? = null
    ) : ScamReason()

    data class SuspiciousLanguage(
        val phrase: String
    ) : ScamReason()

    data object UpfrontPayment : ScamReason()

    data class BlacklistedContact(
        val contactValue: String
    ) : ScamReason()

    data class LlmFlag(
        val rationale: String
    ) : ScamReason()

    data object StreetViewMismatch : ScamReason()

    data object AbroadLandlord : ScamReason()
}