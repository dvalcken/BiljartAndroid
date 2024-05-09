package com.example.biljart.ui.matchoverviewcomponents

sealed interface MatchOverviewApiState {
    object Error : MatchOverviewApiState
    object Loading : MatchOverviewApiState
    object Success : MatchOverviewApiState
}
