package com.wohnjagd.app.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WohnJagdBottomBar(
    currentRoute: String?,
    onNavigate: (Destination) -> Unit
) {
    NavigationBar {
        Destination.bottomNavItems.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label
                    )
                },
                label = { Text(destination.label) },
                alwaysShowLabel = true
            )
        }
    }
}