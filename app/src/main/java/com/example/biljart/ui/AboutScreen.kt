package com.example.biljart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(
                dimensionResource(R.dimen.padding_medium),
            ),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
//                .padding(
//                    dimensionResource(R.dimen.padding_medium),
//                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.spacer_large),
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                Modifier.padding(
                    dimensionResource(R.dimen.padding_medium),
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                    dimensionResource(R.dimen.elevation_medium),
                ),
//                contentPadding = PaddingValues(16.dp),
            ) {
                Column(
                    modifier = Modifier.padding(
                        dimensionResource(R.dimen.padding_medium),

                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.spacer_medium),
                    ),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(R.string.developer),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.dimitri_valckenier),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Divider()
                    Text(
                        text = stringResource(R.string.assignment),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.android_project),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Divider()
                    Text(
                        text = stringResource(R.string.additional_information),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.additional_information_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Divider()
                    Text(
                        text = stringResource(R.string.contact),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(R.string.use_the_email_icon_fab),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    BilliardTheme {
        AboutScreen()
    }
}
