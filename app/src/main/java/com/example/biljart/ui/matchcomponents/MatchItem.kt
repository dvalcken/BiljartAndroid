package com.example.biljart.ui.matchcomponents

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R

@Composable
fun MatchItem(
    matchId: Int,
    player1: String,
    player2: String,
    player1FramesWon: Int?,
    player2FramesWon: Int?,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable { /* TODO open edit dialog action here */ },
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
                    text = stringResource(R.string.match, matchId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))

                val winnerStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                val normalStyle = MaterialTheme.typography.bodyLarge

                if (player1FramesWon != null && player2FramesWon != null) {
                    Text(
                        text = "$player1: $player1FramesWon",
                        style = if (player1FramesWon > player2FramesWon) winnerStyle else normalStyle,
                    )
                    Text(
                        text = "$player2: $player2FramesWon",
                        style = if (player1FramesWon < player2FramesWon) winnerStyle else normalStyle,
                    )
                } else {
                    Text(
                        text = stringResource(
                            R.string.player1_vs_player2_match_not_yet_played,
                            player1,
                            player2,
                        ),
                        style = normalStyle,
//                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }
            }
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_match),
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_medium)),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMatchItem() {
    MaterialTheme {
        MatchItem(
            matchId = 1,
            player1 = "John",
            player2 = "Jane",
            player1FramesWon = 6,
            player2FramesWon = 3,
            onEditClick = { /* Blanc for preview */ },
        )
    }
}
