package com.example.biljart.ui.playingdaycomponents

import androidx.lifecycle.ViewModel
import com.example.biljart.data.PlayingdaySampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayingdayOverviewViewModel : ViewModel() {
    private val _playingdayOverviewState = MutableStateFlow(PlayingdayOverviewState(PlayingdaySampler.getAll()))
    val playingdayUiState = _playingdayOverviewState.asStateFlow()

//    init {
//        Log.i("PlayingDayViewModel", "creating new instance $this")
//    }
}
