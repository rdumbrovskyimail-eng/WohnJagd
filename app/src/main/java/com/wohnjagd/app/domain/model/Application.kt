package com.wohnjagd.app.domain.model

import java.time.Instant
import java.util.UUID

/**
 * Идентификатор отклика (Application = единица CRM-трекера).
 */
@JvmInline
value class ApplicationId(val value: String) {
    companion object {
        fun new(): ApplicationId = ApplicationId(UUID.randomUUID().toString())
    }
}

/**
 * CRM-запись: один отклик на одну квартиру.
 * Жизненный цикл: FOUND → APPLIED → REPLIED → BESICHTIGUNG → ... → CONTRACT_SIGNED.
 */
data class Application(
    val id: ApplicationId,
    val listingId: ListingId,
    val status: ApplicationStatus,
    val appliedAt: Instant? = null,
    val anschreibenSnapshot: AnschreibenSnapshot? = null,
    val bewerbungsmappePath: String? = null,
    val notes: List<TimestampedNote> = emptyList(),
    val nextAction: NextAction? = null,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    val isTerminal: Boolean get() = status.isTerminal
}

/**
 * Состояние отклика в pipeline.
 */
enum class ApplicationStatus(val germanLabel: String, val isTerminal: Boolean = false) {
    FOUND("Gefunden"),
    VIEWED("Angesehen"),
    INTERESTED("Interessiert"),
    APPLIED("Beworben"),
    CONFIRMED_RECEIVED("Empfang bestätigt"),
    REPLIED("Geantwortet"),
    BESICHTIGUNG_SCHEDULED("Besichtigung geplant"),
    BESICHTIGUNG_DONE("Besichtigung absolviert"),
    SELBSTAUSKUNFT_SENT("Selbstauskunft gesendet"),
    MIETANGEBOT_RECEIVED("Mietangebot erhalten"),
    CONTRACT_SIGNED("Vertrag unterschrieben", isTerminal = true),
    REJECTED("Abgelehnt", isTerminal = true),
    WITHDREW("Zurückgezogen", isTerminal = true),
    EXPIRED("Abgelaufen", isTerminal = true)
}

/**
 * Снимок отправленного Anschreiben (для истории).
 */
data class AnschreibenSnapshot(
    val style: AnschreibenStyle,
    val text: String,
    val sentChannel: MessageChannel? = null,
    val sentAt: Instant? = null
)

/**
 * Заметка пользователя с меткой времени.
 */
data class TimestampedNote(
    val text: String,
    val createdAt: Instant
)

/**
 * Следующее запланированное действие по отклику.
 */
data class NextAction(
    val type: NextActionType,
    val scheduledAt: Instant,
    val description: String
)

enum class NextActionType(val germanLabel: String) {
    FOLLOW_UP("Nachfassen"),
    CALL("Anrufen"),
    EMAIL("E-Mail schreiben"),
    BESICHTIGUNG("Besichtigung"),
    SEND_DOCUMENTS("Unterlagen senden"),
    DECISION("Entscheidung"),
    OTHER("Sonstiges")
}