package com.example.biljart.ui.rankingcomponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.example.biljart.R

@Composable
fun RankItem(
    playerId: Int,
    name: String,
    rank: Int,
    totalFramesWon: Int,
    totalFramesLost: Int,
    totalMatchesWon: Int,
    totalMatchesPlayed: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
//            .border(dimensionResource(R.dimen.border_small), Color.Black)
            .padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Column {
            var expanded by rememberSaveable {
                mutableStateOf(false)
            }
            val color by animateColorAsState(
                targetValue = if (expanded) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                },
                label = "colorAnimation",
            )

            ElevatedCard(
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.padding_small),
                        dimensionResource(R.dimen.padding_extra_small),
                    )
                    .fillMaxWidth()
                    .background(color),

//                shape = MaterialTheme.shapes.medium,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.Ranking_player_id, playerId),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = "Expand",
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.Ranking_name, name),
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (expanded) null else TextDecoration.Underline,
                )
                Text(
                    text = stringResource(R.string.Ranking_rank, rank),
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (expanded) {
                    Text(
                        text = stringResource(R.string.Ranking_total_frames_won, totalFramesWon),
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Text(
                        text = stringResource(
                            R.string.Ranking_total_frames_lost,
                            totalFramesLost,
                        ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(
                            R.string.Ranking_total_matches_won,
                            totalMatchesWon,
                        ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(
                            R.string.Ranking_total_matches_played,
                            totalMatchesPlayed,
                        ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
