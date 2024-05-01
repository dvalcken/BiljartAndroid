package com.example.biljart.ui.playingdaycomponents

sealed interface PlayingdayApiState {
    object Error : PlayingdayApiState
    object Loading : PlayingdayApiState
    object Success : PlayingdayApiState
}
