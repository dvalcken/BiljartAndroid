package com.example.biljart.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.biljart.data.RankSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RankingOverviewViewModel : ViewModel() { // Lesson 4: 1:12:00
    private val _rankUiState = MutableStateFlow(RankingOverviewState(RankSampler.getAll())) // private mutable state flow to update the state
    val rankUiState = _rankUiState.asStateFlow() // craates a wrapper around the MutableStateFlow, expose the state as a flow, this is read-only (not mutable)

    init {
        Log.i("RankingViewModel", "creating new instance $this")
    }
}
