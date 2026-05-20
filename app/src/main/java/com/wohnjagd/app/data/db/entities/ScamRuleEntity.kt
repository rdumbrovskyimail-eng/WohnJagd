package com.wohnjagd.app.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Правило для scam-detector'а.
 * matchType: "CONTAINS" / "REGEX" / "EXACT".
 * targetField: "TITLE" / "DESCRIPTION" / "CONTACT" / "PRICE_RATIO".
 * source: "BUILTIN" / "USER_LEARNED".
 */
@Entity(
    tableName = "scam_rules",
    indices = [Index(value = ["enabled"])]
)
data class ScamRuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val pattern: String,
    val matchType: String,
    val targetField: String,
    val scoreContribution: Float,
    val enabled: Boolean,
    val source: String,
    val createdAt: Long
)