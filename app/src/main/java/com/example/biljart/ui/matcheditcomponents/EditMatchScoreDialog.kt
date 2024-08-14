package com.example.biljart.ui.matcheditcomponents

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.BiljartApplication
import com.example.biljart.R

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
        factory = EditMatchScoreViewModel.provideFactory(appContainer.appContainer, matchId),
    )

    val context = LocalContext.current
    val toastMessage by editMatchScoreViewModel.toastMessage.observeAsState()

    // Observe the toast message and show the toast when it changes
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Log.d("Toast", "Showing toast: $it")
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            editMatchScoreViewModel.clearToastMessage()
        }
    }

    // Display a blank field if the value is null, otherwise convert the value to a string
    var player1Score: String by remember { mutableStateOf(player1FramesWon?.toString() ?: "") }
    var player2Score: String by remember { mutableStateOf(player2FramesWon?.toString() ?: "") }
    // var player1Score: Int by remember { mutableIntStateOf(player1FramesWon ?: 0) } // Default to 0 if null
    // var player2Score: Int by remember { mutableIntStateOf(player2FramesWon ?: 0) }

    AlertDialog(
        // This is a Material M3 AlertDialog: https://developer.android.com/develop/ui/compose/components/dialog
        icon = {
            Icon(
                Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_match_scores),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        title = { Text(text = stringResource(R.string.edit_match_scores)) },
        text = {
            Column {
                OutlinedTextField(
                    value = player1Score, // .toString(),
                    // Allow the user to clear the field and enter a new value
                    onValueChange = { newValue ->
                        // Only update the value if it's a valid number or empty
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) { // isDigit() checks if all characters are digits (0-9)
                            player1Score = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.player_score, player1)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_small)))
                OutlinedTextField(
                    value = player2Score, // .toString(),
                    // Allow the user to clear the field and enter a new value
                    onValueChange = { newValue ->
                        // Only update the value if it's a valid number or empty
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                            player2Score = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.player_score, player2)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                // Handle the save button click in the viewmodel, and dismiss the dialog
                editMatchScoreViewModel.updateScores(player1Score, player2Score)
                onDismiss()
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
