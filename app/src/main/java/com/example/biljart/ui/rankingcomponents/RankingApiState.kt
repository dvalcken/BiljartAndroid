package com.example.biljart.ui.rankingcomponents

sealed interface RankingApiState {
    object Error : RankingApiState
    object Loading : RankingApiState
    object Success : RankingApiState
}
