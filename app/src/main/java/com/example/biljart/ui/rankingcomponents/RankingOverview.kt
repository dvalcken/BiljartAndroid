package com.example.biljart.ui.rankingcomponents

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.ui.genericcomponents.ErrorMessageComponent
import com.example.biljart.ui.genericcomponents.LoadingMessageComponent
import com.example.biljart.ui.genericcomponents.NoItemFoundComponent

@SuppressLint("UnrememberedMutableState")
@Composable
fun RankingOverview(
    modifier: Modifier = Modifier,
    rankingOverviewViewModel: RankingOverviewViewModel = viewModel(factory = RankingOverviewViewModel.Factory),

) {
    val rankApiState = rankingOverviewViewModel.rankingApiState
    val rankingListState = rankingOverviewViewModel.rankingListAsState.collectAsState() // Les 9 1u35'   rankUiState is a flow, so we can collect it as a state

    // This is needed to show a toast message based on the toastMessage in the ViewModel
    val context = LocalContext.current
    val toastMessage by rankingOverviewViewModel.toastMessage.observeAsState() // This needed this dependency: implementation("androidx.compose.runtime:runtime-livedata")

    // Show a toast message when the toastMessage is not null in the ViewModel
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show() // show the toast message when it is not null for a long time (Toast.LENGTH_LONG)
            rankingOverviewViewModel.clearToastMessage() // clear the toast message after showing it
        }
    }

    Box(modifier = modifier) {
        when (rankApiState) {
            is RankingApiState.Loading -> LoadingMessageComponent(message = "Loading ranks...")
            is RankingApiState.Error -> ErrorMessageComponent(message = "Error loading ranks")
            is RankingApiState.Success -> {
                if (rankingListState.value.isEmpty()) {
                    NoItemFoundComponent(message = "No rankings available")
                } else {
                    RankingTableLayout(rankingListState.value)
                }
            }
        }
    }
}
