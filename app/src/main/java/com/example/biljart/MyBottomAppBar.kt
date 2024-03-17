package com.example.biljart

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.ui.theme.BilliardTheme

@Composable
fun MyBottomAppBar(
    onHome: () -> Unit,
    onAbout: () -> Unit,
    onRanking: () -> Unit,
    onPlayingDays: () -> Unit,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        actions = {
            // Add actions here
            IconButton(onClick = onHome) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = onAbout) {
                Icon(Icons.Filled.Info, contentDescription = "About")
            }
            IconButton(onClick = onRanking) {
                Icon(Icons.Filled.Star, contentDescription = "Ranking")
            }
            IconButton(onClick = onPlayingDays) {
                Icon(Icons.Filled.Menu, contentDescription = "Playingdays")
            }
        },
    )
//    ) {
//        Text(
//            modifier = Modifier
//                .fillMaxWidth(),
//            textAlign = TextAlign.Center,
//            text = "Bottom app bar",
//        )
//    }
}

// preview
@Preview(showBackground = true)
@Composable
fun MyBottomAppBarPreview() {
    BilliardTheme(darkTheme = false) {
        MyBottomAppBar(
            { /*onHome*/ },
            { /*onAbout*/ },
            { /*onCompetition*/ },
            { /*onPlayingDays*/ },
        )
    }
}
