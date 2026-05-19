package com.wohnjagd.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wohnjagd.app.presentation.navigation.WohnJagdNavHost
import com.wohnjagd.app.presentation.theme.WohnJagdTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            WohnJagdTheme {
                WohnJagdNavHost()
            }
        }
    }
}