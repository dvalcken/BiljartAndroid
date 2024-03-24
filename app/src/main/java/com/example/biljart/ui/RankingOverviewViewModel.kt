package com.example.biljart.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biljart.data.RankSampler
import com.example.biljart.network.RankApi.rankService
import com.example.biljart.network.asDomainObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

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
            Log.i("RankingOverviewViewModel", "getApiRank called")
            try {
                val ranks = rankService.getRank()
                rankApiState = RankApiState.Success(ranks.asDomainObjects()) // update the state with the new ranks, les 7 1u22"
                println("RankingOverviewViewModel.getApiRank: $ranks")
            } catch (e: SocketTimeoutException) {
                rankApiState = RankApiState.Error
                Log.e("RankingOverviewViewModel SocketTimeoutException error", "getApiRank SocketTimeoutException error: ${e.message}", e)
            } catch (e: IOException) {
                rankApiState = RankApiState.Error
                Log.e("RankingOverviewViewModel IOException error", "getApiRank IOException error: ${e.message}", e)
            } catch (e: Exception) {
                rankApiState = RankApiState.Error
                Log.e("RankingOverviewViewModel loading error", "getApiRank loading error: ${e.message}", e)
            }
        }
    }
}
