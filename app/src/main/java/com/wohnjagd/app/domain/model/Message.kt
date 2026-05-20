package com.wohnjagd.app.domain.model

import java.time.Instant
import java.util.UUID

/**
 * Идентификатор сообщения переписки.
 */
@JvmInline
value class MessageId(val value: String) {
    companion object {
        fun new(): MessageId = MessageId(UUID.randomUUID().toString())
    }
}

/**
 * Одно сообщение в переписке с арендодателем.
 * Может быть привязано к Application или к Contact напрямую (если ещё нет Application).
 */
data class Message(
    val id: MessageId,
    val applicationId: ApplicationId? = null,
    val contactId: ContactId? = null,
    val direction: MessageDirection,
    val channel: MessageChannel,
    val subject: String? = null,
    val body: String,
    val attachments: List<String> = emptyList(),
    val timestamp: Instant,
    val parsedActions: List<String> = emptyList()
)

enum class MessageDirection {
    OUTGOING,
    INCOMING
}

enum class MessageChannel(val germanLabel: String) {
    EMAIL("E-Mail"),
    SMS("SMS"),
    PHONE("Telefon"),
    PORTAL("Portal"),
    OTHER("Andere")
}