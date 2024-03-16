package com.example.biljart

import android.os.Bundle // ktlint-disable import-ordering
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.biljart.ui.theme.BilliardTheme

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
                        appName = getString(R.string.app_name), // Assuming you have defined the app name in your strings.xml
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BilliardApp(toggleTheme: () -> Unit, appName: String) {
    Scaffold(
        topBar = {
            MyTopAppBar(appName, toggleTheme)
        },
        bottomBar = {
            MyBottomAppBar()
        },
        /*        floatingActionButton = {
                    FloatingActionButton(onClick = { presses++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },*/
    ) { innerPadding -> // without innerPadding, the content will be placed at the top of the screen, so behind the top app bar
        StartScreen(innerPadding)
    }
}

// @Composable
// fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = name,
//        color = MaterialTheme.colorScheme.primary,
//        style = MaterialTheme.typography.displayLarge,
//        modifier = modifier,
//    )
// }

// @Preview(showBackground = true)
// @Composable
// fun GreetingPreview() {
//    BilliardTheme {
//        Greeting("Android")
//    }
// }
