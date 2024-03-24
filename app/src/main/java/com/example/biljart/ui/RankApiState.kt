package com.example.biljart.ui

import com.example.biljart.model.Rank

sealed interface RankApiState {
    object Error : RankApiState
    object Loading : RankApiState
    data class Success(val ranks: List<Rank>) : RankApiState
}
