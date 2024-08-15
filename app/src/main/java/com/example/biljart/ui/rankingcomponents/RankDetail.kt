package com.example.biljart.ui.rankingcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R

@Composable
fun RankDetail(
    playerId: Int,
    name: String,
    rank: Int,
    totalFramesWon: Int,
    totalFramesLost: Int,
    totalMatchesWon: Int,
    totalMatchesPlayed: Int,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.player_details, name)) },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.Ranking_player_id, playerId),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.Ranking_rank, rank),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.Ranking_total_frames_won, totalFramesWon),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.Ranking_total_frames_lost, totalFramesLost),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.Ranking_total_matches_won, totalMatchesWon),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.Ranking_total_matches_played, totalMatchesPlayed),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.ok))
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRankDetail() {
    MaterialTheme {
        RankDetail(
            playerId = 1,
            name = "John Doe",
            rank = 1,
            totalFramesWon = 10,
            totalFramesLost = 2,
            totalMatchesWon = 5,
            totalMatchesPlayed = 6,
            onDismiss = {},
        )
    }
}
