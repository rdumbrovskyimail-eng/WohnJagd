package com.wohnjagd.app.core.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Добавляет realistic браузерные заголовки к каждому запросу.
 * Без этого Kleinanzeigen / ImmoScout / другие сайты с anti-bot защитой
 * вернут 403 или CAPTCHA на дефолтный OkHttp UA.
 */
@Singleton
class UserAgentInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val withHeaders = original.newBuilder()
            .header("User-Agent", USER_AGENT)
            .header(
                "Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"
            )
            .header("Accept-Language", "de-DE,de;q=0.9,en;q=0.8")
            .header("Accept-Encoding", "gzip, deflate, br")
            .header("DNT", "1")
            .header("Upgrade-Insecure-Requests", "1")
            .build()
        return chain.proceed(withHeaders)
    }

    companion object {
        // Mobile Chrome — выглядит максимально естественно для Samsung-устройства
        private const val USER_AGENT =
            "Mozilla/5.0 (Linux; Android 14; SM-S918B) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36"
    }
}