package com.example.biljart

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RankingViewModel : ViewModel() { // Lesson 4: 1:12:00
    private val _rankUiState = MutableStateFlow(RankingUiState(ranks = data.Rank.getAll())) // private mutable state flow to update the state
    val rankUiState = _rankUiState.asStateFlow() // craates a wrapper around the MutableStateFlow, expose the state as a flow, this is read-only (not mutable)

    init {
        Log.i("RankingViewModel", "creating new instance $this")
    }
}
