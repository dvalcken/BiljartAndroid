package com.example.biljart.ui.matcheditcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.BiljartApplication
import com.example.biljart.R
import kotlinx.coroutines.launch

@Composable
fun EditMatchScoreDialog(
    matchId: Int,
    player1: String,
    player2: String,
    player1FramesWon: Int?,
    player2FramesWon: Int?,
    onDismiss: () -> Unit,
) {
    val appContainer = LocalContext.current.applicationContext as BiljartApplication
    val editMatchScoreViewModel: EditMatchScoreViewModel = viewModel(
        key = "EditMatchScoreViewModel_$matchId", // matchId is used as a key to create a new ViewModel instance for each match
        factory = EditMatchScoreViewModel.provideFactory(appContainer.appContainer, matchId),
    )

    // Display a blank field if the value is null, otherwise convert the value to a string
    var player1Score: String by remember { mutableStateOf(player1FramesWon?.toString() ?: "") }
    var player2Score: String by remember { mutableStateOf(player2FramesWon?.toString() ?: "") }

    val errorMessage by editMatchScoreViewModel.errorMessage.observeAsState()

    val scope = rememberCoroutineScope()

    AlertDialog(
        // This is a Material M3 AlertDialog: https://developer.android.com/develop/ui/compose/components/dialog
        icon = {
            Icon(
                Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_match_scores),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        title = { Text(text = stringResource(R.string.edit_match_scores_with_matchId, matchId)) },
        text = {
            Column {
                OutlinedTextField(
                    value = player1Score,
                    // Allow the user to clear the field and enter a new value
                    onValueChange = { newValue ->
                        // Only update the value if it's a valid number
                        if (newValue.all { it.isDigit() }) { // isDigit() checks if all characters are digits (0-9)
                            player1Score = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.player_score, player1)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_small)))
                OutlinedTextField(
                    value = player2Score,
                    // Allow the user to clear the field and enter a new value
                    onValueChange = { newValue ->
                        // Only update the value if it's a valid number or empty
                        if (newValue.all { it.isDigit() }) {
                            player2Score = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.player_score, player2)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                )

                if (!errorMessage.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_small)))
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.onErrorContainer)
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                // Attempt to update the scores; don't dismiss dialog if there's an error
                // Launch a coroutine to handle the suspend function 'updateScores'
                scope.launch {
                    val success = editMatchScoreViewModel.updateScores(player1Score, player2Score)
                    if (success) {
                        onDismiss()
                    }
                }
            }) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}
