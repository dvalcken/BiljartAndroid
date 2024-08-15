package com.example.biljart.ui.rankingcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
                RankDetailItem(
                    label = stringResource(R.string.Player_id),
                    value = playerId.toString(),
                )
                RankDetailItem(
                    label = stringResource(R.string.Rank),
                    value = rank.toString(),
                )
                RankDetailItem(
                    label = stringResource(R.string.Total_frames_won),
                    value = totalFramesWon.toString(),
                )
                RankDetailItem(
                    label = stringResource(R.string.Total_frames_lost),
                    value = totalFramesLost.toString(),
                )
                RankDetailItem(
                    label = stringResource(R.string.Total_matches_won),
                    value = totalMatchesWon.toString(),
                )
                RankDetailItem(
                    label = stringResource(R.string.Total_matches_played),
                    value = totalMatchesPlayed.toString(),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.ok),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
    )
}

@Composable
fun RankDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_extra_small)),
    ) {
        Row {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.primary,
//                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_extra_small)))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
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
