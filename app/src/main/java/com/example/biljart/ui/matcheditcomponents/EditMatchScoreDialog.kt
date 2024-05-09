package com.example.biljart.ui.matcheditcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.BiljartApplication
import com.example.biljart.R

@Composable
fun EditMatchScoreDialog(
    matchId: Int,
//    matchRepository: MatchRepository,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val appContainer = LocalContext.current.applicationContext as BiljartApplication
    val editMatchScoreViewModel: EditMatchScoreViewModel = viewModel(
        factory = EditMatchScoreViewModel.provideFactory(appContainer.appContainer, matchId),
    )

    var player1Score: Int by remember { mutableIntStateOf(0) }
    var player2Score: Int by remember { mutableIntStateOf(0) }

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
                    value = player1Score.toString(),
                    onValueChange = { player1Score = it.toIntOrNull() ?: 0 },
                    label = { Text("Player 1 score") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_small)))
                OutlinedTextField(
                    value = player2Score.toString(),
                    onValueChange = { player2Score = it.toIntOrNull() ?: 0 },
                    label = { Text("Player 2 score") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                editMatchScoreViewModel.updateScores(player1Score, player2Score)
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    )
}
