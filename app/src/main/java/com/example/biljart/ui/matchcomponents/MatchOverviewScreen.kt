package com.example.biljart.ui.matchcomponents

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.R
import com.example.biljart.ui.genericcomponents.ErrorMessageComponent
import com.example.biljart.ui.genericcomponents.LoadingMessageComponent
import com.example.biljart.ui.genericcomponents.NoItemFoundComponent

@Composable
fun MatchOverviewScreen(
    playingdayId: Int,
    matchOverviewViewModel: MatchOverviewViewModel = viewModel(factory = MatchOverviewViewModel.Factory),
    modifier: Modifier = Modifier,
) {
    // Detail view implementation
    /* TODO: Implement the detail view for a playing day, by showing the list of matches */

    // show a text with the playingdayId
    Text(text = "Playingday ID: $playingdayId", style = MaterialTheme.typography.titleMedium)

    // Explicitly call the method to get the matches for the playing day,
    // because the playingdayId is not known at the time of creation of the ViewModel
    LaunchedEffect(playingdayId) {
        matchOverviewViewModel.getRepoMatches(playingdayId)
    }

    val matchApiState = matchOverviewViewModel.matchApiState
    val matchListState = matchOverviewViewModel.matchListAsState.collectAsState()

    val context = LocalContext.current
    val toastMessage by matchOverviewViewModel.toastMessage.observeAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            matchOverviewViewModel.clearToastMessage()
        }
    }

    Box(modifier = modifier) {
        when (matchApiState) {
            is MatchOverviewApiState.Loading -> LoadingMessageComponent(message = "Loading matches...")
            is MatchOverviewApiState.Error -> ErrorMessageComponent(message = "Error loading matches")
            is MatchOverviewApiState.Success -> {
                if (matchListState.value.isEmpty()) {
                    NoItemFoundComponent(message = "No matches available")
                } else {
                    LazyColumn(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                        items(matchListState.value) { match ->
                            MatchItem(
                                matchId = match.matchId,
                                player1 = match.player1.name,
                                player2 = match.player2.name,
                                player1FramesWon = match.player1FramesWon,
                                player2FramesWon = match.player2FramesWon,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchItem(
    matchId: Int,
    player1: String,
    player2: String,
    player1FramesWon: Int?,
    player2FramesWon: Int?,
    modifier: Modifier = Modifier,
) {
    // Layout for a single match item
    Box(modifier = modifier.padding(dimensionResource(R.dimen.padding_small))) {
        if (player1FramesWon == null || player2FramesWon == null) {
            Text(text = "Match $matchId: $player1 vs $player2 not yet played")
        } else {
            Text(text = "Match $matchId: $player1 vs $player2 ($player1FramesWon : $player2FramesWon)")
        }
    }
}
