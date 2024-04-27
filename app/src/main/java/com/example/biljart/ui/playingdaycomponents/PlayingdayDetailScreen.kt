package com.example.biljart.ui.playingdaycomponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PlayingdayDetailScreen(playingdayId: Int) {
    // Detail view implementation
    /* TODO: Implement the detail view for a playing day, by showing the list of matches */

    // show a text with the playingdayId
    Text(text = "Playingday ID: $playingdayId", style = MaterialTheme.typography.titleMedium)
}
