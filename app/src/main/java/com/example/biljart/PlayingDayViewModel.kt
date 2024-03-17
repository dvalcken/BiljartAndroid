package com.example.biljart

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayingDayViewModel : ViewModel() {
    private val _playingDayUiState = MutableStateFlow(PlayingDayUiState(playingDays = data.PlayingDay.getAll()))
    val playingDayUiState = _playingDayUiState.asStateFlow()

    init {
        Log.i("PlayingDayViewModel", "creating new instance $this")
    }
}
