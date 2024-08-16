package com.example.biljart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme

@Composable
fun StartScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(), // Fill entire screen space
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 2.dp, // elevation for depth
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // fill the available space.
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Center, // center the content vertically.
            horizontalAlignment = Alignment.CenterHorizontally, // center the content horizontally.
        ) {
            Text(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth(),
                text =
                "Welcome to the Billiard App!",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth(),
                text = // using """ to create a multiline string
                """
                    Use the icons in the bottom bar to view the ranking and playing days. 
                    Stay up to date with the latest scores and match details.
                """.trimIndent(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )

            // show the 8-ball logo
            val logo = painterResource(R.drawable.logo8ball)
            Image(
                painter = logo,
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .fillMaxWidth() // use max available width
                    .wrapContentHeight(align = Alignment.CenterVertically), // aligns the image vertically in the center
            )
        }
    }
}

// preview
@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    BilliardTheme(darkTheme = false) {
        StartScreen()
    }
}
