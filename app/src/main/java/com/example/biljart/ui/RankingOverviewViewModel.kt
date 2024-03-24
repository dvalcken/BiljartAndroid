package com.example.biljart.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.biljart.BiljartApplication
import com.example.biljart.data.RankSampler
import com.example.biljart.data.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class RankingOverviewViewModel(
    private val rankingRepository: RankingRepository, // Lesson 8 25'30 (added the repository) + Lesson 8 29'15" To fix the dependency injection with a factory (see below)
) : ViewModel() { // Lesson 4: 1:12:00 + Lesson 8 25'30 (added the repository)
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
//                val ranks = rankService.getRank()
                val ranks = rankingRepository.getRanking() // Lesson 8 25' (this is the new way to get the ranks with the repository)
                rankApiState = RankApiState.Success(ranks) // update the state with the new ranks, les 7 1u22" + les 8 25'30 removed the asDomainObjects() from the ranks
                println("RankingOverviewViewModel.getApiRank: $ranks")
            } catch (e: SocketTimeoutException) {
                /* TODO show a toast */
                rankApiState = RankApiState.Error
                Log.e("RankingOverviewViewModel SocketTimeoutException error", "getApiRank SocketTimeoutException error: ${e.message}", e)
            } catch (e: IOException) {
                /* TODO show a toast */
                rankApiState = RankApiState.Error
                Log.e("RankingOverviewViewModel IOException error", "getApiRank IOException error: ${e.message}", e)
            } catch (e: Exception) {
                /* TODO show a toast */
                rankApiState = RankApiState.Error
                Log.e("RankingOverviewViewModel loading error", "getApiRank loading error: ${e.message}", e)
            }
        }
    }

    companion object { // Lesson 8 29'15" To fix the dependency injection with a factory
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BiljartApplication
                val rankingRepository = application.appContainer.rankingRepository
                RankingOverviewViewModel(rankingRepository)
            }
        }
    }
}
