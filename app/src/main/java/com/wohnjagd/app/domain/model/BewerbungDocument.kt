package com.wohnjagd.app.domain.model

import java.time.Instant

/**
 * Документ, прикреплённый к MieterProfile (для Bewerbungsmappe).
 */
data class BewerbungDocument(
    val type: DocumentType,
    val localPath: String,
    val displayName: String,
    val addedAt: Instant
)

/**
 * Стандартный набор документов для немецкой Bewerbungsmappe.
 */
enum class DocumentType(val germanLabel: String) {
    PERSONALAUSWEIS("Personalausweis"),
    ANMELDEBESCHEINIGUNG("Anmeldebescheinigung"),
    SCHUFA_AUSKUNFT("SCHUFA-Auskunft"),
    KONTOAUSZUG("Kontoauszug"),
    GEHALTSABRECHNUNG("Gehaltsabrechnung"),
    JC_BESCHEID("Jobcenter-Bescheid"),
    MIETSCHULDENFREIHEIT("Mietschuldenfreiheitsbescheinigung"),
    SELBSTAUSKUNFT("Selbstauskunft"),
    PASSFOTO("Passfoto"),
    OTHER("Sonstiges")
}