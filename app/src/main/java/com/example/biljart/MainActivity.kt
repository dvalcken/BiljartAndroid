package com.example.biljart

import android.os.Bundle // ktlint-disable import-ordering
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.biljart.ui.theme.BilliardTheme

enum class Destinations {
    Home,
    About,
    Ranking,
}

class MainActivity : ComponentActivity() {
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
                        appName = getString(R.string.app_name), // app name is declared in strings.xml
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BilliardApp(toggleTheme: () -> Unit, appName: String) {
    val navController = rememberNavController() // hoisted the scope via ctrl+alt+v (lesson 3, 1:38:10)
    Scaffold(
        topBar = {
            MyTopAppBar(
                appName,
                toggleTheme,
            ) {
                val currentBackStackEntry by navController.currentBackStackEntryAsState() // lesson 3, 1:55:00
                val isStartDestination = currentBackStackEntry?.destination?.route == Destinations.Home.name
                if (isStartDestination) {
                    IconButton(onClick = { /* do nothing for now */ }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                } else {
                    IconButton(onClick = { navController.popBackStack() }) { // onUp function for the back icon
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        },
        bottomBar = {
            MyBottomAppBar(
                { navController.popBackStack(Destinations.Home.name, false) },
                // popBackStack pops the back stack until the destination is found
                // inclusive is false, so the destination itself is not popped
                { navController.navigate(Destinations.About.name) },
                { navController.navigate(Destinations.Ranking.name) },
            )
        },
        /*        floatingActionButton = {
                    FloatingActionButton(onClick = { presses++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },*/
    ) { innerPadding -> // without innerPadding, the content will be placed at the top of the screen, so behind the top app bar
        NavHost(
            navController = navController,
            startDestination = Destinations.Home.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Destinations.Home.name) {
                // composable is an extension function on NavGraphBuilder (lesson 3, 1:13:30)
                StartScreen()
            }
            composable(Destinations.About.name) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(), // fill the available space.
                    verticalArrangement = Arrangement.Center, // center the content vertically.
                    horizontalAlignment = Alignment.CenterHorizontally, // center the content horizontally.
                ) {
                    Text(text = "Temporary about screen")
                }
            }
            composable(Destinations.Ranking.name) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(), // fill the available space.
                    verticalArrangement = Arrangement.Center, // center the content vertically.
                    horizontalAlignment = Alignment.CenterHorizontally, // center the content horizontally.
                ) {
                    Text(text = "Temporary competition screen")
                }
            }
        }
    }
}
