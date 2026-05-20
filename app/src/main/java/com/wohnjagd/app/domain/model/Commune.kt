package com.wohnjagd.app.domain.model

/**
 * Идентификатор одной из 19 коммун Rhein-Sieg-Kreis.
 */
enum class CommuneId {
    ALFTER,
    BAD_HONNEF,
    BORNHEIM,
    EITORF,
    HENNEF,
    KOENIGSWINTER,
    LOHMAR,
    MECKENHEIM,
    MUCH,
    NEUNKIRCHEN_SEELSCHEID,
    NIEDERKASSEL,
    RHEINBACH,
    RUPPICHTEROTH,
    SANKT_AUGUSTIN,
    SIEGBURG,
    SWISTTAL,
    TROISDORF,
    WACHTBERG,
    WINDECK
}

/**
 * Описание коммуны: id, отображаемое имя, максимальная Kaltmiete по
 * Angemessenheitsrichtlinien Jobcenter Rhein-Sieg-Kreis для 1 человека.
 */
data class Commune(
    val id: CommuneId,
    val displayName: String,
    val maxKaltmiete: Money
)