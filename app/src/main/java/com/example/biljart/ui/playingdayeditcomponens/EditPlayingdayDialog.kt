package com.example.biljart.ui.playingdayeditcomponens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biljart.BiljartApplication
import com.example.biljart.R

@Composable
fun EditPlayingdayDialog(
    playingdayId: Int,
    isFinished: Boolean,
    onDismiss: () -> Unit,
) {
    val appContainer = LocalContext.current.applicationContext as BiljartApplication
    val editPlayingdayViewModel: EditPlayingdayViewModel = viewModel(
        factory = EditPlayingdayViewModel.provideFactory(appContainer.appContainer),
    )

    var finishedStatus: Boolean by remember { mutableStateOf(isFinished) }

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_playingday),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
//        title = { Text(text = stringResource(R.string.edit_playingday)) },
        title = { Text(text = playingdayId.toString()) },
        text = {
//            Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.is_finished),
                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                )
                // https://developer.android.com/develop/ui/compose/components/switch
                Switch(
                    checked = finishedStatus,
                    onCheckedChange = { finishedStatus = it },
                    colors = SwitchDefaults.colors(
//                        checkedThumbColor = MaterialTheme.colorScheme.primary,  // can remain default
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedIconColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.tertiary,
//                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer, // not needed
                    ),
                    thumbContent = if (finishedStatus) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_small)))
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                editPlayingdayViewModel.updateStatus(playingdayId, finishedStatus)
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

@Preview(showBackground = true)
@Composable
fun PreviewEditPlayingdayDialog() {
    EditPlayingdayDialog(
        playingdayId = 1,
        isFinished = false,
        onDismiss = {},
    )
}
