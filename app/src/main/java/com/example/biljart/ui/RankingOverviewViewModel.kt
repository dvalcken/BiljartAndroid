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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class RankingOverviewViewModel(
    private val rankingRepository: RankingRepository, // Lesson 8 25'30 (added the repository) + Lesson 8 29'15" To fix the dependency injection with a factory (see below)
) : ViewModel() { // Lesson 4: 1:12:00 + Lesson 8 25'30 (added the repository)
    private val _rankUiState = MutableStateFlow(RankingOverviewState()) // private mutable state flow to update the state
    val rankUiState: StateFlow<RankingOverviewState> = _rankUiState.asStateFlow() // creates a wrapper around the MutableStateFlow, expose the state as a flow, this is read-only (not mutable)

    var rankingApiState: RankingApiState by mutableStateOf(RankingApiState.Loading)
        private set

    lateinit var rankingListAsState: StateFlow<List<Rank>> // Lesson 9 1u23'

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage // This needed this dependency: implementation("androidx.compose.runtime:runtime-livedata")

    init {
        Log.i("RankingViewModel", "creating new instance $this")
        getRepoRanks()
    }

    // Les 9 1u30: see new function to add a rank TODO

    // get the ranks from the repository
    private fun getRepoRanks() { // Les 9 1u17: renamed from getApiRank to getRepoRank
        rankingApiState = RankingApiState.Loading

        rankingListAsState = rankingRepository.getAllRanks() // this immediately returns a state flow of the ranks from the database, so the loading message disappears immediately
            .stateIn( // Les 9 1u20: get hot stateflow
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf(), // empty list of ranks
            )
        // val ranks = rankingRepository.getRanking() // Lesson 8 25' (this is the new way to get the ranks with the repository)  Les 9 1u24 commented because of the new way to get the ranks via the repository

        rankingApiState = RankingApiState.Success

        viewModelScope.launch {
            try {
                rankingRepository.refreshRanking() // refresh the data in the database when the viewmodel is created
                _toastMessage.postValue("Local data updated via the API...") // TODO consider removing this message after development
            } catch (e: SocketTimeoutException) {
                Log.w("RankingOverviewViewModel rankingRepository.refreshRanking() error", "RankingOverviewViewModel rankingRepository.refreshRanking() error: ${e.message}", e)
                _toastMessage.postValue("Timeout error fetching data via the API... Check the status of the server.")
                rankingApiState = RankingApiState.Error
            } catch (e: Exception) {
                Log.w("RankingOverviewViewModel rankingRepository.refreshRanking() error", "RankingOverviewViewModel rankingRepository.refreshRanking() error: ${e.message}", e)
                _toastMessage.postValue("Error fetching data via the API...")
                rankingApiState = RankingApiState.Error
            }
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
