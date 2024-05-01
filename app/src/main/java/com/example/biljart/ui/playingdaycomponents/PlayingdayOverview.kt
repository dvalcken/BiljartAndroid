package com.example.biljart.ui.playingdaycomponents

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun PlayingdayOverview(
    modifier: Modifier = Modifier,
    playingdayOverviewViewModel: PlayingdayOverviewViewModel = viewModel(factory = PlayingdayOverviewViewModel.Factory),
    onPlayingdaySelected: (Int) -> Unit,
) {
    val playingdayApiState = playingdayOverviewViewModel.playingdayApiState
    val playingdayListState = playingdayOverviewViewModel.playingdayListAsState.collectAsState()

    val context = LocalContext.current
    val toastMessage by playingdayOverviewViewModel.toastMessage.observeAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            playingdayOverviewViewModel.clearToastMessage()
        }
    }

    Box(modifier = modifier) {
        when (playingdayApiState) {
            is PlayingdayApiState.Loading -> LoadingMessageComponent(message = "Loading playingdays...")
            is PlayingdayApiState.Error -> ErrorMessageComponent(message = "Error loading playingdays")
            is PlayingdayApiState.Success -> {
                if (playingdayListState.value.isEmpty()) {
                    NoItemFoundComponent(message = "No playingdays available")
                } else {
                    LazyColumn(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                        items(playingdayListState.value) { playingday ->
                            PlayingdayItem(
                                playingdayId = playingday.playingdayId,
                                date = playingday.date,
                                isFinished = playingday.isFinished,
                                onClick = { onPlayingdaySelected(playingday.playingdayId) },
                            )
                        }
                    }
                }
            }
        }
    }
}
