package com.wohnjagd.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wohnjagd.app.presentation.feed.FeedScreen
import com.wohnjagd.app.presentation.map.MapScreen
import com.wohnjagd.app.presentation.profile.ProfileScreen
import com.wohnjagd.app.presentation.settings.SettingsScreen
import com.wohnjagd.app.presentation.tracker.TrackerScreen

@Composable
fun WohnJagdNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            WohnJagdBottomBar(
                currentRoute = currentRoute,
                onNavigate = { destination ->
                    if (destination.route != currentRoute) {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Feed.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Feed.route) { FeedScreen() }
            composable(Destination.Map.route) { MapScreen() }
            composable(Destination.Tracker.route) { TrackerScreen() }
            composable(Destination.Profile.route) { ProfileScreen() }
            composable(Destination.Settings.route) { SettingsScreen() }
        }
    }
}