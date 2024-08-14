package com.example.biljart.ui.matcheditcomponents

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.biljart.data.AppContainer
import com.example.biljart.data.MatchRepository
import kotlinx.coroutines.launch

class EditMatchScoreViewModel(
    private val matchRepository: MatchRepository,
    private val matchId: Int,
) : ViewModel() {

    var player1FramesWon: Int by mutableIntStateOf(0)
        private set

    var player2FramesWon: Int by mutableIntStateOf(0)
        private set

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun updateScores(player1Score: String, player2Score: String) {
        val player1FramesWon = player1Score.toIntOrNull() ?: 0
        val player2FramesWon = player2Score.toIntOrNull() ?: 0

        viewModelScope.launch {
            try {
                Log.i("EditMatchScoreViewModel", "Updating match score for matchId $matchId, player1FramesWon $player1FramesWon, player2FramesWon $player2FramesWon")
                matchRepository.updateMatchScores(matchId, player1FramesWon, player2FramesWon)
                _toastMessage.postValue("Match score updated successfully")
                Log.i("EditMatchScoreViewModel", "Match score updated successfully")
            } catch (e: Exception) {
                _toastMessage.postValue("Error updating the match score...")
                Log.w("EditMatchScoreViewModel", "Error updating the match score: ${e.message}", e)
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
    companion object {
        // Same setup as in MatchOverviewViewModel, because matchId is passed as a parameter
        fun provideFactory(
            appContainer: AppContainer,
            matchId: Int,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(EditMatchScoreViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return EditMatchScoreViewModel(appContainer.matchRepository, matchId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
