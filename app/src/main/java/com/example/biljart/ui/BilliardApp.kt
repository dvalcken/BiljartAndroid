package com.example.biljart.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.biljart.Destinations
import com.example.biljart.R
import com.example.biljart.ui.playingdaycomponents.PlayingDayOverview
import com.example.biljart.ui.rankingcomponents.RankingOverview
import com.example.biljart.ui.theme.BilliardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BilliardApp(toggleTheme: () -> Unit, navController: NavHostController = rememberNavController()/*appName: String,*/) {
//    val navController: NavHostController =
//        rememberNavController() // hoisted the scope via ctrl+alt+v (lesson 3, 1:38:10) // moved to parameter for testing (Lesson 5 0:57:00)
    val currentBackStackEntry by navController.currentBackStackEntryAsState() // lesson 3, 1:55:00
    val route = currentBackStackEntry?.destination?.route // lesson 3, 1:55:00

    val titleResId = when (route) {
        Destinations.Home.name -> R.string.home_title
        Destinations.About.name -> R.string.about_title
        Destinations.Ranking.name -> R.string.ranking_title
        Destinations.PlayingDays.name -> R.string.playing_days_title
        else -> R.string.app_name // Default title is the app name
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
//                appName,
                toggleTheme,
                title = titleResId,
            ) {
                val isStartDestination = route == Destinations.Home.name
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
                { navController.popBackStack(Destinations.Home.name, false) }, // onHome
                // popBackStack pops the back stack until the destination is found
                // inclusive is false, so the destination itself is not popped
                { navController.navigate(Destinations.About.name) }, // onAbout
                { navController.navigate(Destinations.Ranking.name) }, // onRanking
                { navController.navigate(Destinations.PlayingDays.name) }, // onPlayingDays
            )
        },
        floatingActionButton = {
            val context =
                LocalContext.current // context is needed to start an activity, in this case to open the email app in the about screen
            when (route) {
                Destinations.About.name -> {
                    // open email app and sender is filled in with valckenierdimitri@hotmail.com
                    FloatingActionButton(onClick = {
                        val emailIntent =
                            Intent(Intent.ACTION_SENDTO).apply { // Intent is used to start an activity.
                                data =
                                    Uri.parse("mailto:valckenierdimitri@hotmail.com") // use a URI with the "mailto:" scheme to specify the email recipient
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    "Email from billiard app on Android",
                                ) // fill in the subject of the email
                            }
                        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
                    }) {
                        Icon(Icons.Default.Email, contentDescription = "Email the developer")
                    }
                }
            }
        },
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
                    Text(text = "Temporary about screen", style = MaterialTheme.typography.bodyMedium)
                }
            }
            composable(Destinations.Ranking.name) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(), // fill the available space.
                    verticalArrangement = Arrangement.Center, // center the content vertically.
                    horizontalAlignment = Alignment.CenterHorizontally, // center the content horizontally.
                ) {
                    // Text(text = "Temporary competition screen")
                    RankingOverview()
                }
            }
            composable(Destinations.PlayingDays.name) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(), // fill the available space.
                    verticalArrangement = Arrangement.Center, // center the content vertically.
                    horizontalAlignment = Alignment.CenterHorizontally, // center the content horizontally.
                ) {
                    // Text(text = "Temporary competition screen")
                    PlayingDayOverview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BilliardAppPreview() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) } // Save theme state across config changes like landscape
    BilliardTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            BilliardApp(
                toggleTheme = { isDarkTheme = !isDarkTheme },
            )
        }
    }
}
