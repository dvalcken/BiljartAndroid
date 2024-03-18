package com.example.biljart.ui

import androidx.lifecycle.ViewModel
import com.example.biljart.data.PlayingDaySampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayingDayOverviewViewModel : ViewModel() {
    private val _playingDayOverviewState = MutableStateFlow(PlayingDayOverviewState(PlayingDaySampler.getAll()))
    val playingDayUiState = _playingDayOverviewState.asStateFlow()

//    init {
//        Log.i("PlayingDayViewModel", "creating new instance $this")
//    }
}
