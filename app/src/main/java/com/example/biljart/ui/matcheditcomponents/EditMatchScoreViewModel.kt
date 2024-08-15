package com.example.biljart.ui.matcheditcomponents

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.biljart.data.AppContainer
import com.example.biljart.data.MatchRepository

class EditMatchScoreViewModel(
    private val matchRepository: MatchRepository,
    private val matchId: Int,
) : ViewModel() {

    var player1FramesWon: Int by mutableIntStateOf(0)
        private set

    var player2FramesWon: Int by mutableIntStateOf(0)
        private set

    // The UI observes the LiveData errorMessage to display the error message (observer pattern)
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    suspend fun updateScores(player1Score: String, player2Score: String): Boolean {
        // Convert the scores to integers or return false if invalid
        // Return of false will keep the dialog open in EditMatchScoreDialog
        val player1FramesWon = player1Score.toIntOrNull() ?: return false
        val player2FramesWon = player2Score.toIntOrNull() ?: return false

        return try {
            Log.i("EditMatchScoreViewModel", "Updating match score for matchId $matchId, player1FramesWon $player1FramesWon, player2FramesWon $player2FramesWon")
            matchRepository.updateMatchScores(matchId, player1FramesWon, player2FramesWon)
            _errorMessage.postValue(null) // Clear any existing error message if updateMatchScores did not throw an exception
            Log.i("EditMatchScoreViewModel", "Match score updated successfully")
            true // Return true if the update was successful, so the dialog can be dismissed in EditMatchScoreDialog
        } catch (e: Exception) {
            _errorMessage.postValue(e.message)
            Log.w("EditMatchScoreViewModel", "Error updating the match score: ${e.message}", e)
            false // Return false if the update was not successful, so the dialog can stay open in EditMatchScoreDialog
        }
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
