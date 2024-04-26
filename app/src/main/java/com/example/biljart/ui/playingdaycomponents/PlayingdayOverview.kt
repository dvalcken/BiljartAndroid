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
fun PlayingdayOverview(modifier: Modifier = Modifier) {
    val viewModel: PlayingdayOverviewViewModel = viewModel()

    val playingDayUiState by viewModel.playingdayUiState.collectAsState()
    val playingDays = playingDayUiState.playingdays

    val content: @Composable() (BoxScope.() -> Unit) = {
        val state = rememberLazyListState()
        LazyColumn(state = state) {
            items(playingDays) {
                PlayingdayItem(
                    playingdayId = it.playingdayId,
                    date = it.date,
                    isFinished = it.isFinished,
                )
            }
        }
    }
    Box(modifier = modifier, content = content)
}
