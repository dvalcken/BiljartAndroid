package com.example.biljart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.biljart.R

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
//            .border(dimensionResource(R.dimen.border_small), Color.Black)
            .padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Column {
            ElevatedCard(
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.padding_small),
                        dimensionResource(R.dimen.padding_extra_small),
                    )
                    .fillMaxWidth(),

//                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = stringResource(R.string.Ranking_player_id, player_id))
                Text(text = stringResource(R.string.Ranking_name, name))
                Text(text = stringResource(R.string.Ranking_rank, rank))
                Text(text = stringResource(R.string.Ranking_total_frames_won, total_frames_won))
                Text(text = stringResource(R.string.Ranking_total_frames_lost, total_frames_lost))
                Text(text = stringResource(R.string.Ranking_total_matches_won, total_matches_won))
                Text(
                    text = stringResource(
                        R.string.Ranking_total_matches_played,
                        total_matches_played,
                    ),
                )
            }
        }
    }
}
