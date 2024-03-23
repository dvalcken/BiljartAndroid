package com.example.biljart.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biljart.data.RankSampler
import com.example.biljart.network.RankApi.rankService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RankingOverviewViewModel : ViewModel() { // Lesson 4: 1:12:00
    private val _rankUiState = MutableStateFlow(RankingOverviewState(RankSampler.getAll())) // private mutable state flow to update the state
    val rankUiState: StateFlow<RankingOverviewState> = _rankUiState.asStateFlow() // craates a wrapper around the MutableStateFlow, expose the state as a flow, this is read-only (not mutable)

    var rankApiState: RankApiState by mutableStateOf(RankApiState.Loading)
        private set

    init {
        Log.i("RankingViewModel", "creating new instance $this")
        getApiRank()
    }

    private fun getApiRank() {
        viewModelScope.launch { // getRank is a suspend function, so we need to call it from a coroutine
            val ranks = rankService.getRank()
            println("RankingOverviewViewModel.getApiRank: $ranks")
        }
    }
}
