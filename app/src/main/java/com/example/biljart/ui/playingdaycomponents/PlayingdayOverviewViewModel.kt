package com.example.biljart.ui.playingdaycomponents

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
import com.example.biljart.data.PlayingdayRepository
import com.example.biljart.model.Playingday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class PlayingdayOverviewViewModel(
    private val playingdayRepository: PlayingdayRepository,
) : ViewModel() {
    private val _playingdayUiSte = MutableStateFlow(PlayingdayOverviewState())
    val playingdayUiState = _playingdayUiSte.asStateFlow()

    var playingdayApiState: PlayingdayApiState by mutableStateOf(PlayingdayApiState.Loading)
        private set

    lateinit var playingdayListAsState: StateFlow<List<Playingday>>

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    init {
        Log.i("PlayingdayViewModel", "creating new instance $this")
        getRepoPlayingdays()
    }

    private fun getRepoPlayingdays() {
        playingdayApiState = PlayingdayApiState.Loading

        playingdayListAsState = playingdayRepository.getAllPlayingdays()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf(),
            )

        playingdayApiState = PlayingdayApiState.Success

        viewModelScope.launch {
            try {
                playingdayRepository.refreshPlayingdays()
                _toastMessage.postValue("Playingdays refreshed via the API") /* TODO consider removing this */
            } catch (e: SocketTimeoutException) {
                Log.e("PlayingdayOverviewViewModel playingdayRepository.refreshPlayingdays() error", "PlayingdayOverviewViewModel playingdayRepository.refreshPlayingdays() error: ${e.message}", e)
                _toastMessage.postValue("Timeout error fetching data via the API... Check the status of the server.")
                playingdayApiState = PlayingdayApiState.Error
            } catch (e: Exception) {
                Log.w("PlayingdayOverviewViewModel playingdayRepository.refreshPlayingdays() error", "PlayingdayOverviewViewModel playingdayRepository.refreshPlayingdays() error: ${e.message}", e)
                _toastMessage.postValue("Error fetching data via the API...")
                playingdayApiState = PlayingdayApiState.Error
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
                val playingdayRepository = application.appContainer.playingdayRepository
                PlayingdayOverviewViewModel(playingdayRepository)
            }
        }
    }
}
