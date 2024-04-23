package com.example.biljart.ui.playingdaycomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlayingDayOverview(modifier: Modifier = Modifier) {
    val viewModel: PlayingDayOverviewViewModel = viewModel()

    val playingDayUiState by viewModel.playingDayUiState.collectAsState()
    val playingDays = playingDayUiState.playingDays

    val content: @Composable() (BoxScope.() -> Unit) = {
        val state = rememberLazyListState()
        LazyColumn(state = state) {
            items(playingDays) {
                PlayingDayItem(
                    playingday_id = it.playingday_id,
                    date = it.date,
                    is_finished = it.is_finished,
                )
            }
        }
    }
    Box(modifier = modifier, content = content)
}
