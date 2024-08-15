package com.example.biljart.ui.rankingcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R

@Composable
fun RankListItem(
    playerId: Int,
    name: String,
    rank: Int,
    totalFramesWon: Int,
    totalFramesLost: Int,
    totalMatchesWon: Int,
    totalMatchesPlayed: Int,
    modifier: Modifier = Modifier,
) {
    var showDetail by remember { mutableStateOf(false) }

    if (showDetail) {
        RankDetail(
            playerId = playerId,
            name = name,
            rank = rank,
            totalFramesWon = totalFramesWon,
            totalFramesLost = totalFramesLost,
            totalMatchesWon = totalMatchesWon,
            totalMatchesPlayed = totalMatchesPlayed,
            onDismiss = { showDetail = false },
        )
    }

    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.cornerradius_small)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = dimensionResource(R.dimen.elevation_small)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$rank. $name",
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))

                Row {
                    Text(
                        text = stringResource(
                            R.string.frames_won_lost,
                            totalFramesWon,
                            totalFramesLost,
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

//                Row {
//                    Text(
//                        text = stringResource(R.string.Ranking_total_frames_won, totalFramesWon),
//                        style = MaterialTheme.typography.bodyLarge,
//                    )
//                }
//                Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))
//                Row {
//                    Text(
//                        text = stringResource(R.string.Ranking_total_frames_lost, totalFramesLost),
//                        style = MaterialTheme.typography.bodyLarge,
//                    )
//                }
            }
            IconButton(onClick = { showDetail = true }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.view_details),
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_medium)),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRankListItem() {
    MaterialTheme {
        RankListItem(
            playerId = 1,
            name = "John Doe",
            rank = 1,
            totalFramesWon = 10,
            totalFramesLost = 2,
            totalMatchesWon = 5,
            totalMatchesPlayed = 6,
        )
    }
}
