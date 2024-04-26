package com.example.biljart.ui.playingdaycomponents

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.biljart.R

@Composable
fun PlayingdayItem(
    playingdayId: Int,
    date: String,
    isFinished: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .border(dimensionResource(R.dimen.border_small), Color.Black)
            .padding(dimensionResource(R.dimen.padding_small)),
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
            Text(text = stringResource(R.string.Playingday_playing_day_id, playingdayId))
            Text(text = stringResource(R.string.Playingday_date, date))
            Text(text = stringResource(R.string.Playingday_is_finished, isFinished))
        }
    }
}
