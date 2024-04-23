package com.example.biljart.ui

sealed interface RankingApiState {
    object Error : RankingApiState
    object Loading : RankingApiState
    object Success : RankingApiState
}
