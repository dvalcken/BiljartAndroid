package com.example.biljart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R
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
            // row with arrangement 'Space Evenly'
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                IconButton(onClick = onHome) {
                    Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.bottombar_home))
                    // Example of changing the icon based on a condition
                    //  Icon(imageVector = if (true) Icons.Filled.Home else Icons.Filled.Face, contentDescription = "Bottombar Home")
                }
                //            Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = onAbout) {
                    Icon(Icons.Filled.Info, contentDescription = stringResource(R.string.bottombar_about))
                }
                //            Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = onRanking) {
                    Icon(Icons.Filled.Scoreboard, contentDescription = stringResource(R.string.bottombar_ranking))
                }
                //            Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = onPlayingDays) {
                    Icon(Icons.Filled.CalendarMonth, contentDescription = stringResource(R.string.bottombar_playingdays))
                }
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
