package com.example.biljart.ui.rankingcomponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.biljart.R
import com.example.biljart.model.Player

@Composable
fun RankingTableLayout(players: List<Player>) {
    LazyColumn(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        item {
            HeaderRow()
        }
        items(players) { rank ->
            RankRow(rank)
        }
    }
}

@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small)),
    ) {
        val color = MaterialTheme.colorScheme.primary
        val style = MaterialTheme.typography.titleMedium
        Text(stringResource(R.string.nr), modifier = Modifier.weight(0.5f), style = style, color = color)
        // Modifier.weight is used to distribute the space between the columns, so that the columns are of equal width in header and rows
        Text(stringResource(R.string.player), modifier = Modifier.weight(2f), style = style, color = color)
        Text(stringResource(R.string.frames_won), modifier = Modifier.weight(1f), style = style, color = color)
        Text(stringResource(R.string.frames_lost), modifier = Modifier.weight(1f), style = style, color = color)
    }
}

@Composable
fun RankRow(player: Player) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small))
            .border(dimensionResource(R.dimen.border_extrasmall), MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(dimensionResource(R.dimen.cornerradius_small)))
            .padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Text(text = player.rank.toString(), modifier = Modifier.weight(0.5f))
        Text(text = player.name, modifier = Modifier.weight(2f))
        Text(text = player.totalFramesWon.toString(), modifier = Modifier.weight(1f))
        Text(text = player.totalFramesLost.toString(), modifier = Modifier.weight(1f))
    }
}
