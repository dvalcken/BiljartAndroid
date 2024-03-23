package com.example.biljart.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun RankingOverview(modifier: Modifier = Modifier) {
    // val ranks = mutableStateOf(data.Rank.getAll()) // this is the original line, but was moved to the view model

    val rankingOverviewViewModel: RankingOverviewViewModel = viewModel()
    // this function 'viewModel' returns the same instance of the view model for the same composable
    // function if it already exists, otherwise it creates a new instance.

    val rankUiState by rankingOverviewViewModel.rankUiState.collectAsState() // rankUiState is a flow, so we can collect it as a state
    val ranks = rankUiState.ranks

    Box(modifier = modifier) {
        val lazyListState = rememberLazyListState()

//        Code below is not needed, because the LazyColumn will automatically scroll to the last item
//        just added here for reference (lesson 4 1:50:00)
//        val coroutineScope = rememberCoroutineScope()
//        coroutineScope.launch {
//            state.animateScrollToItem(rankUiState.ranks.size)
//        }

        LazyColumn(state = lazyListState) { // state = state is not needed, but added for reference (lesson 4 1:50:00)
//            items(ranks) {
//                RankItem(
//                    player_id = it.player_id,
//                    name = it.name,
//                    rank = it.rank,
//                    total_frames_won = it.total_frames_won,
//                    total_frames_lost = it.total_frames_lost,
//                    total_matches_won = it.total_matches_won,
//                    total_matches_played = it.total_matches_played,
//                )
//            }
            val rankApiState = rankingOverviewViewModel.rankApiState
            when (rankApiState) {
                is RankApiState.Loading -> {
                    item {
//                        LoadingRank()
                        Text("Loading ranks...")
                    }
                }
                is RankApiState.Error -> {
                    item {
//                        ErrorRank()
                        Text("Error loading ranks")
                    }
                }
                is RankApiState.Success -> {
                    items(rankApiState.ranks) {
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
//                    for (item in rankApiState.data
//                        item {
//                            RankItem(
//                                player_id = item.player_id,
//                                name = item.name,
//                                rank = item.rank,
//                                total_frames_won = item.total_frames_won,
//                                total_frames_lost = item.total_frames_lost,
//                                total_matches_won = item.total_matches_won,
//                                total_matches_played = item.total_matches_played,
//                            )
//                        }
//                    }
                }
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
