package com.wohnjagd.app.domain.model

import java.time.Instant
import java.time.LocalDate

/**
 * Профиль пользователя — основа для генерации Anschreiben и Bewerbungsmappe.
 */
data class MieterProfile(
    val fullName: String = "",
    val birthDate: LocalDate? = null,
    val nationality: String? = null,
    val profession: String = "",
    val employmentStatus: EmploymentStatus = EmploymentStatus.UNKNOWN,
    val monthlyIncome: Money = Money.ZERO,
    val incomeSource: String = "",

    val household: HouseholdInfo = HouseholdInfo(),
    val references: ReferencesInfo = ReferencesInfo(),
    val preferences: SearchPreferences = SearchPreferences(),

    val aboutMe: String = "",
    val photoPath: String? = null,
    val documents: List<BewerbungDocument> = emptyList(),

    val updatedAt: Instant? = null
) {
    val isComplete: Boolean
        get() = fullName.isNotBlank() &&
                profession.isNotBlank() &&
                employmentStatus != EmploymentStatus.UNKNOWN
}

enum class EmploymentStatus(val germanLabel: String) {
    BUERGERGELD("Bürgergeld"),
    EMPLOYED("Angestellt"),
    SELF_EMPLOYED("Selbstständig"),
    FREELANCE("Freiberuflich"),
    STUDENT("Student"),
    RETIRED("Rentner"),
    UNEMPLOYED("Arbeitslos"),
    UNKNOWN("Unbekannt")
}

data class HouseholdInfo(
    val adults: Int = 1,
    val children: Int = 0,
    val hasPets: Boolean = false,
    val petDetails: String? = null,
    val smokers: Int = 0,
    val musicalInstruments: Boolean = false
)

data class ReferencesInfo(
    val previousLandlord: String? = null,
    val mietschuldenfrei: Boolean = false,
    val schufaScore: Int? = null,
    val schufaDate: LocalDate? = null
)

data class SearchPreferences(
    val earliestMoveIn: LocalDate? = null,
    val latestMoveIn: LocalDate? = null,
    val minRentDurationMonths: Int? = null
)