package com.example.biljart

import android.os.Bundle // ktlint-disable import-ordering
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.biljart.ui.BilliardApp
import com.example.biljart.ui.theme.BilliardTheme

enum class Destinations {
    Home,
    About,
    Ranking,
    PlayingDays,
}

class MainActivity : ComponentActivity() { // this is the main activity of the app (lesson 4, 0:36:30)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) } // Save theme state across config changes like landscape
            BilliardTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BilliardApp(
                        toggleTheme = { isDarkTheme = !isDarkTheme },
//                        appName = getString(R.string.app_name), // app name is declared in strings.xml
                    )
                }
            }
        }
    }
}

