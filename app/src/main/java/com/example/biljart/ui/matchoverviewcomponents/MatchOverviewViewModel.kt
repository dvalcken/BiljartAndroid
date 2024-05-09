package com.example.biljart.ui.matchoverviewcomponents

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.biljart.data.AppContainer
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
    private val playingdayId: Int,
) : ViewModel() {
    private val _matchUiState = MutableStateFlow(MatchOverviewState())
    val matchUiState: StateFlow<MatchOverviewState> = _matchUiState.asStateFlow()

    var matchApiState: MatchOverviewApiState by mutableStateOf(MatchOverviewApiState.Loading)
        private set

    lateinit var matchListAsState: StateFlow<List<Match>>

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    init {
        Log.i("MatchViewModel", "creating new instance $this")
        getRepoMatches(playingdayId)
    }

    private fun getRepoMatches(playingdayId: Int) {
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
        // The factory is used to pass the playingdayId to the ViewModel
        // This is different from the PlayingdayOverviewViewModel,
        // because no parameters are needed in other viewmodels
        // https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments
        fun provideFactory(
            appContainer: AppContainer,
            playingdayId: Int,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(MatchOverviewViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return MatchOverviewViewModel(appContainer.matchRepository, playingdayId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
