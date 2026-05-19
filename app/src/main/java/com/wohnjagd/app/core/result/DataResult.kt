package com.wohnjagd.app.core.result

/**
 * Sealed wrapper для результатов операций data-слоя.
 * Используется вместо exceptions для бизнес-ошибок.
 */
sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Failure(val error: AppError) : DataResult<Nothing>()

    inline fun <R> map(transform: (T) -> R): DataResult<R> = when (this) {
        is Success -> Success(transform(data))
        is Failure -> this
    }

    inline fun <R> flatMap(transform: (T) -> DataResult<R>): DataResult<R> = when (this) {
        is Success -> transform(data)
        is Failure -> this
    }

    inline fun onSuccess(action: (T) -> Unit): DataResult<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (AppError) -> Unit): DataResult<T> {
        if (this is Failure) action(error)
        return this
    }

    fun getOrNull(): T? = (this as? Success)?.data

    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure

    companion object {
        inline fun <T> runCatching(block: () -> T): DataResult<T> = try {
            Success(block())
        } catch (t: Throwable) {
            Failure(AppError.Unknown(t.message ?: "Unknown error", t))
        }
    }
}