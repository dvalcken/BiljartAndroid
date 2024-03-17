package com.example.biljart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlayingDayOverview(modifier: Modifier = Modifier) {
    val viewModel: PlayingDayViewModel = viewModel()

    val playingDayUiState by viewModel.playingDayUiState.collectAsState()
    val playingDays = playingDayUiState.playingDays

    Box(modifier = modifier) {
        LazyColumn {
            items(playingDays) {
                PlayingDay(
                    playingday_id = it.playingday_id,
                    date = it.date,
                    is_finished = it.is_finished,
                )
            }
        }
    }
}

@Composable
fun PlayingDay(
    playingday_id: Int,
    date: String,
    is_finished: Boolean,
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
            Text(text = "Playing day ID: $playingday_id")
            Text(text = "Date: $date")
            Text(text = "Is finished: $is_finished")
        }
    }
}
