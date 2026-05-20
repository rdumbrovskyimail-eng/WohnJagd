package com.wohnjagd.app.domain.model

import kotlin.math.abs

/**
 * Денежное значение, хранится в центах (Long) для точности.
 * Все операции типа-безопасны.
 */
@JvmInline
value class Money(val cents: Long) : Comparable<Money> {

    operator fun plus(other: Money): Money = Money(cents + other.cents)
    operator fun minus(other: Money): Money = Money(cents - other.cents)
    operator fun times(factor: Int): Money = Money(cents * factor)
    operator fun times(factor: Long): Money = Money(cents * factor)
    operator fun times(factor: Double): Money = Money((cents * factor).toLong())
    operator fun div(divisor: Int): Money = Money(cents / divisor)
    operator fun unaryMinus(): Money = Money(-cents)

    override fun compareTo(other: Money): Int = cents.compareTo(other.cents)

    val isZero: Boolean get() = cents == 0L
    val isPositive: Boolean get() = cents > 0L
    val isNegative: Boolean get() = cents < 0L

    fun toDecimal(): Double = cents / 100.0

    /**
     * Форматирование в немецком стиле: "450 €" или "1234,56 €".
     */
    fun format(showCents: Boolean = false, currency: String = "€"): String {
        val absCents = abs(cents)
        val sign = if (cents < 0L) "-" else ""
        val euros = absCents / 100
        val cts = absCents % 100
        return if (showCents) {
            "$sign$euros,${cts.toString().padStart(2, '0')} $currency"
        } else {
            "$sign$euros $currency"
        }
    }

    companion object {
        val ZERO: Money = Money(0L)

        fun euros(amount: Int): Money = Money(amount * 100L)
        fun euros(amount: Long): Money = Money(amount * 100L)
        fun cents(amount: Long): Money = Money(amount)
        fun fromDecimal(decimal: Double): Money = Money((decimal * 100).toLong())
    }
}