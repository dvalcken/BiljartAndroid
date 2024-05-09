package com.example.biljart.ui.playingdayeditcomponens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.biljart.data.AppContainer
import com.example.biljart.data.PlayingdayRepository
import kotlinx.coroutines.launch

class EditPlayingdayViewModel(
    private val playingdayRepository: PlayingdayRepository,
    private val playingdayId: Int,
) : ViewModel() {

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun updateStatus(isFinished: Boolean) {
        viewModelScope.launch {
            try {
                Log.i("EditPlayingdayStatusViewModel", "Updating playingday status for playingdayId $playingdayId to isFinished $isFinished")
                playingdayRepository.updatePlayingdayStatus(playingdayId, isFinished)
                _toastMessage.postValue("Playingday status updated successfully")
                Log.i("EditPlayingdayStatusViewModel", "Playingday status updated successfully")
            } catch (e: Exception) {
                _toastMessage.postValue("Error updating the playingday status...")
                Log.w("EditPlayingdayStatusViewModel", "Error updating the playingday status: ${e.message}", e)
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    companion object {
        fun provideFactory(
            appContainer: AppContainer,
            playingdayId: Int,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(EditPlayingdayViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return EditPlayingdayViewModel(appContainer.playingdayRepository, playingdayId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
