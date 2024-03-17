package com.example.biljart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlayingDayOverview(modifier: Modifier = Modifier) {
    val viewModel: PlayingDayViewModel = viewModel()

    val playingDayUiState by viewModel.playingDayUiState.collectAsState()
    val playingDays = playingDayUiState.playingDays

    Box(modifier = modifier) {
        LazyColumn {
            items(playingDays) {
                PlayingDayItem(
                    playingday_id = it.playingday_id,
                    date = it.date,
                    is_finished = it.is_finished,
                )
            }
        }
    }
}
