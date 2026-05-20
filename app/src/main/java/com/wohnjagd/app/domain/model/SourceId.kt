package com.wohnjagd.app.domain.model

/**
 * Идентификатор источника объявлений. Значение — стабильный slug.
 * Константы пополняются по мере добавления коннекторов.
 */
@JvmInline
value class SourceId(val value: String) {
    companion object {
        // Portals
        val IMMOSCOUT24 = SourceId("immoscout24")
        val IMMOWELT = SourceId("immowelt")
        val IMMONET = SourceId("immonet")
        val KLEINANZEIGEN = SourceId("kleinanzeigen")
        val WG_GESUCHT = SourceId("wg_gesucht")
        val IMMOBILIEN_DE = SourceId("immobilien_de")
        val WOHNUNGSBOERSE = SourceId("wohnungsboerse")
        val WOHNUNGSMARKT24 = SourceId("wohnungsmarkt24")
        val QUOKA = SourceId("quoka")
        val MEINESTADT = SourceId("meinestadt")

        // Regional
        val KALAYDO = SourceId("kalaydo")
        val IMMOSUCHMASCHINE = SourceId("immosuchmaschine")
        val GENERAL_ANZEIGER = SourceId("general_anzeiger")
        val K_STA = SourceId("k_sta")
        val RHEIN_SIEG_ANZEIGER = SourceId("rhein_sieg_anzeiger")
        val WOCHENANZEIGER = SourceId("wochenanzeiger")
        val EXTRA_BLATT_EITORF = SourceId("extra_blatt_eitorf")

        // Big landlords
        val VONOVIA = SourceId("vonovia")
        val LEG = SourceId("leg")
        val VIVAWEST = SourceId("vivawest")
        val DEUTSCHE_WOHNEN = SourceId("deutsche_wohnen")
        val GAG_KOELN = SourceId("gag_koeln")
        val ADLER_GROUP = SourceId("adler_group")

        // Genossenschaften Rhein-Sieg
        val GWG_TROISDORF = SourceId("gwg_troisdorf")
        val GWG_RHEIN_SIEG = SourceId("gwg_rhein_sieg")
        val GBG_SIEGBURG = SourceId("gbg_siegburg")
        val GWG_BONN = SourceId("gwg_bonn")
        val SPAR_BAUVEREIN_BONN = SourceId("spar_bauverein_bonn")
        val SIEGBURGER_BAUVEREIN = SourceId("siegburger_bauverein")
        val BAUVEREIN_BEUEL = SourceId("bauverein_beuel")
        val WOHNBAU_RHEIN_SIEG = SourceId("wohnbau_rhein_sieg")
        val VEBOWAG = SourceId("vebowag")
        val BAUGENOSSENSCHAFT_RHEIDT = SourceId("baugenossenschaft_rheidt")

        // Social
        val TELEGRAM = SourceId("telegram")
        val FACEBOOK_GROUP = SourceId("facebook_group")
        val FACEBOOK_MARKETPLACE = SourceId("facebook_marketplace")
        val INSTAGRAM = SourceId("instagram")
        val REDDIT = SourceId("reddit")
        val DISCORD = SourceId("discord")
        val WHATSAPP_WEB = SourceId("whatsapp_web")

        // Other
        val EMAIL_IMAP = SourceId("email_imap")
        val RSS = SourceId("rss")
        val BOARD_OCR = SourceId("board_ocr")
        val ZVG_PORTAL = SourceId("zvg_portal")
    }
}