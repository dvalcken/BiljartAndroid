package com.example.biljart.ui.matchoverviewcomponents

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.BiljartApplication
import com.example.biljart.R
import com.example.biljart.ui.genericcomponents.ErrorMessageComponent
import com.example.biljart.ui.genericcomponents.LoadingMessageComponent
import com.example.biljart.ui.genericcomponents.NoItemFoundComponent

@Composable
fun MatchOverviewScreen(
    playingdayId: Int,
    // The ViewModel is created in the composable, because the playingdayId must be passed to the ViewModel factory
    // So different approach than in the PlayingdayOverview composable
//    matchOverviewViewModel: MatchOverviewViewModel = viewModel(factory = MatchOverviewViewModel.Factory),
    modifier: Modifier = Modifier,
) {
    // Following 2 lines are needed to create the ViewModel with the playingdyId parameter in the composable, instead of using
    // the same approach as in the PlayingdayOverview composable
    val appContainer = LocalContext.current.applicationContext as BiljartApplication
    val matchOverviewViewModel: MatchOverviewViewModel = viewModel(
        factory = MatchOverviewViewModel.provideFactory(appContainer.appContainer, playingdayId),
    )

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

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Playingday: $playingdayId",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
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
}
