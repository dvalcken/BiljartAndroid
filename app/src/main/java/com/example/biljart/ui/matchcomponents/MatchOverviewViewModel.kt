package com.example.biljart.ui.matchcomponents

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
import com.example.biljart.data.MatchRepository
import com.example.biljart.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MatchOverviewViewModel(
    private val matchRepository: MatchRepository,
) : ViewModel() {
    private val _matchUiState = MutableStateFlow(MatchOverviewState())
    val matchUiState: StateFlow<MatchOverviewState> = _matchUiState.asStateFlow()

    var matchApiState: MatchOverviewApiState by mutableStateOf(MatchOverviewApiState.Loading)
        private set

    lateinit var matchListAsState: StateFlow<List<Match>>

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    // init can not be used here, because the playingdayId is not known at the time of creation
    // The method getRepoMatches() is called explicitly from the composable MatchOverview
    init {
        Log.i("MatchViewModel", "creating new instance $this")
        getRepoMatches(31)
    }

    // public instead of private, because it is called from the composable MatchOverview to pass the playingdayId
    fun getRepoMatches(playingdayId: Int) {
        matchApiState = MatchOverviewApiState.Loading

        matchListAsState = matchRepository.getMatchesByPlayingDay(playingdayId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf(),
            )

        matchApiState = MatchOverviewApiState.Success

        viewModelScope.launch {
            try {
                matchRepository.refreshMatches(playingdayId)
                _toastMessage.postValue("Matches refreshed via the API")
            } catch (e: SocketTimeoutException) {
                Log.e("MatchOverviewViewModel matchRepository.refreshMatches() error", "MatchOverviewViewModel matchRepository.refreshMatches() error: ${e.message}", e)
                _toastMessage.postValue("Timeout error fetching data via the API... Check the status of the server.")
                matchApiState = MatchOverviewApiState.Error
            } catch (e: Exception) {
                Log.w("MatchOverviewViewModel matchRepository.refreshMatches() error", "MatchOverviewViewModel matchRepository.refreshMatches() error: ${e.message}", e)
                _toastMessage.postValue("Error fetching data via the API...")
                matchApiState = MatchOverviewApiState.Error
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BiljartApplication
                val matchRepository = application.appContainer.matchRepository
                MatchOverviewViewModel(matchRepository)
            }
        }
    }
}
