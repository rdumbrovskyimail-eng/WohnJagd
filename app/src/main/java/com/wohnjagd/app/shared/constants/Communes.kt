package com.wohnjagd.app.shared.constants

import com.wohnjagd.app.domain.model.Commune
import com.wohnjagd.app.domain.model.CommuneId
import com.wohnjagd.app.domain.model.Money

/**
 * Справочник 19 коммун Rhein-Sieg-Kreis с лимитами Kaltmiete для 1 человека
 * по Angemessenheitsrichtlinien Jobcenter Rhein-Sieg (Anlage 1 zur Weisung 48).
 *
 * Лимиты периодически обновляются — при изменении руками править здесь.
 */
object Communes {

    val ALL: Map<CommuneId, Commune> = mapOf(
        CommuneId.ALFTER to Commune(CommuneId.ALFTER, "Alfter", Money.euros(550)),
        CommuneId.BAD_HONNEF to Commune(CommuneId.BAD_HONNEF, "Bad Honnef", Money.euros(510)),
        CommuneId.BORNHEIM to Commune(CommuneId.BORNHEIM, "Bornheim", Money.euros(550)),
        CommuneId.EITORF to Commune(CommuneId.EITORF, "Eitorf", Money.euros(450)),
        CommuneId.HENNEF to Commune(CommuneId.HENNEF, "Hennef", Money.euros(520)),
        CommuneId.KOENIGSWINTER to Commune(CommuneId.KOENIGSWINTER, "Königswinter", Money.euros(530)),
        CommuneId.LOHMAR to Commune(CommuneId.LOHMAR, "Lohmar", Money.euros(530)),
        CommuneId.MECKENHEIM to Commune(CommuneId.MECKENHEIM, "Meckenheim", Money.euros(510)),
        CommuneId.MUCH to Commune(CommuneId.MUCH, "Much", Money.euros(450)),
        CommuneId.NEUNKIRCHEN_SEELSCHEID to Commune(
            CommuneId.NEUNKIRCHEN_SEELSCHEID,
            "Neunkirchen-Seelscheid",
            Money.euros(530)
        ),
        CommuneId.NIEDERKASSEL to Commune(CommuneId.NIEDERKASSEL, "Niederkassel", Money.euros(550)),
        CommuneId.RHEINBACH to Commune(CommuneId.RHEINBACH, "Rheinbach", Money.euros(490)),
        CommuneId.RUPPICHTEROTH to Commune(CommuneId.RUPPICHTEROTH, "Ruppichteroth", Money.euros(450)),
        CommuneId.SANKT_AUGUSTIN to Commune(CommuneId.SANKT_AUGUSTIN, "Sankt Augustin", Money.euros(550)),
        CommuneId.SIEGBURG to Commune(CommuneId.SIEGBURG, "Siegburg", Money.euros(550)),
        CommuneId.SWISTTAL to Commune(CommuneId.SWISTTAL, "Swisttal", Money.euros(490)),
        CommuneId.TROISDORF to Commune(CommuneId.TROISDORF, "Troisdorf", Money.euros(550)),
        CommuneId.WACHTBERG to Commune(CommuneId.WACHTBERG, "Wachtberg", Money.euros(510)),
        CommuneId.WINDECK to Commune(CommuneId.WINDECK, "Windeck", Money.euros(450))
    )

    /** Все коммуны, отсортированные по displayName. */
    val asList: List<Commune> = ALL.values.sortedBy { it.displayName }

    /** Возвращает коммуну по id, бросает если id неизвестен. */
    fun byId(id: CommuneId): Commune = ALL.getValue(id)

    /** Возвращает коммуну по id, null если id неизвестен или null. */
    fun byIdOrNull(id: CommuneId?): Commune? = id?.let(ALL::get)

    /**
     * Максимально допустимая Kaution для коммуны (3 × Kaltmiete-лимит).
     * По правилам JC Rhein-Sieg.
     */
    fun maxKautionFor(id: CommuneId): Money = byId(id).maxKaltmiete * 3
}