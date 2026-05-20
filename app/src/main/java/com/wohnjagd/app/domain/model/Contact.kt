package com.wohnjagd.app.domain.model

import java.util.UUID

/**
 * Идентификатор контакта (арендодатель / Hausverwaltung / Makler).
 */
@JvmInline
value class ContactId(val value: String) {
    companion object {
        fun new(): ContactId = ContactId(UUID.randomUUID().toString())
    }
}

/**
 * Контактные данные. Один контакт может быть связан с N listings.
 */
data class Contact(
    val id: ContactId,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val organization: String? = null,
    val landlordType: LandlordType = LandlordType.UNKNOWN,
    val notes: String? = null
) {
    val hasAnyContactInfo: Boolean
        get() = !email.isNullOrBlank() || !phone.isNullOrBlank()
}