package com.example.biljart.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RankItem(
    player_id: Int,
    name: String,
    rank: Int,
    total_frames_won: Int,
    total_frames_lost: Int,
    total_matches_won: Int,
    total_matches_played: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Player ID: $player_id")
            Text(text = "Name: $name")
            Text(text = "Rank: $rank")
            Text(text = "Total frames won: $total_frames_won")
            Text(text = "Total frames lost: $total_frames_lost")
            Text(text = "Total matches won: $total_matches_won")
            Text(text = "Total matches played: $total_matches_played")
        }
    }
}
