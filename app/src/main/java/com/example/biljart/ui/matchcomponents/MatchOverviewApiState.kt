package com.example.biljart.ui.matchcomponents

sealed interface MatchOverviewApiState {
    object Error : MatchOverviewApiState
    object Loading : MatchOverviewApiState
    object Success : MatchOverviewApiState
}
