package com.wohnjagd.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Feed : Destination("feed", "Feed", Icons.Outlined.Home)
    data object Map : Destination("map", "Karte", Icons.Outlined.Map)
    data object Tracker : Destination("tracker", "Tracker", Icons.Outlined.Bookmark)
    data object Profile : Destination("profile", "Profil", Icons.Outlined.Person)
    data object Settings : Destination("settings", "Einstellungen", Icons.Outlined.Settings)

    companion object {
        val bottomNavItems: List<Destination> =
            listOf(Feed, Map, Tracker, Profile, Settings)
    }
}