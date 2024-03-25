package com.example.biljart.ui

import com.example.biljart.model.Rank

sealed interface RankingApiState {
    object Error : RankingApiState
    object Loading : RankingApiState
    data class Success(val ranks: List<Rank>) : RankingApiState
}
