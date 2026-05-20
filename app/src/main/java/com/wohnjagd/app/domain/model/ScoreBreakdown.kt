package com.wohnjagd.app.domain.model

import java.time.Instant

/**
 * Разбивка скоринга по 5 категориям.
 * Все значения от 0 до 100. Total — взвешенная сумма.
 */
data class ScoreBreakdown(
    val total: Float,
    val realism: Float,
    val money: Float,
    val geo: Float,
    val quality: Float,
    val strategy: Float,
    val calculatedAt: Instant
) {
    val level: ScoreLevel
        get() = when {
            total >= 90f -> ScoreLevel.HOT
            total >= 70f -> ScoreLevel.WARM
            total >= 50f -> ScoreLevel.COLD
            else -> ScoreLevel.ARCHIVE
        }
}

/**
 * Уровень listing'а для приоритизации уведомлений.
 */
enum class ScoreLevel {
    HOT,
    WARM,
    COLD,
    ARCHIVE
}