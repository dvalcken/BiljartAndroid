package com.example.biljart.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.biljart.BiljartApplication
import com.example.biljart.data.RankingRepository
import com.example.biljart.model.Rank
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

class RankingOverviewViewModel(
    private val rankingRepository: RankingRepository, // Lesson 8 25'30 (added the repository) + Lesson 8 29'15" To fix the dependency injection with a factory (see below)
) : ViewModel() { // Lesson 4: 1:12:00 + Lesson 8 25'30 (added the repository)
    private val _rankUiState = MutableStateFlow(RankingOverviewState()) // private mutable state flow to update the state
    val rankUiState: StateFlow<RankingOverviewState> = _rankUiState.asStateFlow() // craates a wrapper around the MutableStateFlow, expose the state as a flow, this is read-only (not mutable)

    var rankingApiState: RankingApiState by mutableStateOf(RankingApiState.Loading)
        private set

    lateinit var rankingListAsState: StateFlow<List<Rank>> // Lesson 9 1u23'

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage // This needed this dependency: implementation("androidx.compose.runtime:runtime-livedata")

    init {
//        Log.i("RankingViewModel", "creating new instance $this")
        getRepoRanks()
    }

    // Les 9 1u30: see new function to add a rank TODO

    // get the ranks from the repository
    private fun getRepoRanks() { // Les 9 1u17: renamed from getApiRank to getRepoRank
        rankingApiState = RankingApiState.Loading

        try {
            _toastMessage.postValue("Local data shown, fetching data from the API...")
            viewModelScope.launch {
                try {
                    _toastMessage.postValue("Local data shown, fetching data from the API...")
                    rankingRepository.refreshRanking() // refresh the data in the database when the viewmodel is created
                } catch (e: Exception) {
                    Log.w("RankingOverviewViewModel rankingRepository.refreshRanking() error", "RankingOverviewViewModel rankingRepository.refreshRanking() error: ${e.message}", e)
                    if (rankingListAsState.value.isNotEmpty()) {
                        _toastMessage.postValue("Local data shown, could not update via API.")
                        // Reset the message after 3 seconds
                        delay(5_000L)
                        _toastMessage.postValue(null)
                    }
                }
            }
            // getRank is a suspend function, so we need to call it from a coroutine
//            Log.i("RankingOverviewViewModel", "getApiRank called")
            // use the repository instead of the service
            // val ranks = rankService.getRank()
            rankingListAsState = rankingRepository.getAllRanks()
                .stateIn( // Les 9 1u20: get hot stateflow
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf(), // empty list of ranks
                )
            // val ranks = rankingRepository.getRanking() // Lesson 8 25' (this is the new way to get the ranks with the repository)  Les 9 1u24 commented because of the new way to get the ranks via the repository

            rankingApiState = RankingApiState.Success
            // println("RankingOverviewViewModel.getApiRank: $ranks")
        } catch (e: SocketTimeoutException) {
            /* TODO show a toast */
            rankingApiState = RankingApiState.Error
            Log.e("RankingOverviewViewModel SocketTimeoutException error", "getApiRank SocketTimeoutException error: ${e.message}", e)
        } catch (e: IOException) {
            /* TODO show a toast */
            rankingApiState = RankingApiState.Error
            Log.e("RankingOverviewViewModel IOException error", "getApiRank IOException error: ${e.message}", e)
        } catch (e: Exception) {
            /* TODO show a toast */
            rankingApiState = RankingApiState.Error
            Log.e("RankingOverviewViewModel loading error", "getApiRank loading error: ${e.message}", e)
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
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
