package com.wohnjagd.app.core.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * In-memory cookie storage по хостам.
 * Cookies живут только в течение запуска приложения.
 * Достаточно для Cloudflare/anti-bot challenge cookies внутри одной сессии.
 * Persistent cookies (на диск) добавим в Phase 4+ при необходимости.
 */
@Singleton
class InMemoryCookieJar @Inject constructor() : CookieJar {

    private val store: MutableMap<String, MutableList<Cookie>> = ConcurrentHashMap()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val host = url.host
        val now = System.currentTimeMillis()
        val cookies = store[host] ?: return emptyList()
        // Отфильтровываем просроченные
        val valid = cookies.filter { it.expiresAt > now }
        if (valid.size != cookies.size) {
            store[host] = valid.toMutableList()
        }
        return valid
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies.isEmpty()) return
        val host = url.host
        val existing = store.getOrPut(host) { mutableListOf() }
        for (newCookie in cookies) {
            // Удаляем cookie с таким же именем (replace)
            existing.removeAll { it.name == newCookie.name }
            existing.add(newCookie)
        }
    }
}