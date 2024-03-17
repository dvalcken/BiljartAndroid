package com.example.biljart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun RankingOverview(modifier: Modifier = Modifier) {
    // val ranks = mutableStateOf(data.Rank.getAll()) // this is the original line, but was moved to the view model

    val viewModel: RankingViewModel = viewModel()
    // this function 'viewModel' returns the same instance of the view model for the same composable
    // function if it already exists, otherwise it creates a new instance.

    val rankUiState by viewModel.rankUiState.collectAsState() // rankUiState is a flow, so we can collect it as a state
    val ranks = rankUiState.ranks

    Box(modifier = modifier) {
        LazyColumn {
            items(ranks) {
                RankItem(
                    player_id = it.player_id,
                    name = it.name,
                    rank = it.rank,
                    total_frames_won = it.total_frames_won,
                    total_frames_lost = it.total_frames_lost,
                    total_matches_won = it.total_matches_won,
                    total_matches_played = it.total_matches_played,
                )
            }
        }
//        Column {
//            for (item in ranks.value) {
//                Rank(
//                    player_id = item.player_id,
//                    name = item.name,
//                    rank = item.rank,
//                    total_frames_won = item.total_frames_won,
//                    total_frames_lost = item.total_frames_lost,
//                    total_matches_won = item.total_matches_won,
//                    total_matches_played = item.total_matches_played,
//                    modifier = Modifier.fillMaxWidth(),
//                )
//            }
//        }
    }
}
