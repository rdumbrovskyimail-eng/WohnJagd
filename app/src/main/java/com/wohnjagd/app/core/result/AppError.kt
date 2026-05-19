package com.wohnjagd.app.core.result

/**
 * Sealed hierarchy всех ошибок, которые могут происходить в data-слое.
 * Каждый класс отражает класс проблемы, который UI может обрабатывать по-разному.
 */
sealed class AppError(
    open val message: String,
    open val cause: Throwable? = null
) {

    data class Network(
        override val message: String,
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class Parse(
        override val message: String,
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class RateLimited(
        val retryAfterMs: Long?,
        override val message: String = "Rate limited",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class AuthRequired(
        override val message: String = "Authentication required",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class Banned(
        override val message: String = "Banned by source",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class NotFound(
        override val message: String = "Not found",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class Validation(
        override val message: String,
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class Unknown(
        override val message: String,
        override val cause: Throwable? = null
    ) : AppError(message, cause)
}